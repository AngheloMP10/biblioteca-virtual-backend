package com.biblio.virtual.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblio.virtual.dto.AuthRequest;
import com.biblio.virtual.dto.AuthResponse;
import com.biblio.virtual.dto.RegisterRequest;
import com.biblio.virtual.dto.TwoFactorRequest;
import com.biblio.virtual.dto.TwoFactorResponse;
import com.biblio.virtual.model.Usuario;
import com.biblio.virtual.repository.IUsuarioRepository;
import com.biblio.virtual.service.TwoFactorAuthService;
import com.biblio.virtual.util.JwtUtil;

import jakarta.validation.Valid;

import com.biblio.virtual.security.Roles;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TwoFactorAuthService twoFactorAuthService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {

		/*
		 * Se delega la validación de credenciales a Spring Security para mantener la
		 * lógica de autenticación centralizada.
		 */
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
		}

		/*
		 * El usuario se recupera desde persistencia para construir el token con
		 * información confiable y actual.
		 */
		Usuario usuario = usuarioRepository.findByUsername(authRequest.getUsername())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		/*
		 * Si 2FA está habilitado, se requiere verificación adicional
		 */
		if (usuario.isTwoFAEnabled()) {
			String tempToken = jwtUtil.generateTempToken(usuario.getUsername());
			return ResponseEntity.ok(Map.of("requires2FA", true, "tempToken", tempToken));
		}

		/*
		 * El JWT se genera con username y rol para habilitar autorización basada en
		 * roles sin consultas adicionales.
		 */
		String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRole());

		return ResponseEntity.ok(new AuthResponse(token, usuario.getUsername(), usuario.getRole()));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

		if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body("El usuario ya existe");
		}

		Usuario usuario = new Usuario();
		usuario.setUsername(request.getUsername());
		usuario.setPassword(passwordEncoder.encode(request.getPassword()));
		usuario.setEmail(request.getEmail());
		usuario.setCelular(request.getCelular());
		usuario.setRole(Roles.USER);

		usuarioRepository.save(usuario);

		return ResponseEntity.ok("Usuario registrado exitosamente");
	}

	/*
	 * Endpoint de diagnóstico para validar el contexto de seguridad y el contenido
	 * del token autenticado.
	 */
	@GetMapping("/me")
	public ResponseEntity<Map<String, Object>> verifyUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "No hay autenticación"));
		}

		Usuario usuario = usuarioRepository.findByUsername(auth.getName())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		return ResponseEntity.ok(Map.of("username", auth.getName(), "authorities", auth.getAuthorities(),
				"isAuthenticated", auth.isAuthenticated(), "is2faEnabled", usuario.isTwoFAEnabled()));
	}

	@GetMapping("/generate-qr")
	public ResponseEntity<?> generateQR() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
		}

		Usuario usuario = usuarioRepository.findByUsername(auth.getName())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		String secret = twoFactorAuthService.generateSecret();
		usuario.setSecretKey2FA(secret);

		usuarioRepository.save(usuario);

		String qrUrl = twoFactorAuthService.generateQrUrl(usuario.getUsername(), secret);

		return ResponseEntity.ok(Map.of("secret", secret, "qrUrl", qrUrl));
	}

	@PostMapping("/verify-2fa")
	public ResponseEntity<?> verify2FA(@RequestBody TwoFactorRequest request) {

		String username = jwtUtil.extractUsername(request.getTempToken());

		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		if (!usuario.isTwoFAEnabled()) {
			return ResponseEntity.badRequest().body("2FA no está habilitado");
		}

		boolean valid = twoFactorAuthService.validateCode(usuario.getSecretKey2FA(), request.getCode());

		if (!valid) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código 2FA inválido");
		}

		String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRole());

		return ResponseEntity.ok(new AuthResponse(token, usuario.getUsername(), usuario.getRole()));
	}

	@PostMapping("/confirm-2fa")
	public ResponseEntity<?> confirm2FA(@RequestBody TwoFactorRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElseThrow();

		// Validamos el código que el usuario acaba de escanear
		boolean valid = twoFactorAuthService.validateCode(usuario.getSecretKey2FA(), request.getCode());

		if (!valid) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código incorrecto, no se pudo activar 2FA");
		}

		// Solo si el código es correcto, activamos el 2FA
		usuario.setTwoFAEnabled(true);
		usuarioRepository.save(usuario);

		return ResponseEntity.ok("2FA activado correctamente");
	}

	@PostMapping("/disable-2fa")
	public ResponseEntity<?> disable2FA() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Usuario usuario = usuarioRepository.findByUsername(auth.getName())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		usuario.setTwoFAEnabled(false);
		usuario.setSecretKey2FA(null);

		usuarioRepository.save(usuario);

		return ResponseEntity.ok("2FA desactivado correctamente");
	}
}
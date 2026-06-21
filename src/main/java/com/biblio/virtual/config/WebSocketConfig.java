package com.biblio.virtual.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuración de WebSocket para la aplicación.
 * Habilita mensajería en tiempo real usando STOMP sobre WebSocket.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Registra el endpoint STOMP y configura opciones de conexión.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * Configura el broker de mensajes simple.
     * Define prefijos para rutas de destino y aplicación.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Prefijo para destinos que el servidor envía a clientes
        config.enableSimpleBroker("/topic");

        // Prefijo para mensajes que los clientes envían al servidor
        config.setApplicationDestinationPrefixes("/app");
    }
}

package com.biblio.virtual.service;

import com.biblio.virtual.dto.NotificacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificacionService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void enviarNotificacion(NotificacionDTO notificacion) {
        messagingTemplate.convertAndSend("/topic/notificaciones", notificacion);
    }
}

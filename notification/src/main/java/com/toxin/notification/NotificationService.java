package com.toxin.notification;

import com.toxin.clients.notification.NotificationRequest;

public interface NotificationService {
    void send(NotificationRequest request);
}

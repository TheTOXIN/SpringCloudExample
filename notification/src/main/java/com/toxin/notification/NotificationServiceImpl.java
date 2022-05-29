package com.toxin.notification;

import com.toxin.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;

    @Override
    public void send(NotificationRequest request) {
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(request.toCustomerId())
                        .toCustomerEmail(request.toCustomerName())
                        .message(request.message())
                        .sentAt(LocalDateTime.now())
                        .sender("TheTOXIN")
                        .build()
        );
    }
}

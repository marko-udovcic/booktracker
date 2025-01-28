package com.example.library.controller;

import com.example.library.model.Notification;
import com.example.library.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private  final NotificationService notificationService;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Notification>> getNotificationsForMember(@PathVariable Long memberId) {
        List<Notification> notifications = notificationService.getNotificationsForMember(memberId);
        if (notifications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notifications);
    }


}

package com.example.library.service;

import com.example.library.model.Member;
import com.example.library.model.Notification;
import com.example.library.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(Member member, String message){
        Notification notification = new Notification();
        notification.setMember(member);
        notification.setMessage(message);
        notification.setSendDate(LocalDateTime.now());
        notificationRepository.save(notification);

        messagingTemplate.convertAndSend("/topic/notifications/"+member.getId(), notification);

    }
    public List<Notification> getNotificationsForMember(Long memberId){
        return notificationRepository.findByMemberId(memberId);
    }

}

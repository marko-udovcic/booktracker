package com.example.library.controller;

import com.example.library.model.Member;
import com.example.library.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final NotificationService notificationService;
    @GetMapping("/notifications/{memberId}")
    public String getNotificationsPage(@PathVariable Long memberId) {
        return "index";
    }
}

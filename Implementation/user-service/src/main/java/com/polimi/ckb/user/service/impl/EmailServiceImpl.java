package com.polimi.ckb.user.service.impl;

import com.polimi.ckb.user.dto.EmailDto;
import com.polimi.ckb.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender javaMailSender;

    @Override @Async
    public void sendEmail(EmailDto msg) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(msg.getFrom());
        mailMessage.setTo(msg.getTo());
        mailMessage.setSubject(msg.getSubject());
        mailMessage.setText(msg.getBody());

        mailMessage.setCc(msg.getCc());
        mailMessage.setBcc(msg.getBcc());

        javaMailSender.send(mailMessage);
    }
}

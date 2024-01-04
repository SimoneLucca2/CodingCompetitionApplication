package com.polimi.ckb.user.service;

import com.polimi.ckb.user.dto.EmailDto;

public interface EmailService {
    /**
     * sends an email to the specified email address
     */
    void sendEmail(EmailDto msg);
}

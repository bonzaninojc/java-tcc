package com.ifsc.julio.javatcc.service;

public interface EmailService {
    void sendEmail(String destinatario, String subject, String text);
}

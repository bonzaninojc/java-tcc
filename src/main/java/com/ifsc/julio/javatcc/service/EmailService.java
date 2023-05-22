package com.ifsc.julio.javatcc.service;

public interface EmailService {

    void enviarEmail(String destinatario, String assunto, String mensagem);
}

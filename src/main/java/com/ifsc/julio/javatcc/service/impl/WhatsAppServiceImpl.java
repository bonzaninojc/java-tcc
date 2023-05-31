package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.service.WhatsAppService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppServiceImpl implements WhatsAppService {

    private static final String ACCOUNT_SID = "sua-account-sid";
    private static final String AUTH_TOKEN = "seu-auth-token";

    @Override
    public void sendMessage(String phone, String text) {
        //TODO - Ver se precisa inicializar sempre
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        //TODO - Comprar um número de telefone
        PhoneNumber from = new PhoneNumber("seu-numero-do-twilio");
        PhoneNumber to = new PhoneNumber(phone);
        Message.creator(to, from, text).create();
    }
}

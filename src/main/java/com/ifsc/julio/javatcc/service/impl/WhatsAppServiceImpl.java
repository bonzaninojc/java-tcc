package com.ifsc.julio.javatcc.service.impl;

import com.ifsc.julio.javatcc.service.WhatsAppService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;
import static com.ifsc.julio.javatcc.util.Const.*;

@Service
public class WhatsAppServiceImpl implements WhatsAppService {

    @Override
    public void sendMessage(String phone, String text) {
        //TODO - Ver se precisa inicializar sempre
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        PhoneNumber from = new PhoneNumber(TWILIO_PHONE);
        PhoneNumber to = new PhoneNumber(phone);
        Message.creator(to, from, text).create();
    }
}

package com.ifsc.julio.javatcc.schedule;

import com.ifsc.julio.javatcc.entity.HistoryEmailEntity;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import static java.util.Objects.*;

//@EnableScheduling
@Component
public class CheckStationDataSchedule {

    private static final String OWNER_EMAIL = "julio.bp25@aluno.ifsc.edu.br";
    private static final String OWNER_PHONE = "+5548991455898";
    private static final String SUBJECT_EMAIL = "Estação com mal funcionamento";

    @Autowired
    private StationService stationService;

    @Autowired
    private DeviceTelemetryService deviceTelemetryService;

    @Autowired
    private HistoryEmailService historyEmailService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private WhatsAppService whatsAppService;

    //TODO - Validar tempo do agendamento
//    @Scheduled(cron = "0 0 0,5 * * *")
    public void checkStationDataSchedule() {
        List<StationEntity> stations = stationService.findAll();

        stations.forEach(station -> {
            if (station.isDisabled() || !deviceTelemetryService.hasPassedThreeHoursSinceLimitDate(station.getId())) {
                return;
            }

            String text = getText(station);
            sendEmailOwner(text);
            sendWhatsAppMessageOwner(text);
            sendEmailStationGuardian(station.getEmail(), text);
            sendWhatsAppMessageStationGuardian(station.getPhone(), text);
            saveHistoryEmail(station, text);
        });
    }

    private void sendEmailOwner(String text) {
        emailService.sendEmail(OWNER_EMAIL, SUBJECT_EMAIL, text);
    }

    private void sendWhatsAppMessageOwner(String text) {
        whatsAppService.sendMessage(OWNER_PHONE, text);
    }

    private void sendEmailStationGuardian(String email, String text) {
        if (isNull(email)) {
            return;
        }
        emailService.sendEmail(email, SUBJECT_EMAIL, text);
    }

    private void sendWhatsAppMessageStationGuardian(String phone, String text) {
        if (isNull(phone)) {
            return;
        }
        whatsAppService.sendMessage(phone, text);
    }

    private void saveHistoryEmail(StationEntity station, String text) {
        historyEmailService.save(HistoryEmailEntity.builder()
                .date(new Date())
                .station(station)
                .text(text)
                .build());
    }

    private String getText(StationEntity station) {
        //TODO - Verificar mensagem
        StringBuilder text = new StringBuilder();
        text.append("Prezado(a),\n\n")
            .append("Favor verificar se a estação localizada no(a) ")
            .append(station.getAddress())
            .append(", ")
            .append(station.getCity())
            .append("/")
            .append(station.getUf())
            .append(" está conectada a internet corretamente.");

        return text.toString();
    }
}

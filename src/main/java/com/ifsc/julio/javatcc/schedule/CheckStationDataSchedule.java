package com.ifsc.julio.javatcc.schedule;

import com.ifsc.julio.javatcc.entity.HistoryEmailEntity;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import static java.util.Objects.*;

@EnableScheduling
@Component
public class CheckStationDataSchedule {

    private static final String OWNER_EMAIL = "julio.bp25@aluno.ifsc.edu.br";
    private static final String SUBJECT_EMAIL = "Estação com mal funcionamento";

    @Autowired
    private StationService stationService;

    @Autowired
    private DeviceTelemetryHourService deviceTelemetryHourService;

    @Autowired
    private HistoryEmailService historyEmailService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private WhatsAppService whatsAppService;

    //TODO - Validar tempo do agendamento
    @Scheduled(cron = "0 0 0,5 * * *")
    public void checkStationDataSchedule() {
        List<StationEntity> stations = stationService.findAll();

        stations.forEach(station -> {
            if (station.isDisabled() || !deviceTelemetryHourService.hasPassedThreeHoursSinceLimitDate(station.getId())) {
                return;
            }

            String text = getText(station);
            sendEmailOwner(text);
            sendEmailStationGuardian(station.getEmail(), text);
            saveHistoryEmail(station, text);
        });
    }

    private void sendEmailOwner(String text) {
        emailService.sendEmail(OWNER_EMAIL, SUBJECT_EMAIL, text);
    }

    private void sendEmailStationGuardian(String email, String text) {
        if (isNull(email)) {
            return;
        }
        emailService.sendEmail(email, SUBJECT_EMAIL, text);
    }

    private void saveHistoryEmail(StationEntity station, String text) {
        historyEmailService.save(HistoryEmailEntity.builder()
                .date(new Date())
                .station(station)
                .text(text)
                .build());
    }

    //TODO - Validar texto de envio
    private String getText(StationEntity station) {
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

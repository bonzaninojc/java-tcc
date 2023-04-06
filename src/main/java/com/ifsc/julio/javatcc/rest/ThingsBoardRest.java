package com.ifsc.julio.javatcc.rest;

import com.google.gson.Gson;
import com.ifsc.julio.javatcc.dto.*;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryEntity;
import com.ifsc.julio.javatcc.service.DeviceTelemetryService;
import com.ifsc.julio.javatcc.util.ThingsBoardUtil;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static java.lang.String.*;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpMethod.*;

@Component
public class ThingsBoardRest {

    private static final String LOGIN_ENDPOINT = "%s/api/auth/login";
    private static final String DEVICE_ENDPOINT = "%s/api/plugins/telemetry/DEVICE/%s/values/timeseries";

    private String tokenTemp;
    private LocalDateTime localDateTimeToken;

    @Autowired
    private DeviceTelemetryService deviceTelemetryService;

    @Autowired
    private ThingsBoardUtil thingsBoardUtil;

    @Autowired
    private Gson gson;

    public void saveTelemetry(DeviceSearchDTO deviceSearch) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder.fromUriString(format(DEVICE_ENDPOINT, thingsBoardUtil.getUrl(), thingsBoardUtil.getDevice()))
                .queryParam("keys", deviceSearch.getKeysString())
                .queryParam("startTs", deviceSearch.getStartMiliseconds())
                .queryParam("endTs", deviceSearch.getEndMiliseconds())
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-Authorization", getToken());

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, GET, entity, String.class);
        String responseBody = responseEntity.getBody();

        DeviceTelemetryDTO deviceTelemetry = gson.fromJson(responseBody, DeviceTelemetryDTO.class);
        saveTelemetry(deviceTelemetry);
    }

    private void saveTelemetry(DeviceTelemetryDTO deviceTelemetry) {
        List<DeviceTelemetryEntity> entities = new ArrayList<>();
        HashMap<String, List<TelemetryValueDTO>> devices = deviceTelemetry.getDevices();

        devices.forEach((key, list) -> {
            for (TelemetryValueDTO telemetryValue : list) {
                DeviceTelemetryEntity telemetryEntity = new DeviceTelemetryEntity();
                telemetryEntity.setKey(key);
                telemetryEntity.setDate(new Date(telemetryValue.getTs().intValue()));
                telemetryEntity.setValue(telemetryValue.getValue());
                entities.add(telemetryEntity);
            }
        });

        deviceTelemetryService.saveAll(entities);
    }

    private String getToken() {
        if (nonNull(tokenTemp) && !isTokenExpired()) {
            return tokenTemp;
        }
        localDateTimeToken = LocalDateTime.now();
        tokenTemp = format("Bearer %s", getTokenWithUserCredentials());
        return tokenTemp;
    }

    private boolean isTokenExpired() {
        Duration duration = Duration.between(localDateTimeToken, LocalDateTime.now());
        return duration.toMinutes() > 59;
    }

    public String getTokenWithUserCredentials() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(getLogin(), headers);
        ResponseEntity<String> response = restTemplate.exchange(format(LOGIN_ENDPOINT, thingsBoardUtil.getUrl()), POST, request, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject.getString("token");
    }

    private String getLogin() {
        LoginDTO login = LoginDTO.builder()
                .username(thingsBoardUtil.getUsername())
                .password(thingsBoardUtil.getPassword())
                .build();
        return gson.toJson(login);
    }
}

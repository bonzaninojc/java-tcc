package com.ifsc.julio.javatcc.rest;

import com.google.gson.Gson;
import com.ifsc.julio.javatcc.dto.thingsboard.*;
import com.ifsc.julio.javatcc.util.ThingsBoardUtil;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import static com.ifsc.julio.javatcc.util.Const.*;
import static java.lang.String.*;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.web.util.UriComponentsBuilder.*;

@Component
public class ThingsBoardRest {

    private String tokenTemp;
    private LocalDateTime localDateTimeToken;

    @Autowired
    private ThingsBoardUtil thingsBoardUtil;

    @Autowired
    private Gson gson;

    public void saveTelemetry(ThingsboardSearchDTO thingsboardSearchDTO) {
        ThingsboardValuesDTO thingsboardValuesDTO = getThingsboardValuesDTO(thingsboardSearchDTO);
        //TODO - Salvar valores
    }

    private ThingsboardValuesDTO getThingsboardValuesDTO(ThingsboardSearchDTO thingsboardSearchDTO) {
        ThingsboardValuesDTO thingsboardValuesDTO = getThingsboardValuesDTORest(thingsboardSearchDTO);

        List<DeviceValueDTO> filteredTemperature = filterTelemetryByStation(thingsboardValuesDTO.getTemperature(), thingsboardSearchDTO.getStationId());
        thingsboardValuesDTO.setTemperature(filteredTemperature);

        List<DeviceValueDTO> filteredHumidity = filterTelemetryByStation(thingsboardValuesDTO.getHumidity(), thingsboardSearchDTO.getStationId());
        thingsboardValuesDTO.setHumidity(filteredHumidity);

        return thingsboardValuesDTO;
    }

    private ThingsboardValuesDTO getThingsboardValuesDTORest(ThingsboardSearchDTO thingsboardSearchDTO) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(getUri(thingsboardSearchDTO), GET, getRequestEntity(), String.class);
        String responseBody = responseEntity.getBody();

        return gson.fromJson(responseBody, ThingsboardValuesDTO.class);
    }

    private HttpEntity<?> getRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-Authorization", getToken());

        return new HttpEntity<>(null, headers);
    }

    private URI getUri(ThingsboardSearchDTO thingsboardSearchDTO) {
        return fromUriString(format(DEVICE_ENDPOINT, thingsBoardUtil.getUrl(), thingsBoardUtil.getDevice()))
                .queryParam("keys", thingsboardSearchDTO.getKeysString())
                .queryParam("startTs", thingsboardSearchDTO.getStartMiliseconds())
                .queryParam("endTs", thingsboardSearchDTO.getEndMiliseconds())
                .queryParam("limit", REGISTER_LIMIT)
                .build()
                .toUri();
    }

    private List<DeviceValueDTO> filterTelemetryByStation(List<DeviceValueDTO> telemetryList, UUID stationId) {
        return telemetryList.stream()
                .filter(telemetry -> telemetry.getStationUUID().equals(stationId))
                .collect(toList());
    }

    private String getToken() {
        if (nonNull(tokenTemp) && !isTokenExpired()) {
            return tokenTemp;
        }
        refreshAuthToken();
        return tokenTemp;
    }

    private void refreshAuthToken() {
        localDateTimeToken = LocalDateTime.now();
        tokenTemp = format("Bearer %s", getTokenWithUserCredentials());
    }

    private boolean isTokenExpired() {
        Duration duration = Duration.between(localDateTimeToken, LocalDateTime.now());
        return duration.toMinutes() > 59;
    }

    private String getTokenWithUserCredentials() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getLogin(), getTokenHeader());
        ResponseEntity<TokenDTO> response = restTemplate.exchange(format(LOGIN_ENDPOINT, thingsBoardUtil.getUrl()), POST, request, TokenDTO.class);

        TokenDTO tokenDTO = response.getBody();
        assert tokenDTO != null;
        return tokenDTO.getToken();
    }

    private HttpHeaders getTokenHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    private String getLogin() {
        LoginDTO login = LoginDTO.builder()
                .username(thingsBoardUtil.getUsername())
                .password(thingsBoardUtil.getPassword())
                .build();
        return gson.toJson(login);
    }
}

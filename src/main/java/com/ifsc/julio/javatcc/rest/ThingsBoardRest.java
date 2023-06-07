package com.ifsc.julio.javatcc.rest;

import com.google.gson.Gson;
import com.ifsc.julio.javatcc.dto.*;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryEntity;
import com.ifsc.julio.javatcc.service.DeviceTelemetryService;
import com.ifsc.julio.javatcc.service.StationService;
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
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.web.util.UriComponentsBuilder.*;

@Component
public class ThingsBoardRest {

    private String tokenTemp;
    private LocalDateTime localDateTimeToken;

    @Autowired
    private DeviceTelemetryService deviceTelemetryService;

    @Autowired
    private StationService stationService;

    @Autowired
    private ThingsBoardUtil thingsBoardUtil;

    @Autowired
    private Gson gson;

    public void saveTelemetry(DeviceSearchDTO deviceSearch) {
        DeviceTelemetryDTO deviceTelemetryDTO = getDeviceTelemetryDTO(deviceSearch);
        saveTelemetry(deviceTelemetryDTO);
    }

    private DeviceTelemetryDTO getDeviceTelemetryDTO(DeviceSearchDTO deviceSearch) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(getUri(deviceSearch), GET, getRequestEntity(), String.class);
        String responseBody = responseEntity.getBody();

        DeviceTelemetryDTO deviceTelemetryDTO = gson.fromJson(responseBody, DeviceTelemetryDTO.class);

        if (isNull(deviceSearch.getStationId())) {
            return deviceTelemetryDTO;
        }
        return getDeviceTelemetryDTOPerStation(deviceTelemetryDTO, deviceSearch.getStationId());
    }

    private HttpEntity<?> getRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-Authorization", getToken());

        return new HttpEntity<>(null, headers);
    }

    private URI getUri(DeviceSearchDTO deviceSearch) {
        return fromUriString(format(DEVICE_ENDPOINT, thingsBoardUtil.getUrl(), thingsBoardUtil.getDevice()))
                .queryParam("keys", deviceSearch.getKeysString())
                .queryParam("startTs", deviceSearch.getStartMiliseconds())
                .queryParam("endTs", deviceSearch.getEndMiliseconds())
                .queryParam("limit", REGISTER_LIMIT)
                .build()
                .toUri();
    }

    private DeviceTelemetryDTO getDeviceTelemetryDTOPerStation(DeviceTelemetryDTO deviceTelemetryDTO, UUID stationId) {
        List<TelemetryValueDTO> filteredTemperature = filterTelemetryByStation(deviceTelemetryDTO.getTemperature(), stationId);
        deviceTelemetryDTO.setTemperature(filteredTemperature);

        List<TelemetryValueDTO> filteredHumidity = filterTelemetryByStation(deviceTelemetryDTO.getHumidity(), stationId);
        deviceTelemetryDTO.setHumidity(filteredHumidity);

        return deviceTelemetryDTO;
    }

    private List<TelemetryValueDTO> filterTelemetryByStation(List<TelemetryValueDTO> telemetryList, UUID stationId) {
        return telemetryList.stream()
                .filter(telemetry -> telemetry.getStationUUID().equals(stationId))
                .collect(toList());
    }

    private void saveTelemetry(DeviceTelemetryDTO deviceTelemetry) {
        List<DeviceTelemetryEntity> entities = new ArrayList<>();
        HashMap<String, List<TelemetryValueDTO>> devices = deviceTelemetry.getDevices();

        devices.forEach((key, list) -> {
            for (TelemetryValueDTO telemetryValue : list) {
                DeviceTelemetryEntity telemetryEntity = new DeviceTelemetryEntity();
                telemetryEntity.setKey(key);
                telemetryEntity.setDate(new Date(telemetryValue.getTs()));
                telemetryEntity.setValue(telemetryValue.getValue());
                telemetryEntity.setStation(stationService.findById(telemetryValue.getStationUUID()));
                entities.add(telemetryEntity);
            }
        });
        deviceTelemetryService.saveAll(entities);
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

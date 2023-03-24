package com.ifsc.julio.javatcc.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifsc.julio.javatcc.dto.*;
import com.ifsc.julio.javatcc.entity.DeviceTelemetryEntity;
import com.ifsc.julio.javatcc.service.DeviceTelemetryService;
import com.ifsc.julio.javatcc.util.ThingsBoardUtil;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static java.lang.String.*;
import static java.util.Objects.nonNull;

@Component
public class ThingsBoardRest {

    private static final String LOGIN_ENDPOINT = "/auth/login";
    private static final String DEVICE_ENDPOINT = "%s/api/plugins/telemetry/DEVICE/%s/values/timeseries";

    private String tokenTemp;
    private LocalDateTime localDateTimeToken;

    @Autowired
    private DeviceTelemetryService deviceTelemetryService;

    @Autowired
    private ThingsBoardUtil thingsBoardUtil;

    public void saveTelemetry(DeviceSearchDTO deviceSearch) throws Exception {
        URI uri = new URIBuilder(format(DEVICE_ENDPOINT, thingsBoardUtil.getUrl(), thingsBoardUtil.getDevice()))
                .addParameter("keys", deviceSearch.getKeysString())
                .addParameter("startTs", deviceSearch.getStartMiliseconds())
                .addParameter("endTs", deviceSearch.getEndMiliseconds())
                .build();

        HttpGet request = new HttpGet(uri);
        request.setHeader("x-Authorization", getToken());
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);

        String responseBody = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        DeviceTelemetryDTO deviceTelemetry = objectMapper.readValue(responseBody, DeviceTelemetryDTO.class);
        saveTelemetry(deviceTelemetry, thingsBoardUtil.getDevice());
    }

    private void saveTelemetry(DeviceTelemetryDTO deviceTelemetry, String deviceId) {
        for (TelemetryKeyValuesDTO keyValuesDto : deviceTelemetry.getData()) {
            for (TelemetryValueDTO valueDto : keyValuesDto.getValues()) {
                DeviceTelemetryEntity telemetryEntity = new DeviceTelemetryEntity();
                telemetryEntity.setDeviceId(deviceId);
                telemetryEntity.setKey(keyValuesDto.getKey());
                telemetryEntity.setDate(new Date(valueDto.getTs().intValue()));
                telemetryEntity.setValue(valueDto.getValue());

                deviceTelemetryService.save(telemetryEntity);
            }
        }
    }

    private String getToken() throws Exception {
        if (nonNull(tokenTemp) && !isTokenExpired()) {
            return tokenTemp;
        }
        localDateTimeToken = LocalDateTime.now();
        tokenTemp = getTokenWithUserCredentials();
        return format("Bearer %s", tokenTemp);
    }

    private boolean isTokenExpired() {
        Duration duration = Duration.between(localDateTimeToken, LocalDateTime.now());
        return duration.toMinutes() > 59;
    }

    public String getTokenWithUserCredentials() throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(thingsBoardUtil.getUrl() + LOGIN_ENDPOINT);

        JSONObject json = new JSONObject();
        json.put("username", thingsBoardUtil.getUsername());
        json.put("password", thingsBoardUtil.getPassword());

        StringEntity entity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        HttpEntity responseEntity = response.getEntity();
        JSONObject responseJson = new JSONObject(responseEntity.getContent().toString());

        return responseJson.getString("token");
    }
}

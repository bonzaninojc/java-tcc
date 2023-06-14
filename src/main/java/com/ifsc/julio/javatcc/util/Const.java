package com.ifsc.julio.javatcc.util;

import java.util.Arrays;
import java.util.List;

public class Const {

    public static final String LOGIN_ENDPOINT = "%s/api/auth/login";
    public static final String DEVICE_ENDPOINT = "%s/api/plugins/telemetry/DEVICE/%s/values/timeseries";

    public static final String ACCOUNT_SID = "sua-account-sid";
    public static final String AUTH_TOKEN = "seu-auth-token";
    //TODO - Comprar um número de telefone
    public static final String TWILIO_PHONE = "seu-numero-twilio";

    public static final String TEMPERATURE = "temperature";
    public static final String HUMIDITY = "humidity";
    public static final Integer REGISTER_LIMIT = 10000;

    public static final Integer REQUESTS_DEFAULT = 144;

    protected static final List<String> KEYS = Arrays.asList(TEMPERATURE, HUMIDITY);
}

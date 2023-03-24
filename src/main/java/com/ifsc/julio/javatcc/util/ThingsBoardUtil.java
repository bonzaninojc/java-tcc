package com.ifsc.julio.javatcc.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ThingsBoardUtil {

    @Value("${thisgsboard.url}")
    private String url;

    @Value("${thisgsboard.username}")
    private String username;

    @Value("${thisgsboard.password}")
    private String password;

    @Value("${thisgsboard.dashboard}")
    private String dashboard;

    @Value("${thisgsboard.customer}")
    private String customer;

    @Value("${thisgsboard.device}")
    private String device;

    @Value("${thisgsboard.deviceProfile}")
    private String deviceProfile;
}

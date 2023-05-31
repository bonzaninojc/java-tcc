package com.ifsc.julio.javatcc.repository;

import java.util.UUID;

public interface DeviceTelemetryHourRepositoryCustom {

    boolean hasPassedThreeHoursSinceLimitDate(UUID station);
}

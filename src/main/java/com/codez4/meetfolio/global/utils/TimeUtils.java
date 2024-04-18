package com.codez4.meetfolio.global.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtils {

    // 생성 시간과 현재 시간 비표
    public static long getSinceCreation(LocalDateTime pastTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(pastTime, currentTime);
        return Math.abs(duration.toMinutes());
    }

}

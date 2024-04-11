package com.codez4.meetfolio.domain.point.service;

import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.point.PointRepository;
import com.codez4.meetfolio.domain.point.dto.PointResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.codez4.meetfolio.domain.point.dto.PointResponse.toPointStatics;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointQueryService {
    private final PointRepository pointRepository;

    public PointResponse.PointStatics getPointStatics(int year, int month) {
        String requestMonth = Integer.toString(year) + '-' + month;
        int coverLetterPoint = pointRepository.queryGetPointSum(PointType.USE_COVER_LETTER, requestMonth);
        int analysisPoint = pointRepository.queryGetPointSum(PointType.USE_AI_ANALYSIS, requestMonth);
        String yearMonth = year + "년" + " " + month + "월";
        return toPointStatics(yearMonth, coverLetterPoint, analysisPoint);
    }
}

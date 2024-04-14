package com.codez4.meetfolio.domain.point.service;

import com.codez4.meetfolio.domain.admin.dto.PointAdminResponse;
import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.point.Point;
import com.codez4.meetfolio.domain.point.dto.PointResponse;
import com.codez4.meetfolio.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.codez4.meetfolio.domain.admin.dto.PointAdminResponse.toPointStatics;
import static com.codez4.meetfolio.domain.point.dto.PointResponse.toPointResult;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointQueryService {
    private final PointRepository pointRepository;

    public PointResponse.PointResult getMyPointList(int page, Member member) {
        PageRequest pageRequest = PageRequest.of(page, 9, Sort.by("createdAt").descending());
        Page<Point> points = pointRepository.getPointByMember(member, pageRequest);
        return toPointResult(member, points);
    }

    public PointAdminResponse.PointStatics getPointStatics(int year, int month) {
        String requestMonth = Integer.toString(year) + '-' + month;
        long totalPoint = pointRepository.countAllByPointTypeIsNot(PointType.CHARGE);
        long coverLetterPoint = pointRepository.queryGetPointSum(PointType.USE_COVER_LETTER, requestMonth);
        long analysisPoint = pointRepository.queryGetPointSum(PointType.USE_AI_ANALYSIS, requestMonth);
        String yearMonth = year + "년" + " " + month + "월";
        return toPointStatics(yearMonth, (int) coverLetterPoint, (int) analysisPoint, (int) totalPoint);
    }
}

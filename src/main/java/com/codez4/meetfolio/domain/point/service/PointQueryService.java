package com.codez4.meetfolio.domain.point.service;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.point.Point;
import com.codez4.meetfolio.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.codez4.meetfolio.domain.point.dto.PointResponse.toPointResult;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointQueryService {
    private final PointRepository pointRepository;

    public com.codez4.meetfolio.domain.point.dto.PointResponse.PointResult getMyPointList(int page, Member member) {
        PageRequest pageRequest = PageRequest.of(page, 9, Sort.by("id").descending());
        Page<Point> points = pointRepository.getPointByMember(member, pageRequest);
        return toPointResult(member, points);
    }

}

package com.codez4.meetfolio.domain.point.service;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.point.Point;
import com.codez4.meetfolio.domain.point.dto.PointRequest;
import com.codez4.meetfolio.domain.point.dto.PointResponse;
import com.codez4.meetfolio.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.codez4.meetfolio.domain.point.dto.PointRequest.toEntity;
import static com.codez4.meetfolio.domain.point.dto.PointResponse.toPointProc;

@Service
@RequiredArgsConstructor
@Transactional
public class PointCommandService {

    private final PointRepository pointRepository;

    private Point save(Point point) {
        return pointRepository.save(point);
    }

    public Point post(PointRequest.Post post) {
        return save(toEntity(post));
    }

    public PointResponse.PointProc usePoint(PointRequest.Post post, Member member) {
        Point point = post(post);
        member.setPoint(post.getTotalPoint());
        return toPointProc(point);
    }


}

package com.codez4.meetfolio.domain.point.service;

import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.point.Point;
import com.codez4.meetfolio.domain.point.dto.PointRequest;
import com.codez4.meetfolio.domain.point.dto.PointResponse;
import com.codez4.meetfolio.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.codez4.meetfolio.domain.point.dto.PointRequest.toEntity;
import static com.codez4.meetfolio.domain.point.dto.PointRequest.toSharePoint;
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

        if (post.getPointType() == PointType.USE_COVER_LETTER) {
            shareCoverLetter(post);
        }
        return toPointProc(point);
    }

    public void shareCoverLetter(PointRequest.Post post) {

        CoverLetter coverLetter = post.getCoverLetter();
        Member owner = coverLetter.getMember();
        PointType pointType = PointType.SHARE_COVER_LETTER;
        int point = 100;
        int totalPoint = owner.getPoint() + point;

        Point sharePoint = toSharePoint(point, pointType, totalPoint, owner, coverLetter);
        save(sharePoint);
        owner.setPoint(totalPoint);
    }


}

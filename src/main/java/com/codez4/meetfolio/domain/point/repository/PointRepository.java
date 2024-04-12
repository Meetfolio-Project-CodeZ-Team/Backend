package com.codez4.meetfolio.domain.point.repository;

import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.point.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointRepository extends JpaRepository<Point, Long> {

    Page<Point> getPointByMemberAndPointTypeIs(Member member, PointType pointType, Pageable pageable);
    Page<Point> getPointByMemberAndPointTypeIsNot(Member member, PointType pointType, Pageable pageable);

    @Query("SELECT IFNULL(MAX(POINTSUM.POINT), 0) FROM (SELECT DATE_FORMAT(p.createdAt, '%Y-%c') AS MONTH, sum(p.point) AS POINT FROM Point p WHERE p.pointType = :type GROUP BY MONTH) AS POINTSUM WHERE POINTSUM.MONTH =:month")
    int queryGetPointSum(PointType type, String month);
}

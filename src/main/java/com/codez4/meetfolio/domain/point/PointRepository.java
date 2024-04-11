package com.codez4.meetfolio.domain.point;

import com.codez4.meetfolio.domain.enums.PointType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point,Long> {

    @Query("SELECT IFNULL(MAX(POINTSUM.POINT), 0) FROM (SELECT DATE_FORMAT(p.createdAt, '%Y-%c') AS MONTH, sum(p.point) AS POINT FROM Point p WHERE p.pointType = :type GROUP BY MONTH) AS POINTSUM WHERE POINTSUM.MONTH =:month")
    int queryGetPointSum(PointType type, String month);
}

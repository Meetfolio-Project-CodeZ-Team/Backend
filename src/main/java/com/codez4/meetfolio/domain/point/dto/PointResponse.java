package com.codez4.meetfolio.domain.point.dto;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.point.Point;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import static com.codez4.meetfolio.domain.member.dto.MemberResponse.toMemberInfo;

public class PointResponse {

    @Schema(description = "포인트 내역 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PointResult {

        @Schema(description = "로그인 사용자 정보")
        private MemberResponse.MemberInfo memberInfo;

        @Schema(description = "결제 내역")
        private PointResponse.PointInfo pointInfo;

    }

    @Schema(description = "결제 내역 목록 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PointInfo {

        @Schema(description = "내 포인트")
        private int myPoint;

        @Schema(description = "포인트 내역 목록")
        private List<PointResponse.PointItem> pointList;

        @Schema(description = "페이징된 리스트의 항목 개수")
        private Integer listSize;

        @Schema(description = "총 페이징 수 ")
        private Integer totalPage;

        @Schema(description = "전체 데이터의 개수")
        private Long totalElements;

        @Schema(description = "첫 페이지의 여부")
        private Boolean isFirst;

        @Schema(description = "마지막 페이지의 여부")
        private Boolean isLast;
    }

    @Schema(description = "포인트 내역 목록 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PointItem {

        @Schema(description = "사용/충전 일시")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;

        @Schema(description = "사용/충전 포인트")
        private int point;

        @Schema(description = "사용/충전 타입")
        private String type;

        @Schema(description = "사용/충전 후 포인트")
        private int totalPoint;
    }

    @Schema(description = "포인트 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PointProc {

        @Schema(description = "포인트 Id")
        private Long pointId;

        @Schema(description = "응답 DTO 생성 시간")
        private LocalDateTime createdAt;
    }

    @Schema(description = "포인트 조회 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MemberPoint {

        @Schema(description = "로그인한 사용자")
        private String memberName;

        @Schema(description = "현재 포인트")
        private int point;
    }


    public static PointResult toPointResult(Member member, Page<Point> points) {
        return PointResult.builder()
                .memberInfo(toMemberInfo(member))
                .pointInfo(toPointInfo(member.getPoint(), points))
                .build();

    }

    public static PointInfo toPointInfo(int myPoint, Page<Point> points) {
        List<PointItem> pointList = points.stream().map(PointResponse::toPointItem).toList();
        return PointInfo.builder()
                .myPoint(myPoint)
                .pointList(pointList)
                .listSize(pointList.size())
                .totalPage(points.getTotalPages())
                .totalElements(points.getTotalElements())
                .isFirst(points.isFirst())
                .isLast(points.isLast())
                .build();
    }

    public static PointProc toPointProc(Point point) {
        return PointProc.builder()
                .pointId(point.getId())
                .createdAt(point.getCreatedAt())
                .build();
    }

    public static PointItem toPointItem(Point point) {
        return PointItem.builder()
                .createdAt(point.getCreatedAt())
                .point(point.getPoint())
                .type(point.getPointType().getDescription())
                .totalPoint(point.getTotalPoint())
                .build();
    }

    public static MemberPoint toMemberPoint(Member member) {
        return MemberPoint.builder()
                .memberName(member.getEmail().split("@")[0])
                .point(member.getPoint())
                .build();
    }

}

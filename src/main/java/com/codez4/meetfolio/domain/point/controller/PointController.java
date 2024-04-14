package com.codez4.meetfolio.domain.point.controller;

import com.codez4.meetfolio.domain.coverLetter.service.CoverLetterQueryService;
import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.point.dto.PointRequest;
import com.codez4.meetfolio.domain.point.dto.PointResponse;
import com.codez4.meetfolio.domain.point.service.PointCommandService;
import com.codez4.meetfolio.domain.point.service.PointQueryService;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.ApiResponse;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.codez4.meetfolio.domain.point.dto.PointResponse.toMemberPoint;

@Tag(name = "포인트 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PointController {

    private final PointQueryService pointQueryService;
    private final PointCommandService pointCommandService;
    private final CoverLetterQueryService coverLetterQueryService;

    @Operation(summary = "현재 포인트 조회", description = "포인트 사용 및 충전을 위해 현재 포인트를 조회합니다.")
    @GetMapping("/points")
    public ApiResponse<PointResponse.MemberPoint> getMyPoint(@AuthenticationMember Member member) {
        return ApiResponse.onSuccess(toMemberPoint(member));
    }

    @Operation(summary = "마이페이지 - 포인트 사용 내역 조회", description = "마이페이지의 포인트 사용 내역을 조회합니다.")
    @GetMapping("/my-points")
    public ApiResponse<PointResponse.PointResult> getMyPointList(@AuthenticationMember Member member,
                                                                 @RequestParam(value = "page", defaultValue = "0") int page) {
        return ApiResponse.onSuccess(pointQueryService.getMyPointList(page, member));
    }

    @Operation(summary = "포인트 사용", description = "자소서 조회 / AI 서비스 이용을 위해 포인트를 사용합니다.")
    @Parameter(name = "memberId", description = "자소서 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @PostMapping("/coverLetters/{coverLetterId}/points")
    public ApiResponse<PointResponse.PointProc> usePoint(@AuthenticationMember Member member,
                                                         @Valid @RequestBody PointRequest.PointUseRequest request,
                                                         @PathVariable(value = "coverLetterId") Long coverLetterId) {
        PointType type = PointType.convert(request.getType());
        if (type == PointType.USE_AI_ANALYSIS &&
                coverLetterQueryService.findById(coverLetterId).getMember() != member) {
            throw new ApiException(ErrorStatus._FORBIDDEN);
        }
        int totalPoint = member.getPoint() - request.getPoint();
        PointRequest.Post post =
                PointRequest.Post.builder()
                        .point(request.getPoint())
                        .pointType(type)
                        .totalPoint(totalPoint)
                        .member(member)
                        .build();
        return ApiResponse.onSuccess(pointCommandService.usePoint(post, member));
    }


}

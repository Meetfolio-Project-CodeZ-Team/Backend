package com.codez4.meetfolio.domain.experience.controller;

import static com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.toExperienceCardResult;
import static com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.toExperienceResult;
import static com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.toRecommendCard;

import com.codez4.meetfolio.domain.experience.dto.ExperienceRequest;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.ExperienceCardInfo;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.ExperienceCardItem;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.ExperienceCardResult;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.ExperienceInfo;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.ExperienceProc;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.ExperienceResult;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.RecommendCard;
import com.codez4.meetfolio.domain.experience.service.ExperienceCommandService;
import com.codez4.meetfolio.domain.experience.service.ExperienceQueryService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "경험 분해 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceQueryService experienceQueryService;
    private final ExperienceCommandService experienceCommandService;

    @Operation(summary = "랜딩 페이지 - 추천 카드 12개 조회", description = "비로그인 사용자는 랜덤 12개, 로그인 사용자는 희망 직무에 따라 12개 추천")
    @GetMapping
    public ApiResponse<RecommendCard> recommendCard(@AuthenticationMember Member member) {

        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        List<ExperienceCardItem> recommendCardInfo = experienceQueryService.getRecommendCard(
            member);

        return ApiResponse.onSuccess(toRecommendCard(memberInfo, recommendCardInfo));
    }

    @Operation(summary = "경험 분해 상세정보 조회", description = "특정 경험 분해 정보를 조회합니다.")
    @Parameter(name = "experienceId", description = "경험 분해 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @GetMapping("/experiences/{experienceId}")
    public ApiResponse<ExperienceResult> getExperience(@AuthenticationMember Member member,
        @PathVariable(name = "experienceId") Long experienceId) {

        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        ExperienceInfo experienceInfo = experienceQueryService.getExperience(experienceId);

        return ApiResponse.onSuccess(toExperienceResult(memberInfo, experienceInfo));
    }

    @Operation(summary = "경험 분해 작성", description = "경험 분해 작성 요청을 POST로 보냅니다.")
    @PostMapping("/experiences")
    public ApiResponse<ExperienceProc> post(@AuthenticationMember Member member,
        @RequestBody ExperienceRequest post) {

        return ApiResponse.onSuccess(experienceCommandService.post(post, member));
    }

    @Operation(summary = "경험 분해 수정", description = "경험 분해 수정 요청을 PATCH로 보냅니다.")
    @Parameter(name = "experienceId", description = "경험 분해 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @PatchMapping("/experiences{experienceId}")
    public ApiResponse<ExperienceProc> patch(@RequestBody ExperienceRequest patch,
        @PathVariable(name = "experienceId") Long experienceId) {

        return ApiResponse.onSuccess(experienceCommandService.patch(patch, experienceId));
    }

    @Operation(summary = "경험 분해 삭제", description = "경험 분해 삭제 요청을 DELETE로 보냅니다.")
    @Parameter(name = "experienceId", description = "경험 분해 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @DeleteMapping("/experiences{experienceId}")
    public ApiResponse<ExperienceProc> delete(
        @PathVariable(name = "experienceId") Long experienceId) {

        return ApiResponse.onSuccess(experienceCommandService.delete(experienceId));
    }

    @Operation(summary = "경험 카드 목록 조회", description = "경험 카드 목록(Paging) 조회 GET으로 보냅니다.")
    @Parameter(name = "page", description = "페이징 번호, page, Query String입니다.", example = "0", in = ParameterIn.QUERY)
    @GetMapping("/experience-cards")
    public ApiResponse<ExperienceCardResult> getExperienceCards(@AuthenticationMember Member member,
        @RequestParam(value = "page", defaultValue = "0") int page) {

        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);

        ExperienceCardInfo experienceCardInfo = experienceQueryService.getExperienceCardInfo(member,
            page);

        return ApiResponse.onSuccess(toExperienceCardResult(memberInfo, experienceCardInfo));
    }

}

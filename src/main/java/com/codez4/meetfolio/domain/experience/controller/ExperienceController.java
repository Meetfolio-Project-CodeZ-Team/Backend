package com.codez4.meetfolio.domain.experience.controller;

import static com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.toExperienceResult;

import com.codez4.meetfolio.domain.experience.dto.ExperienceRequest;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.ExperienceInfo;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.ExperienceProc;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.ExperienceResult;
import com.codez4.meetfolio.domain.experience.service.ExperienceCommandService;
import com.codez4.meetfolio.domain.experience.service.ExperienceQueryService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "경험 분해 API")
@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceQueryService experienceQueryService;
    private final ExperienceCommandService experienceCommandService;
    private final MemberQueryService memberQueryService;

    @Operation(summary = "경험 분해 상세정보 조회", description = "특정 경험 분해 정보를 조회합니다.")
    @Parameter(name = "experienceId", description = "경험 분해 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @GetMapping("/{experienceId}")
    public ApiResponse<ExperienceResult> getExperience(
        @PathVariable(name = "experienceId") Long experienceId) {

        // TODO: 추후 로그인 사용자로 수정
        MemberInfo memberInfo = memberQueryService.getMemberInfo(1L);
        ExperienceInfo experienceInfo = experienceQueryService.getExperience(experienceId);

        return ApiResponse.onSuccess(toExperienceResult(memberInfo, experienceInfo));
    }

    @Operation(summary = "경험 분해 작성", description = "경험 분해 작성 요청을 POST로 보냅니다.")
    @PostMapping
    public ApiResponse<ExperienceProc> post(@RequestBody ExperienceRequest.Post post) {

        // TODO: 추후 로그인 사용자로 수정 - 1L
        Member member = memberQueryService.findById(1L);

        return ApiResponse.onSuccess(experienceCommandService.post(post, member));
    }
}

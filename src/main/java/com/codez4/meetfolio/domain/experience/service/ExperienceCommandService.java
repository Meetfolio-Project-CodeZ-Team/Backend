package com.codez4.meetfolio.domain.experience.service;

import com.codez4.meetfolio.domain.experience.Experience;
import com.codez4.meetfolio.domain.experience.dto.ExperienceRequest;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.ExperienceProc;
import com.codez4.meetfolio.domain.experience.repository.ExperienceRepository;
import com.codez4.meetfolio.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExperienceCommandService {

    private final ExperienceRepository experienceRepository;

    public ExperienceProc post(ExperienceRequest.Post post, Member member) {

        // TODO: 로그인 사용자로 추후 수정
        Experience experience = experienceRepository.save(ExperienceRequest.toEntity(post, member));

        return ExperienceResponse.toExperienceProc(experience.getId());

    }
}
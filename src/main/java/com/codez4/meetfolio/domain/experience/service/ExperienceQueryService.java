package com.codez4.meetfolio.domain.experience.service;

import static com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.toExperienceCardInfo;
import static com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.toExperienceInfo;

import com.codez4.meetfolio.domain.experience.Experience;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.ExperienceCardInfo;
import com.codez4.meetfolio.domain.experience.dto.ExperienceResponse.ExperienceInfo;
import com.codez4.meetfolio.domain.experience.repository.ExperienceRepository;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExperienceQueryService {

    private final ExperienceRepository experienceRepository;

    public ExperienceInfo getExperience(Long experienceId) {

        return toExperienceInfo(findById(experienceId));
    }

    public Experience findById(Long experienceId) {
        return experienceRepository.findById(experienceId).orElseThrow(
            () -> new ApiException(ErrorStatus._EXPERIENCE_NOT_FOUND)
        );
    }

    public ExperienceCardInfo getExperienceCardInfo(Member member, int page) {

        PageRequest pageRequest = PageRequest.of(page, 6, Sort.by("createdAt").descending());

        return toExperienceCardInfo(experienceRepository.findAllByMember(member, pageRequest));
    }

}

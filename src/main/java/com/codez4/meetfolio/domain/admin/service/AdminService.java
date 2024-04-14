package com.codez4.meetfolio.domain.admin.service;

import com.codez4.meetfolio.domain.admin.dto.DashboardResponse;
import com.codez4.meetfolio.domain.analysis.repository.AnalysisRepository;
import com.codez4.meetfolio.domain.enums.Authority;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.feedback.repository.FeedbackRepository;
import com.codez4.meetfolio.domain.member.repository.MemberRepository;
import com.codez4.meetfolio.domain.payment.repository.PaymentRepository;
import com.codez4.meetfolio.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static com.codez4.meetfolio.domain.admin.dto.DashboardResponse.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final FeedbackRepository feedbackRepository;
    private final AnalysisRepository analysisRepository;

    public DashboardResponse.DashboardResult getDashboard() {
        return toDashboardResult(getAISolutionInfo(),
                getMemberInfo(),
                getPointInfo(),
                (int) paymentRepository.queryGetTotalSales());
    }


    public DashboardResponse.AISolutionInfo getAISolutionInfo() {
        long feedbackCount = feedbackRepository.countAllBy();
        long analysisCount = analysisRepository.countAllBy();
        double feedbackSatisfaction = feedbackRepository.queryGetAvgSatisfaction();
        double analysisSatisfaction = analysisRepository.queryGetAvgSatisfaction();
        double avgSatisfaction = (feedbackSatisfaction + analysisSatisfaction) / 2;
        return toAISolutionInfo((int) feedbackCount, (int) analysisCount, avgSatisfaction);
    }

    public DashboardResponse.PointInfo getPointInfo() {
        long totalPoint = pointRepository.countAllByPointTypeIsNot(PointType.CHARGE);
        long coverLetterPoint = pointRepository.countPointByPointTypeIs(PointType.USE_COVER_LETTER);
        long analysisPoint = pointRepository.countPointByPointTypeIs(PointType.USE_AI_ANALYSIS);
        return toPointInfo((int) totalPoint, (int) coverLetterPoint, (int) analysisPoint);
    }

    private DashboardResponse.MemberInfo getMemberInfo() {
        int totalMemberCount = memberRepository.countMemberByStatusAndAuthority(Status.ACTIVE, Authority.MEMBER);

        Map<JobKeyword, Integer> jobCount = new HashMap<>();
        for (JobKeyword jobKeyword : JobKeyword.values()) {
            jobCount.put(jobKeyword, getMemberCountByJobKeyword(jobKeyword));
        }
        return toMemberInfo(totalMemberCount, jobCount);
    }

    private int getMemberCountByJobKeyword(JobKeyword jobKeyword) {
        return memberRepository.countMemberByStatusAndAuthorityAndJobKeyword(Status.ACTIVE, Authority.MEMBER, jobKeyword);
    }
}

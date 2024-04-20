package com.codez4.meetfolio.domain.admin.service;

import com.codez4.meetfolio.domain.admin.dto.*;
import com.codez4.meetfolio.domain.analysis.repository.AnalysisRepository;
import com.codez4.meetfolio.domain.dataset.Dataset;
import com.codez4.meetfolio.domain.dataset.repository.DatasetRepository;
import com.codez4.meetfolio.domain.enums.*;
import com.codez4.meetfolio.domain.feedback.repository.FeedbackRepository;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.repository.MemberRepository;
import com.codez4.meetfolio.domain.model.repository.ModelRepository;
import com.codez4.meetfolio.domain.payment.Payment;
import com.codez4.meetfolio.domain.payment.repository.PaymentRepository;
import com.codez4.meetfolio.domain.point.Point;
import com.codez4.meetfolio.domain.point.repository.PointRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codez4.meetfolio.domain.admin.dto.AIServiceResponse.toAIServiceInfo;
import static com.codez4.meetfolio.domain.admin.dto.AIServiceResponse.toAIServiceResult;
import static com.codez4.meetfolio.domain.admin.dto.DashboardResponse.*;
import static com.codez4.meetfolio.domain.admin.dto.DatasetResponse.toDatasetInfo;
import static com.codez4.meetfolio.domain.admin.dto.PaymentResponse.toPaymentItem;
import static com.codez4.meetfolio.domain.admin.dto.PointResponse.toPointStatics;
import static com.codez4.meetfolio.domain.member.dto.MemberResponse.toMemberList;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminQueryService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final FeedbackRepository feedbackRepository;
    private final AnalysisRepository analysisRepository;
    private final ModelRepository modelRepository;
    private final DatasetRepository datasetRepository;

    public DashboardResponse.DashboardResult getDashboard() {
        return toDashboardResult(getAIServiceInfo(),
                getMemberInfo(),
                getPointInfo(),
                (int) paymentRepository.queryGetTotalSales());
    }

    public MemberResponse.MemberListResult getMemberList(int page, JobKeyword jobKeyword) {
        PageRequest pageRequest = PageRequest.of(page, 12, Sort.by("createdAt").descending());
        if (jobKeyword == null) {
            return toMemberList(memberRepository.findMemberByStatusAndAuthority(Status.ACTIVE, Authority.MEMBER, pageRequest));
        } else
            return toMemberList(memberRepository.findMemberByStatusAndAuthorityAndJobKeyword(Status.ACTIVE, Authority.MEMBER, jobKeyword, pageRequest));
    }

    public PointResponse.PointStatics getPointStatics(int year, int month) {
        String requestMonth = Integer.toString(year) + '-' + month;
        long totalPoint = pointRepository.countAllByPointTypeIsNot(PointType.CHARGE);
        long coverLetterPoint = pointRepository.queryGetPointSum(PointType.USE_COVER_LETTER, requestMonth);
        long analysisPoint = pointRepository.queryGetPointSum(PointType.USE_AI_ANALYSIS, requestMonth);
        String yearMonth = year + "년" + " " + month + "월";
        return toPointStatics(yearMonth, (int) coverLetterPoint, (int) analysisPoint, (int) totalPoint);
    }

    public PaymentResponse.PaymentResult getPaymentList(int page, int year, int month) {
        String requestMonth = Integer.toString(year) + '-' + month;
        long totalSales = paymentRepository.queryGetTotalSalesByMonth(requestMonth);
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        String yearMonth = year + "년" + " " + month + "월";

        Page<Payment> payments = paymentRepository.findByMember_AuthorityAndPaymentStatusIs(Authority.MEMBER, PaymentStatus.APPROVE, pageRequest);
        List<PaymentResponse.PaymentItem> paymentList = payments.stream().map(payment -> {
            Point point = pointRepository.getPointByPayment(payment).orElseThrow(() -> new ApiException(ErrorStatus._BAD_REQUEST));
            return toPaymentItem(payment, point);
        }).toList();
        return PaymentResponse.toPaymentResult(yearMonth, (int) totalSales, payments, paymentList);

    }

    public AIServiceResponse.AIServiceResult getAIServiceStatics() {
        List<AIServiceResponse.AIModelInfo> models = modelRepository.findTop3ByOrderByAccuracy().stream()
                .map(AIServiceResponse::toAIModelInfo)
                .toList();
        return toAIServiceResult(getAIServiceInfo(), models);
    }

    public DatasetResponse.DatasetInfo getDatasetList(int page) {
        PageRequest pageRequest = PageRequest.of(page, 7, Sort.by("createdAt").descending());
        Page<Dataset> datasets = datasetRepository.getDatasetByOrderByCreatedAt(pageRequest);
        return toDatasetInfo(datasets);
    }

    public AIServiceResponse.AIServiceInfo getAIServiceInfo() {
        long feedbackCount = feedbackRepository.countAllBy();
        long analysisCount = analysisRepository.countAllBy();
        double feedbackSatisfaction = feedbackRepository.queryGetAvgSatisfaction() ;
        double analysisSatisfaction = analysisRepository.queryGetAvgSatisfaction() ;
        double avgSatisfaction = (feedbackSatisfaction + analysisSatisfaction) / 2;
        return toAIServiceInfo((int) feedbackCount, (int) analysisCount, avgSatisfaction);
    }

    public DashboardResponse.PointInfo getPointInfo() {
        long totalPoint = pointRepository.countAllByPointTypeIsNot(PointType.CHARGE);
        long coverLetterPoint = pointRepository.countPointByPointTypeIs(PointType.USE_COVER_LETTER);
        long analysisPoint = pointRepository.countPointByPointTypeIs(PointType.USE_AI_ANALYSIS);
        return toPointInfo((int) totalPoint, (int) coverLetterPoint, (int) analysisPoint);
    }

    private MembersInfo getMemberInfo() {
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

package com.codez4.meetfolio.domain.admin.service;

import com.codez4.meetfolio.domain.admin.dto.*;
import com.codez4.meetfolio.domain.analysis.repository.AnalysisRepository;
import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.repository.BoardRepository;
import com.codez4.meetfolio.domain.dataset.Dataset;
import com.codez4.meetfolio.domain.dataset.repository.DatasetRepository;
import com.codez4.meetfolio.domain.enums.Authority;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.PointType;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.feedback.repository.FeedbackRepository;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.repository.MemberRepository;
import com.codez4.meetfolio.domain.model.Model;
import com.codez4.meetfolio.domain.model.repository.ModelRepository;
import com.codez4.meetfolio.domain.payment.Payment;
import com.codez4.meetfolio.domain.payment.repository.PaymentRepository;
import com.codez4.meetfolio.domain.point.Point;
import com.codez4.meetfolio.domain.point.repository.PointRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codez4.meetfolio.domain.admin.dto.AIServiceResponse.toAIServiceInfo;
import static com.codez4.meetfolio.domain.admin.dto.AIServiceResponse.toAIServiceResult;
import static com.codez4.meetfolio.domain.admin.dto.BoardResponse.toBoardAdminResult;
import static com.codez4.meetfolio.domain.admin.dto.DashboardResponse.toDashboardResult;
import static com.codez4.meetfolio.domain.admin.dto.DashboardResponse.toMemberInfo;
import static com.codez4.meetfolio.domain.admin.dto.DatasetResponse.toDatasetWithModel;
import static com.codez4.meetfolio.domain.admin.dto.ModelResponse.toModelListResult;
import static com.codez4.meetfolio.domain.admin.dto.ModelResponse.toModelResult;
import static com.codez4.meetfolio.domain.admin.dto.PaymentResponse.toPaymentItem;
import static com.codez4.meetfolio.domain.admin.dto.PointResponse.toPointStatics;
import static com.codez4.meetfolio.domain.member.dto.MemberResponse.toMemberList;

@Slf4j
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
    private final BoardRepository boardRepository;

    public DashboardResponse.DashboardResult getDashboard() {
        return toDashboardResult(getAIServiceInfo(),
                getMemberInfo(),
                getPointStatics(LocalDate.now(ZoneId.of("Asia/Seoul")).getYear(), LocalDate.now(ZoneId.of("Asia/Seoul")).getMonthValue()),
                (int) paymentRepository.queryGetTotalSales());
    }

    public MemberResponse.MemberListResult getMemberList(int page, JobKeyword jobKeyword) {
        PageRequest pageRequest = PageRequest.of(page, 8, Sort.by("id").descending());
        if (jobKeyword == null) {
            return toMemberList(memberRepository.findMemberByAuthorityAndEmailIsNot(Authority.MEMBER, "", pageRequest));
        } else
            return toMemberList(memberRepository.findMemberByAuthorityAndJobKeywordAndEmailIsNot(Authority.MEMBER, jobKeyword, "",pageRequest));
    }

    public MemberResponse.MemberListResult getMemberListByKeyword(int page, String keyword) {
        PageRequest pageRequest = PageRequest.of(page, 8, Sort.by("id").descending());
        return toMemberList(memberRepository.queryFindMemberByAuthorityAndKeywordAndEmailIsNotEmpty(Authority.MEMBER, keyword, pageRequest));
    }

    public PointResponse.PointStatics getPointStatics(int year, int month) {
        String requestMonth = Integer.toString(year) + '-' + month;
        long totalPoint = pointRepository.queryGetAllPointSum(PointType.CHARGE, requestMonth);
        long coverLetterPoint = pointRepository.queryGetPointSum(PointType.USE_COVER_LETTER, requestMonth);
        long analysisPoint = pointRepository.queryGetPointSum(PointType.USE_AI_ANALYSIS, requestMonth);
        return toPointStatics(year + "년" + " " + month + "월", (int) coverLetterPoint, (int) analysisPoint, (int) totalPoint);
    }

    public PaymentResponse.PaymentResult getPaymentList(int page, int year, int month) {
        String requestMonth = Integer.toString(year) + '-' + month;
        long totalSales = paymentRepository.queryGetTotalSalesByMonth(requestMonth);
        String yearMonth = year + "년" + " " + month + "월";

        PageRequest pageRequest = PageRequest.of(page, 10,  Sort.by("id").descending());
        Page<Payment> payments = paymentRepository.queryGetSalesByMonth(requestMonth, pageRequest);

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

    public DatasetResponse.DatasetWithModel getDatasetWithModel(int page) {
        PageRequest pageRequest = PageRequest.of(page, 8, Sort.by("id").descending());
        Page<Dataset> datasets = datasetRepository.getAllBy(pageRequest);

        Integer trainableNumber = datasetRepository.countByStatus(Status.INACTIVE);

        Model model = modelRepository.findModelByStatus(Status.ACTIVE).orElseThrow(
            () -> new ApiException(ErrorStatus._MODEL_NOT_FOUND)
        );

        return toDatasetWithModel(datasets, trainableNumber, model);
    }

    public AIServiceResponse.AIServiceInfo getAIServiceInfo() {
        long feedbackCount = feedbackRepository.countAllBy();
        long analysisCount = analysisRepository.countAllBy();
        double feedbackSatisfaction = feedbackRepository.queryGetAvgSatisfaction();
        double analysisSatisfaction = analysisRepository.queryGetAvgSatisfaction();
        double avgSatisfaction = (feedbackSatisfaction + analysisSatisfaction) / 2;
        return toAIServiceInfo((int) feedbackCount, (int) analysisCount, avgSatisfaction);
    }

    public BoardResponse.BoardAdminResult getBoardsByKeyword(String keyword, int page) {
        PageRequest pageRequest = PageRequest.of(page, 12, Sort.by("id").descending());
        String search = keyword == null ? "" : keyword;
        Page<Board> boards = boardRepository.queryFindBoardsByKeyword(search,pageRequest);
        return toBoardAdminResult(boards);
    }

    public ModelResponse.ModelListResult getModelsInfo(int page){
        PageRequest pageRequest = PageRequest.of(page, 12, Sort.by("createdAt").descending());
        Page<Model> models = modelRepository.findAllByVersionStatusIsNull(pageRequest);
        return toModelListResult(models);
    }

    public ModelResponse.ModelResult getModelInfo(Model model){
        return toModelResult(model);
    }

    private DashboardResponse.MembersInfo getMemberInfo() {
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

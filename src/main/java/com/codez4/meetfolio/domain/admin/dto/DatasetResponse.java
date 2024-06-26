package com.codez4.meetfolio.domain.admin.dto;

import com.codez4.meetfolio.domain.admin.dto.ModelResponse.ModelResult;
import com.codez4.meetfolio.domain.dataset.Dataset;
import com.codez4.meetfolio.domain.model.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class DatasetResponse {

    @Schema(description = "관리자 - 학습 데이터 리스트 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DatasetInfo {

        @Schema(description = "데이터셋 리스트")
        List<DatasetItem> datasetInfo;

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

    @Schema(description = "관리자 - 학습 데이터 리스트 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DatasetItem {

        @Schema(description = "생성 일시")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;

        @Schema(description = "지원 직무")
        private String job;

        @Schema(description = "데이터 수집 사이트")
        private String domain;

        @Schema(description = "데이터 수집 url")
        private String url;

    }

    @Schema(description = "관리자 - 데이터셋 저장 완료 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DatasetProc {

        @Schema(description = "데이터셋 ID")
        private Long datasetId;

        @Schema(description = "데이터 생성 시각")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
    }

    public static DatasetItem toDatasetItem(Dataset dataset) {
        return DatasetItem.builder()
            .createdAt(dataset.getCreatedAt())
            .job(dataset.getJobKeyword().getDescription())
            .domain(dataset.getDomain())
            .url(dataset.getUrl())
            .build();
    }

    public static DatasetInfo toDatasetInfo(Page<Dataset> datasets) {
        List<DatasetItem> datasetList = datasets.stream().map(DatasetResponse::toDatasetItem)
            .toList();
        return DatasetInfo.builder()
            .datasetInfo(datasetList)
            .listSize(datasetList.size())
            .totalElements(datasets.getTotalElements())
            .totalPage(datasets.getTotalPages())
            .isFirst(datasets.isFirst())
            .isLast(datasets.isLast())
            .build();
    }

    public static DatasetProc toDatasetProc(Dataset dataset) {
        return DatasetProc.builder()
            .datasetId(dataset.getId())
            .createdAt(dataset.getCreatedAt())
            .build();
    }

    @Schema(description = "관리자 - 학습 데이터셋 & 훈련 가능한 데이터 개수 & 활성화된 모델 정보 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DatasetWithModel {

        private DatasetInfo datasetInfo;

        @Schema(description = "훈련 가능한 데이터셋의 개수")
        private int trainableNumber;

        private ModelResult modelResult;

    }

    public static DatasetWithModel toDatasetWithModel(Page<Dataset> datasets, int trainableNumber,
        Model model) {

        DatasetInfo datasetInfo = toDatasetInfo(datasets);

        ModelResult modelResult = ModelResponse.toModelResult(model);

        return DatasetWithModel.builder()
            .datasetInfo(datasetInfo)
            .trainableNumber(trainableNumber)
            .modelResult(modelResult)
            .build();

    }
}


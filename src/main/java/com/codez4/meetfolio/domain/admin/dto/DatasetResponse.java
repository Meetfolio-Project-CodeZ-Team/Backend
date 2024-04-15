package com.codez4.meetfolio.domain.admin.dto;

import com.codez4.meetfolio.domain.dataset.Dataset;
import com.codez4.meetfolio.domain.enums.JobKeyword;
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

    @Schema(description = "관리자 - AI 서비스 통계 모델 정보 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DatasetInfo {
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

    @Schema(description = "관리자 - AI 서비스 통계 모델 정보 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DatasetItem{
        @Schema(description = "결제 일시")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        private String job;

        private String domain;

        private String url;
    }

    @Schema(description = "관리자 - AI 서비스 통계 모델 정보 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DatasetProc{
        @Schema(description = "데이터셋 ID")
        private Long datasetId;

        @Schema(description = "데이터 생성 시각")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
    }

    public static DatasetItem toDatasetItem(Dataset dataset){
        return DatasetItem.builder()
                .job(dataset.getJobKeyword().getDescription())
                .domain(dataset.getDomain())
                .url(dataset.getUrl())
                .build();
    }

    public static DatasetInfo toDatasetInfo(Page<Dataset> datasets){
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

    public static DatasetProc toDatasetProc(Dataset dataset){
        return DatasetProc.builder()
                .datasetId(dataset.getId())
                .createdAt(dataset.getCreatedAt())
                .build();
    }
}


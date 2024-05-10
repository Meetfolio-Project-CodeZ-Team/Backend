package com.codez4.meetfolio.domain.admin.dto;

import com.codez4.meetfolio.domain.enums.Status;
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

public class ModelResponse {

    @Schema(description = "모델 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ModelListResult {

        List<ModelItem> modelInfo;

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

    @Schema(description = "모델 목록 - 정보 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
        public static class ModelItem {
        @Schema(description = "모델 id")
        private Long modelId;
        @Schema(description = "모델 버전")
        private double version;
        @Schema(description = "모델명")
        private String modelName;
        @Schema(description = "성능")
        private double accuracy;
        @Schema(description = "모델 배포 여부")
        private Status status;
        @Schema(description = "모델 학습일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime learnedDate;
    }

    @Schema(description = "모델 정보 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ModelResult {
        @Schema(description = "모델 id")
        private Long modelId;
        @Schema(description = "모델 버전")
        private double version;
        @Schema(description = "모델명")
        private String modelName;
        @Schema(description = "파일명")
        private String fileName;
        @Schema(description = "파일 경로")
        private String filePath;
        @Schema(description = "모델 활성화 여부")
        private Status status;
        @Schema(description = "생성일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime createdDate;
        @Schema(description = "활성일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime activatedDate;
    }

    @Schema(description = "모델 수정/삭제 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ModelProc {
        @Schema(description = "모델 id")
        private Long modelId;
        @Schema(description = "응답 DTO 생성 시간")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
    }

    public static ModelListResult toModelListResult(Page<Model> models) {
        List<ModelItem> modelResults = models.stream().map(ModelResponse::toModelItem).toList();
        return ModelListResult.builder()
                .modelInfo(modelResults)
                .listSize(models.getSize())
                .totalElements(models.getTotalElements())
                .totalPage(models.getTotalPages())
                .isFirst(models.isFirst())
                .isLast(models.isLast())
                .build();
    }

    public static ModelItem toModelItem(Model model){
        return ModelItem.builder()
                .modelId(model.getId())
                .version(model.getVersion())
                .modelName(model.getName())
                .accuracy(model.getAccuracy())
                .learnedDate(model.getCreatedAt())
                .status(model.getStatus())
                .build();
    }

    public static ModelResult toModelResult(Model model) {
        return ModelResult.builder()
                .modelId(model.getId())
                .version(model.getVersion())
                .modelName(model.getName())
                .fileName(model.getFileName())
                .filePath(model.getFilePath())
                .status(model.getStatus())
                .createdDate(model.getCreatedAt())
                .activatedDate(model.getActivatedDate())
                .build();
    }

    public static ModelProc toModelProc(Model model){
        return ModelProc.builder()
                .modelId(model.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}

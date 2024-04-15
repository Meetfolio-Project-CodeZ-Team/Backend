package com.codez4.meetfolio.domain.admin.dto;

import com.codez4.meetfolio.domain.dataset.Dataset;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.global.annotation.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "데이터셋 저장 Request dto")
@Getter
public class DatasetRequest {
    private String domain;
    private String url;
    private String data;
    @EnumValid(enumClass = JobKeyword.class)
    private String job;

    public static Dataset toEntity(DatasetRequest request){
        return Dataset.builder()
                .domain(request.domain)
                .url(request.url)
                .data(request.data)
                .jobKeyword(JobKeyword.convert(request.job))
                .build();
    }
}

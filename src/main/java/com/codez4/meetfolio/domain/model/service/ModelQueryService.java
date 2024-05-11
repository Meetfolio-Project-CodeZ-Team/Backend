package com.codez4.meetfolio.domain.model.service;

import com.codez4.meetfolio.domain.model.Model;
import com.codez4.meetfolio.domain.model.repository.ModelRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ModelQueryService {
    private final ModelRepository modelRepository;

    public Model findById(Long modelId){
        return modelRepository.findById(modelId).orElseThrow(()-> new ApiException(ErrorStatus._MODEL_NOT_FOUND));
    }

}

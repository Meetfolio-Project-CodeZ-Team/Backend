package com.codez4.meetfolio.domain.admin.service;

import com.codez4.meetfolio.domain.admin.dto.DatasetRequest;
import com.codez4.meetfolio.domain.admin.dto.DatasetResponse;
import com.codez4.meetfolio.domain.admin.dto.ModelResponse;
import com.codez4.meetfolio.domain.dataset.repository.DatasetRepository;
import com.codez4.meetfolio.domain.model.Model;
import com.codez4.meetfolio.domain.model.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.codez4.meetfolio.domain.admin.dto.DatasetRequest.toEntity;
import static com.codez4.meetfolio.domain.admin.dto.DatasetResponse.toDatasetProc;
import static com.codez4.meetfolio.domain.admin.dto.ModelResponse.toModelProc;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCommandService {
    private final DatasetRepository datasetRepository;
    private final ModelRepository modelRepository;

    public DatasetResponse.DatasetProc saveDataset(DatasetRequest request) {
        return toDatasetProc(datasetRepository.save(toEntity(request)));
    }

    public ModelResponse.ModelProc deleteModel(Model model){
        model.softDelete();
        return toModelProc(model);
    }
}

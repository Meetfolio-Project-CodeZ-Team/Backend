package com.codez4.meetfolio.domain.admin.service;

import com.codez4.meetfolio.domain.admin.dto.DatasetRequest;
import com.codez4.meetfolio.domain.admin.dto.DatasetResponse;
import com.codez4.meetfolio.domain.admin.dto.ModelResponse;
import com.codez4.meetfolio.domain.board.repository.BoardRepository;
import com.codez4.meetfolio.domain.comment.repository.CommentRepository;
import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.coverLetter.repository.CoverLetterRepository;
import com.codez4.meetfolio.domain.dataset.repository.DatasetRepository;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.experience.repository.ExperienceRepository;
import com.codez4.meetfolio.domain.like.repository.LikeRepository;
import com.codez4.meetfolio.domain.member.Member;
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

    public ModelResponse.ModelProc changeActiveModel(Model model){
        modelRepository.findModelByStatus(Status.ACTIVE).ifPresent(Model::inactivate);
        model.activate();
        return toModelProc(model);
    }

    public ModelResponse.ModelProc deleteModel(Model model){
        modelRepository.delete(model);
        return toModelProc(model);
    }
}

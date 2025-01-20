package com.reson8.app.mapper;

import com.reson8.app.dto.PracticeRoutineDTO;
import com.reson8.app.model.PracticeRoutine;
import org.springframework.stereotype.Component;

@Component
public class PracticeRoutineMapper {
  public PracticeRoutine toEntity(PracticeRoutineDTO dto) {
    PracticeRoutine entity = new PracticeRoutine();
    entity.setId(dto.getId());
    entity.setTitle(dto.getTitle());
    entity.setDescription(dto.getDescription());
    entity.setCreatedDate(dto.getCreatedDate());
    entity.setCategory(dto.getCategory());

    return entity;
  }

  public PracticeRoutineDTO toDto(PracticeRoutine entity) {
    PracticeRoutineDTO dto = new PracticeRoutineDTO();
    dto.setId(entity.getId());
    dto.setTitle(entity.getTitle());
    dto.setDescription(entity.getDescription());
    dto.setCreatedDate(entity.getCreatedDate());
    dto.setCategory(entity.getCategory());

    return dto;
  }
}

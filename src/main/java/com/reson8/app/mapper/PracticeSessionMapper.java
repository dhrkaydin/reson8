package com.reson8.app.mapper;

import com.reson8.app.dto.PracticeSessionDTO;
import com.reson8.app.model.PracticeSession;
import org.springframework.stereotype.Component;

@Component
public class PracticeSessionMapper {
  public PracticeSessionDTO toDto(PracticeSession entity) {
    PracticeSessionDTO dto = new PracticeSessionDTO();
    dto.setId(entity.getId());
    dto.setBpm(entity.getBpm());
    dto.setDuration(entity.getDuration());
    dto.setSessionDate(entity.getSessionDate());
    dto.setPracticeRoutineId(entity.getPracticeRoutine().getId());
    return dto;
  }

  public PracticeSession toEntity(PracticeSessionDTO dto) {
    PracticeSession entity = new PracticeSession();
    entity.setId(dto.getId());
    entity.setBpm(dto.getBpm());
    entity.setDuration(dto.getDuration());
    entity.setSessionDate(dto.getSessionDate());
    // practiceRoutine should be set in the service layer
    return entity;
  }

}

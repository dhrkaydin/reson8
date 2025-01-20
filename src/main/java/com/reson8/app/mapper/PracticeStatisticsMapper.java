package com.reson8.app.mapper;

import com.reson8.app.dto.PracticeStatisticsDTO;
import com.reson8.app.model.PracticeStatistics;
import org.springframework.stereotype.Component;

@Component
public class PracticeStatisticsMapper {
  public PracticeStatisticsDTO toDto(PracticeStatistics stats) {
    PracticeStatisticsDTO dto = new PracticeStatisticsDTO();
    dto.setTotalPracticeTime(stats.getTotalPracticeTime());
    dto.setTotalSessions(stats.getTotalSessions());
    dto.setHighestBPM(stats.getHighestBPM());
    dto.setTotalBPMIncrease(stats.getTotalBPMIncrease());
    dto.setPracticeRoutineId(stats.getPracticeRoutine().getId());
    return dto;
  }

  public PracticeStatistics toEntity(PracticeStatisticsDTO dto) {
    PracticeStatistics stats = new PracticeStatistics();
    stats.setTotalPracticeTime(dto.getTotalPracticeTime());
    stats.setTotalSessions(dto.getTotalSessions());
    stats.setHighestBPM(dto.getHighestBPM());
    stats.setTotalBPMIncrease(dto.getTotalBPMIncrease());
    // practiceRoutine should be set in the service layer.
    return stats;
  }
}

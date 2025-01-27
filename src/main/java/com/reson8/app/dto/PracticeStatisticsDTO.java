package com.reson8.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PracticeStatisticsDTO {
  private Long id;
  @NotNull
  private int totalPracticeTime;
  @NotNull
  private int totalSessions;
  @NotNull
  private int highestBPM;
  @NotNull
  private int totalBPMIncrease;
  @NotNull
  private Long practiceRoutineId;
}

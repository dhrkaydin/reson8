package com.reson8.app.dto;

import lombok.Data;

@Data
public class PracticeStatisticsDTO {
  private Long id;
  private int totalPracticeTime;
  private int totalSessions;
  private int highestBPM;
  private int totalBPMIncrease;
  private Long practiceRoutineId;
}

package com.reson8.app.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PracticeSessionDTO {
  private Long id;
  private LocalDate sessionDate;
  private int bpm;
  private int duration;
  private Long practiceRoutineId;
}

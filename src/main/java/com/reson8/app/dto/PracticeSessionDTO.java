package com.reson8.app.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PracticeSessionDTO {
  private Long id;
  @NotNull
  private LocalDate sessionDate;
  @NotNull
  private int bpm;
  @NotNull
  private int duration;
  @NotNull
  private Long practiceRoutineId;
}

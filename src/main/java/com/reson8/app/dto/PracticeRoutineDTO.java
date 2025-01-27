package com.reson8.app.dto;

import com.reson8.app.model.Category;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PracticeRoutineDTO {
  private Long id;
  @NotNull
  private String title;
  @NotNull
  private String description;
  @NotNull
  private LocalDate createdDate;
  @NotNull
  private Category category;
  private Integer targetBPM;
  private Integer targetFrequencyInterval;
  private String targetFrequencyUnit;
}

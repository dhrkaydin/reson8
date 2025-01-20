package com.reson8.app.dto;

import com.reson8.app.model.Category;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PracticeRoutineDTO {
  private Long id;
  private String title;
  private String description;
  private LocalDate createdDate;
  private Category category;
  private Integer targetBPM;
  private Integer targetFrequencyInterval;
  private String targetFrequencyUnit;
}

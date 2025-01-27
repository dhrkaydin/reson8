package com.reson8.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PracticeRoutineTitleDTO {
  @NotNull
  private Long id;
  @NotNull
  private String title;
}

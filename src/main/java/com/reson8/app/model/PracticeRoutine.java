package com.reson8.app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

/**
 * Contains the basics for each routine, a category, title, description, date, and tabs. Also has a unique ID.
 */
@Data
@Entity
public class PracticeRoutine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Title can not be null")
  @Size(min = 1, message = "Title can not be empty")
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @NotNull(message = "Date can not be null")
  @Column(name = "created_date")
  private LocalDate createdDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private Category category;

  // all 3 nullable/optional
  @Column(name = "target_bpm")
  private Integer targetBPM;

  @Column(name = "target_frequency_interval")
  private Integer targetFrequencyInterval;

  @Column(name = "target_frequency_unit")
  private String targetFrequencyUnit;

  @OneToMany(mappedBy = "practiceRoutine", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PracticeSession> sessions;
  @OneToOne(mappedBy = "practiceRoutine", cascade = CascadeType.ALL)
  private PracticeStatistics statistics;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    PracticeRoutine that = (PracticeRoutine) obj;
    return id != null && id.equals(that.id);
  }
}
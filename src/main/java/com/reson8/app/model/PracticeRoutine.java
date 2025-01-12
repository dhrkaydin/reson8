package com.reson8.app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
  private String title;
  private String description;
  private String tabNotation;
  private LocalDate createdDate;
  @Enumerated(EnumType.STRING)
  private Category category;

  // all 3 nullable/optional
  private Integer targetBPM;
  private Integer targetFrequencyInterval;
  private String targetFrequencyUnit;

  @OneToMany(mappedBy = "practiceRoutine", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PracticeSession> sessions;
  @OneToOne(mappedBy = "practiceRoutine", cascade = CascadeType.ALL)
  private PracticeStatistics statistics;
}
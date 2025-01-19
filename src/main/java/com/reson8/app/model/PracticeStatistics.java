package com.reson8.app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class PracticeStatistics {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "total_practice_time")
  private int totalPracticeTime;

  @Column(name = "total_sessions")
  private int totalSessions;

  @Column(name = "highest_bpm")
  private int highestBPM;

  @Column(name = "total_bpm_increase")
  private int totalBPMIncrease;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "practice_routine_id")
  private PracticeRoutine practiceRoutine;
}
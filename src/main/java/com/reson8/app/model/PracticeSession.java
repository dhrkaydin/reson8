package com.reson8.app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Linked to a PracticeRoutine, a PracticeSession contains more session-relevant information: the BPM used to practice, the duration, and the date of the session itself.
 */
@Data
@Entity
public class PracticeSession {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime sessionDate;
  private int bpm;
  private int duration;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "practice_routine_id")
  private PracticeRoutine practiceRoutine;

}
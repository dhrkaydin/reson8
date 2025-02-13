package com.reson8.app.repository;

import com.reson8.app.model.PracticeSession;
import com.reson8.app.model.PracticeRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

import java.util.List;

@Repository
public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {
  List<PracticeSession> findByPracticeRoutine(PracticeRoutine routine);
  List<PracticeSession> findByPracticeRoutineId(Long routineId);
  List<PracticeSession> findByPracticeRoutineIdAndSessionDateBetween(Long practiceRoutineId, LocalDate startDate, LocalDate endDate);
  List<PracticeSession> findBySessionDateBetween(LocalDate startDate, LocalDate endDate);

}
package com.reson8.app.repository;

import com.reson8.app.model.PracticeStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeStatisticsRepository extends JpaRepository<PracticeStatistics, Long> {
  PracticeStatistics findByPracticeRoutineId(Long routineId);
}
package com.reson8.app.repository;

import com.reson8.app.model.PracticeRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeRoutineRepository extends JpaRepository<PracticeRoutine, Long> {
}
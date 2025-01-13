package com.reson8.app.service;

import com.reson8.app.model.PracticeRoutine;
import com.reson8.app.model.PracticeStatistics;
import com.reson8.app.repository.PracticeRoutineRepository;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PracticeRoutineService {

  @Autowired
  private PracticeRoutineRepository routineRepository;

  @Autowired
  private PracticeSessionService sessionService;
  @Autowired
  private PracticeStatisticsService statisticsService;

  public PracticeRoutine createRoutine(PracticeRoutine routine) {
    return routineRepository.save(routine);
  }

  public PracticeRoutine getRoutine(Long routineId) {
    return routineRepository.findById(routineId)
        .orElseThrow(() -> new NoSuchElementException("Could not get routine, routine not found"));
  }

  //TODO: Make this dynamic based on the changed fields in the PracticeRoutine object. Might be overkill though.
  public PracticeRoutine updateRoutine(Long routineId, PracticeRoutine routine) {
    return routineRepository.findById(routineId)
        .map(existingRoutine -> {
          existingRoutine.setTitle(routine.getTitle());
          existingRoutine.setDescription(routine.getDescription());
          existingRoutine.setCategory(routine.getCategory());
          existingRoutine.setTabNotation(routine.getTabNotation());

          // Sessions and statistics should be updated through their respective services.

          return routineRepository.save(existingRoutine);
        })
        .orElseThrow(() -> new RuntimeException("Could not update routine, routine not found"));
  }

  @Transactional
  public void deleteRoutine(Long routineId) {
    routineRepository.deleteById(routineId);
  }
}

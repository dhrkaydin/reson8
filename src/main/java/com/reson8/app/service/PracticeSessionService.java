package com.reson8.app.service;

import com.reson8.app.model.PracticeRoutine;
import com.reson8.app.model.PracticeSession;
import com.reson8.app.model.PracticeStatistics;
import com.reson8.app.repository.PracticeRoutineRepository;
import com.reson8.app.repository.PracticeSessionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PracticeSessionService {

  @Autowired
  private PracticeSessionRepository sessionRepository;
  @Autowired
  private PracticeRoutineRepository routineRepository;

  @Autowired
  private PracticeStatisticsService statsService;

  //TODO: Update the stats in an async way or look into a more optimal way.
  public PracticeSession createSession(PracticeSession session) {
    PracticeSession createdSession = sessionRepository.save(session);
    updateStatsForSession(createdSession);
    return createdSession;
  }

  public List<PracticeSession> getSessions(Long routineId) {
    PracticeRoutine routine = routineRepository.findById(routineId)
        .orElseThrow(() -> new RuntimeException("Routine not found"));
    return sessionRepository.findByPracticeRoutine(routine);
  }

  public PracticeSession updateSession(Long sessionId, PracticeSession updatedSession) {
    return sessionRepository.findById(sessionId)
        .map(existingSession -> {
          existingSession.setBpm(updatedSession.getBpm());
          existingSession.setDuration(updatedSession.getDuration());
          existingSession.setSessionDate(updatedSession.getSessionDate());

          if (!existingSession.getPracticeRoutine().equals(updatedSession.getPracticeRoutine())) {
            PracticeRoutine oldRoutine = existingSession.getPracticeRoutine();
            PracticeRoutine newRoutine = updatedSession.getPracticeRoutine();

            oldRoutine.getSessions().remove(existingSession);
            existingSession.setPracticeRoutine(newRoutine);
            newRoutine.getSessions().add(existingSession);
          }

          PracticeSession savedSession = sessionRepository.save(existingSession);
          updateStatsForSession(savedSession);

          return savedSession;
        })
        .orElseThrow(() -> new RuntimeException("Session not found"));
  }

  public void updateStatsForSession(PracticeSession session) {
    statsService.updateStats(session.getPracticeRoutine().getId());
  }
}
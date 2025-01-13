package com.reson8.app.service;

import com.reson8.app.model.PracticeRoutine;
import com.reson8.app.model.PracticeSession;
import com.reson8.app.repository.PracticeRoutineRepository;
import com.reson8.app.repository.PracticeSessionRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PracticeSessionService {

  @Autowired
  private PracticeSessionRepository sessionRepo;
  @Autowired
  private PracticeRoutineRepository routineRepo;

  @Autowired
  private PracticeStatisticsService statsService;

  //TODO: Update the stats in an async way or look into a more optimal way.
  public PracticeSession createSession(PracticeSession session) {
    PracticeSession createdSession = sessionRepo.save(session);
    updateStatsForSession(createdSession);
    return createdSession;
  }

  public List<PracticeSession> getSessions(Long routineId) {
    PracticeRoutine routine = routineRepo.findById(routineId)
        .orElseThrow(() -> new NoSuchElementException("Routine not found"));
    return sessionRepo.findByPracticeRoutine(routine);
  }

  public PracticeSession updateSession(Long sessionId, PracticeSession updatedSession) {
    return sessionRepo.findById(sessionId)
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

          PracticeSession savedSession = sessionRepo.save(existingSession);
          updateStatsForSession(savedSession);

          return savedSession;
        })
        .orElseThrow(() -> new RuntimeException("Session not found"));
  }

  public void deleteSession(Long sessionId) {
    sessionRepo.deleteById(sessionId);
  }

  public void updateStatsForSession(PracticeSession session) {
    statsService.updateStats(session.getPracticeRoutine().getId());
  }
}
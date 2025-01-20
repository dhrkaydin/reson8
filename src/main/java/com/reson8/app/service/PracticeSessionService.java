package com.reson8.app.service;

import com.reson8.app.dto.PracticeSessionDTO;
import com.reson8.app.mapper.PracticeSessionMapper;
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
  @Autowired
  private PracticeSessionMapper mapper;

  // TODO: Update the stats in an async way or look into a more optimal way.
  public PracticeSessionDTO createSession(PracticeSessionDTO session) {
    PracticeRoutine routine = routineRepo.findById(session.getPracticeRoutineId())
        .orElseThrow(() -> new NoSuchElementException("Routine not found"));

    PracticeSession entity = mapper.toEntity(session);
    entity.setPracticeRoutine(routine);

    PracticeSession createdSession = sessionRepo.save(entity);
    updateStatsForSession(createdSession);
    return mapper.toDto(createdSession);
  }

  public List<PracticeSessionDTO> getSessions(Long routineId) {
    return sessionRepo.findByPracticeRoutineId(routineId).stream()
        .map(mapper::toDto)
        .toList();
  }

  public PracticeSessionDTO updateSession(Long sessionId, PracticeSessionDTO updatedSession) {
    return sessionRepo.findById(sessionId)
        .map(session -> {
          session.setBpm(updatedSession.getBpm());
          session.setDuration(updatedSession.getDuration());
          session.setSessionDate(updatedSession.getSessionDate());
          PracticeSession updated = sessionRepo.save(session);

          updateStatsForSession(updated);
          return mapper.toDto(updated);
        })
        .orElseThrow(() -> new NoSuchElementException("Can not update: Session not found"));
  }

  public void deleteSession(Long sessionId) {
    sessionRepo.deleteById(sessionId);
  }

  public void updateStatsForSession(PracticeSession session) {
    statsService.updateStats(session.getId());
  }
}
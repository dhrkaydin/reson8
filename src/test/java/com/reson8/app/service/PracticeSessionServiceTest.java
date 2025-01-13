package com.reson8.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.reson8.app.model.Category;
import com.reson8.app.model.PracticeRoutine;
import com.reson8.app.model.PracticeSession;
import com.reson8.app.repository.PracticeRoutineRepository;
import com.reson8.app.repository.PracticeSessionRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PracticeSessionServiceTest {

  @Mock
  private PracticeSessionRepository sessionRepository;

  @Mock
  private PracticeRoutineRepository routineRepository;

  @Mock
  private PracticeStatisticsService statsService;

  @InjectMocks
  private PracticeSessionService sessionService;

  private PracticeRoutine sampleRoutine;
  private PracticeSession sampleSession;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    sampleRoutine = new PracticeRoutine();
    sampleRoutine.setId(1L);
    sampleRoutine.setTitle("Test Routine");
    sampleRoutine.setCategory(Category.CHORDS);

    sampleSession = new PracticeSession();
    sampleSession.setId(1L);
    sampleSession.setPracticeRoutine(sampleRoutine);
    sampleSession.setSessionDate(LocalDate.of(2025, 1, 12));
    sampleSession.setBpm(120);
    sampleSession.setDuration(30);
  }

  @Test
  void createSession_ShouldReturnSavedSession() {
    when(sessionRepository.save(sampleSession)).thenReturn(sampleSession);

    PracticeSession createdSession = sessionService.createSession(sampleSession);

    assertNotNull(createdSession);
    assertEquals(1L, createdSession.getId());
    assertEquals(LocalDate.of(2025, 1, 12), createdSession.getSessionDate());
    assertEquals(120, createdSession.getBpm());
    assertEquals(30, createdSession.getDuration());
    verify(sessionRepository, times(1)).save(sampleSession);
    verify(statsService, times(1)).updateStats(sampleRoutine.getId());
  }

  @Test
  void getSessions_ShouldReturnSessionsForRoutine() {
    List<PracticeSession> sessions = Arrays.asList(sampleSession);
    when(routineRepository.findById(1L)).thenReturn(Optional.of(sampleRoutine));
    when(sessionRepository.findByPracticeRoutine(sampleRoutine)).thenReturn(sessions);

    List<PracticeSession> result = sessionService.getSessions(1L);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(sampleSession, result.get(0));
    verify(routineRepository, times(1)).findById(1L);
    verify(sessionRepository, times(1)).findByPracticeRoutine(sampleRoutine);
  }

  @Test
  void getSessions_ShouldThrowException_WhenRoutineNotFound() {
    when(routineRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      sessionService.getSessions(1L);
    });

    assertEquals("Routine not found", exception.getMessage());
    verify(routineRepository, times(1)).findById(1L);
  }

  @Test
  void updateSession_ShouldUpdateAndReturnUpdatedSession() {
    PracticeSession updatedSession = new PracticeSession();
    updatedSession.setId(1L);
    updatedSession.setPracticeRoutine(sampleRoutine);
    updatedSession.setSessionDate(LocalDate.of(2025, 1, 13));
    updatedSession.setBpm(130);
    updatedSession.setDuration(40);

    when(sessionRepository.findById(1L)).thenReturn(Optional.of(sampleSession));
    when(sessionRepository.save(any(PracticeSession.class))).thenReturn(updatedSession);

    PracticeSession result = sessionService.updateSession(1L, updatedSession);

    assertNotNull(result);
    assertEquals(LocalDate.of(2025, 1, 13), result.getSessionDate());
    assertEquals(130, result.getBpm());
    assertEquals(40, result.getDuration());
    verify(sessionRepository, times(1)).save(updatedSession);
    verify(statsService, times(1)).updateStats(sampleRoutine.getId());
  }

  @Test
  void updateSession_ShouldThrowException_WhenSessionNotFound() {
    when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      sessionService.updateSession(1L, sampleSession);
    });

    assertEquals("Session not found", exception.getMessage());
    verify(sessionRepository, times(1)).findById(1L);
  }

  @Test
  void updateStatsForSession_ShouldCallStatsService() {
    when(sessionRepository.save(sampleSession)).thenReturn(sampleSession);

    sessionService.createSession(sampleSession);

    verify(statsService, times(1)).updateStats(sampleRoutine.getId());
  }
}
package com.reson8.app.service;

import com.reson8.app.dto.PracticeSessionDTO;
import com.reson8.app.mapper.PracticeSessionMapper;
import com.reson8.app.model.PracticeRoutine;
import com.reson8.app.model.PracticeSession;
import com.reson8.app.repository.PracticeRoutineRepository;
import com.reson8.app.repository.PracticeSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PracticeSessionServiceTest {

  @Mock
  private PracticeSessionRepository sessionRepo;

  @Mock
  private PracticeRoutineRepository routineRepo;

  @Mock
  private PracticeStatisticsService statsService;

  @Mock
  private PracticeSessionMapper mapper;

  @InjectMocks
  private PracticeSessionService sessionService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createSession_ShouldSaveAndReturnSessionDTO() {
    // Arrange
    Long routineId = 1L;
    PracticeRoutine practiceRoutine = new PracticeRoutine();
    practiceRoutine.setId(routineId);

    PracticeSessionDTO sessionDTO = new PracticeSessionDTO();
    sessionDTO.setPracticeRoutineId(routineId);
    sessionDTO.setBpm(120);
    sessionDTO.setDuration(30);
    sessionDTO.setSessionDate(LocalDate.now());

    PracticeSession sessionEntity = new PracticeSession();
    sessionEntity.setBpm(120);
    sessionEntity.setDuration(30);
    sessionEntity.setSessionDate(LocalDate.now());
    sessionEntity.setPracticeRoutine(practiceRoutine);

    PracticeSession savedSession = new PracticeSession();
    savedSession.setId(1L);
    savedSession.setBpm(120);
    savedSession.setDuration(30);
    savedSession.setSessionDate(LocalDate.now());
    savedSession.setPracticeRoutine(practiceRoutine);

    when(routineRepo.findById(routineId)).thenReturn(Optional.of(practiceRoutine));
    when(mapper.toEntity(sessionDTO)).thenReturn(sessionEntity);
    when(sessionRepo.save(any(PracticeSession.class))).thenReturn(savedSession);
    when(mapper.toDto(savedSession)).thenReturn(sessionDTO);

    // Act
    PracticeSessionDTO result = sessionService.createSession(sessionDTO);

    // Assert
    assertEquals(sessionDTO.getPracticeRoutineId(), result.getPracticeRoutineId());
    assertEquals(sessionDTO.getBpm(), result.getBpm());
    assertEquals(sessionDTO.getDuration(), result.getDuration());
    assertEquals(sessionDTO.getSessionDate(), result.getSessionDate());
    verify(statsService, times(1)).updateStats(savedSession.getId());  // Verify stats update
  }

  @Test
  void getSessions_ShouldReturnListOfSessions() {
    // Arrange
    Long routineId = 1L;
    PracticeSession session1 = new PracticeSession();
    session1.setBpm(100);
    session1.setDuration(30);
    session1.setSessionDate(LocalDate.now());

    PracticeSession session2 = new PracticeSession();
    session2.setBpm(110);
    session2.setDuration(45);
    session2.setSessionDate(LocalDate.now());

    List<PracticeSession> sessions = Arrays.asList(session1, session2);

    PracticeSessionDTO sessionDTO1 = new PracticeSessionDTO();
    sessionDTO1.setBpm(100);
    sessionDTO1.setDuration(30);
    sessionDTO1.setSessionDate(LocalDate.now());

    PracticeSessionDTO sessionDTO2 = new PracticeSessionDTO();
    sessionDTO2.setBpm(110);
    sessionDTO2.setDuration(45);
    sessionDTO2.setSessionDate(LocalDate.now());

    when(sessionRepo.findByPracticeRoutineId(routineId)).thenReturn(sessions);
    when(mapper.toDto(session1)).thenReturn(sessionDTO1);
    when(mapper.toDto(session2)).thenReturn(sessionDTO2);

    // Act
    List<PracticeSessionDTO> result = sessionService.getSessions(routineId);

    // Assert
    assertEquals(2, result.size());
    assertEquals(sessionDTO1.getBpm(), result.get(0).getBpm());
    assertEquals(sessionDTO2.getBpm(), result.get(1).getBpm());
  }

  @Test
  void updateSession_ShouldUpdateAndReturnUpdatedSessionDTO() {
    // Arrange
    Long sessionId = 1L;
    PracticeSessionDTO updatedSessionDTO = new PracticeSessionDTO();
    updatedSessionDTO.setBpm(130);
    updatedSessionDTO.setDuration(40);
    updatedSessionDTO.setSessionDate(LocalDate.now());

    PracticeSession existingSession = new PracticeSession();
    existingSession.setId(sessionId);
    existingSession.setBpm(120);
    existingSession.setDuration(30);
    existingSession.setSessionDate(LocalDate.now());

    PracticeSession updatedSession = new PracticeSession();
    updatedSession.setId(sessionId);
    updatedSession.setBpm(130);
    updatedSession.setDuration(40);
    updatedSession.setSessionDate(LocalDate.now());

    when(sessionRepo.findById(sessionId)).thenReturn(Optional.of(existingSession));
    when(mapper.toDto(updatedSession)).thenReturn(updatedSessionDTO);
    when(sessionRepo.save(any(PracticeSession.class))).thenReturn(updatedSession);

    // Act
    PracticeSessionDTO result = sessionService.updateSession(sessionId, updatedSessionDTO);

    // Assert
    assertEquals(updatedSessionDTO.getBpm(), result.getBpm());
    assertEquals(updatedSessionDTO.getDuration(), result.getDuration());
    assertEquals(updatedSessionDTO.getSessionDate(), result.getSessionDate());
    verify(statsService, times(1)).updateStats(updatedSession.getId());  // Verify stats update
  }

  @Test
  void deleteSession_ShouldDeleteSession() {
    // Arrange
    Long sessionId = 1L;

    // Act
    sessionService.deleteSession(sessionId);

    // Assert
    verify(sessionRepo, times(1)).deleteById(sessionId);  // Verify deletion
  }
}
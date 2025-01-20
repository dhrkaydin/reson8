package com.reson8.app.service;

import com.reson8.app.dto.PracticeStatisticsDTO;
import com.reson8.app.mapper.PracticeStatisticsMapper;
import com.reson8.app.model.PracticeRoutine;
import com.reson8.app.model.PracticeSession;
import com.reson8.app.model.PracticeStatistics;
import com.reson8.app.repository.PracticeSessionRepository;
import com.reson8.app.repository.PracticeStatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PracticeStatisticsServiceTest {

  @Mock
  private PracticeStatisticsRepository statsRepo;

  @Mock
  private PracticeSessionRepository sessionRepo;

  @Mock
  private PracticeStatisticsMapper mapper;

  @InjectMocks
  private PracticeStatisticsService statsService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void updateStats_ShouldCalculateAndUpdateStatistics() {
    // Arrange
    Long routineId = 1L;

    // Mock PracticeRoutine
    PracticeRoutine practiceRoutine = new PracticeRoutine();
    practiceRoutine.setId(routineId);

    // Mock PracticeSessions for the routine
    PracticeSession session1 = new PracticeSession();
    session1.setDuration(30);
    session1.setBpm(100);

    PracticeSession session2 = new PracticeSession();
    session2.setDuration(45);
    session2.setBpm(120);

    List<PracticeSession> sessions = Arrays.asList(session1, session2);

    // Mock existing PracticeStatistics
    PracticeStatistics existingStats = new PracticeStatistics();
    existingStats.setPracticeRoutine(practiceRoutine);

    // Mock repository and mapper behavior
    when(sessionRepo.findByPracticeRoutineId(routineId)).thenReturn(sessions);
    when(statsRepo.findByPracticeRoutineId(routineId)).thenReturn(existingStats);
    when(statsRepo.save(any(PracticeStatistics.class))).thenAnswer(invocation -> invocation.getArgument(0));

    PracticeStatisticsDTO expectedDto = new PracticeStatisticsDTO();
    expectedDto.setPracticeRoutineId(routineId);
    expectedDto.setTotalPracticeTime(75);
    expectedDto.setTotalSessions(2);
    expectedDto.setHighestBPM(120);
    expectedDto.setTotalBPMIncrease(20);

    when(mapper.toDto(any(PracticeStatistics.class))).thenReturn(expectedDto);

    // Act
    PracticeStatisticsDTO result = statsService.updateStats(routineId);

    // Assert
    assertEquals(expectedDto.getPracticeRoutineId(), result.getPracticeRoutineId());
    assertEquals(expectedDto.getTotalPracticeTime(), result.getTotalPracticeTime());
    assertEquals(expectedDto.getTotalSessions(), result.getTotalSessions());
    assertEquals(expectedDto.getHighestBPM(), result.getHighestBPM());
    assertEquals(expectedDto.getTotalBPMIncrease(), result.getTotalBPMIncrease());
  }

  @Test
  void getStats_ShouldReturnStatisticsForRoutine() {
    // Arrange
    Long routineId = 1L;

    // Mock PracticeRoutine
    PracticeRoutine practiceRoutine = new PracticeRoutine();
    practiceRoutine.setId(routineId);

    // Mock PracticeStatistics
    PracticeStatistics stats = new PracticeStatistics();
    stats.setPracticeRoutine(practiceRoutine);
    stats.setTotalPracticeTime(120);
    stats.setTotalSessions(4);
    stats.setHighestBPM(150);
    stats.setTotalBPMIncrease(50);

    PracticeStatisticsDTO expectedDto = new PracticeStatisticsDTO();
    expectedDto.setPracticeRoutineId(routineId);
    expectedDto.setTotalPracticeTime(120);
    expectedDto.setTotalSessions(4);
    expectedDto.setHighestBPM(150);
    expectedDto.setTotalBPMIncrease(50);

    when(statsRepo.findByPracticeRoutineId(routineId)).thenReturn(stats);
    when(mapper.toDto(stats)).thenReturn(expectedDto);

    // Act
    PracticeStatisticsDTO result = statsService.getStats(routineId);

    // Assert
    assertEquals(expectedDto.getPracticeRoutineId(), result.getPracticeRoutineId());
    assertEquals(expectedDto.getTotalPracticeTime(), result.getTotalPracticeTime());
    assertEquals(expectedDto.getTotalSessions(), result.getTotalSessions());
    assertEquals(expectedDto.getHighestBPM(), result.getHighestBPM());
    assertEquals(expectedDto.getTotalBPMIncrease(), result.getTotalBPMIncrease());
  }
}
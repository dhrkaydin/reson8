package com.reson8.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.reson8.app.model.Category;
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

class PracticeStatisticsServiceTest {

  @Mock
  private PracticeSessionRepository sessionRepo;

  @Mock
  private PracticeStatisticsRepository statsRepo;

  @InjectMocks
  private PracticeStatisticsService statsService;

  private PracticeRoutine sampleRoutine;
  private PracticeSession session1;
  private PracticeSession session2;
  private PracticeStatistics existingStats;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    sampleRoutine = new PracticeRoutine();
    sampleRoutine.setId(1L);
    sampleRoutine.setTitle("Test Routine");
    sampleRoutine.setCategory(Category.CHORDS);

    session1 = new PracticeSession();
    session1.setId(1L);
    session1.setBpm(100);
    session1.setDuration(30);
    session1.setPracticeRoutine(sampleRoutine);

    session2 = new PracticeSession();
    session2.setId(2L);
    session2.setBpm(120);
    session2.setDuration(45);
    session2.setPracticeRoutine(sampleRoutine);

    existingStats = new PracticeStatistics();
    existingStats.setTotalPracticeTime(60);
    existingStats.setTotalSessions(1);
    existingStats.setHighestBPM(100);
    existingStats.setTotalBPMIncrease(0);
    existingStats.setPracticeRoutine(sampleRoutine);
  }

  @Test
  void updateStats_ShouldReturnUpdatedStats() {
    List<PracticeSession> sessions = Arrays.asList(session1, session2);
    PracticeStatistics expectedStats = new PracticeStatistics();
    expectedStats.setTotalPracticeTime(75); // 30 + 45
    expectedStats.setTotalSessions(2);
    expectedStats.setHighestBPM(120);
    expectedStats.setTotalBPMIncrease(20); // 120 - 100
    expectedStats.setPracticeRoutine(sampleRoutine);

    when(sessionRepo.findByPracticeRoutineId(1L)).thenReturn(sessions);
    when(statsRepo.findByPracticeRoutineId(1L)).thenReturn(null); // No existing stats
    when(statsRepo.save(any(PracticeStatistics.class))).thenReturn(expectedStats);

    PracticeStatistics result = statsService.updateStats(1L);

    assertNotNull(result);
    assertEquals(75, result.getTotalPracticeTime());
    assertEquals(2, result.getTotalSessions());
    assertEquals(120, result.getHighestBPM());
    assertEquals(20, result.getTotalBPMIncrease());
    assertEquals(sampleRoutine, result.getPracticeRoutine()); // Check that the practice routine is correctly set
    verify(sessionRepo, times(1)).findByPracticeRoutineId(1L);
    verify(statsRepo, times(1)).save(any(PracticeStatistics.class));
  }

  @Test
  void updateStats_ShouldUpdateExistingStats() {
    List<PracticeSession> sessions = Arrays.asList(session1, session2);
    PracticeStatistics expectedStats = new PracticeStatistics();
    expectedStats.setTotalPracticeTime(75); // 30 + 45
    expectedStats.setTotalSessions(2);
    expectedStats.setHighestBPM(120);
    expectedStats.setTotalBPMIncrease(20); // 120 - 100
    expectedStats.setPracticeRoutine(sampleRoutine);

    when(sessionRepo.findByPracticeRoutineId(1L)).thenReturn(sessions);
    when(statsRepo.findByPracticeRoutineId(1L)).thenReturn(existingStats);
    when(statsRepo.save(any(PracticeStatistics.class))).thenReturn(expectedStats);

    PracticeStatistics result = statsService.updateStats(1L);

    assertNotNull(result);
    assertEquals(75, result.getTotalPracticeTime());
    assertEquals(2, result.getTotalSessions());
    assertEquals(120, result.getHighestBPM());
    assertEquals(20, result.getTotalBPMIncrease());
    assertEquals(sampleRoutine, result.getPracticeRoutine()); // Check that the practice routine is correctly set
    verify(sessionRepo, times(1)).findByPracticeRoutineId(1L);
    verify(statsRepo, times(1)).save(any(PracticeStatistics.class));
  }

  @Test
  void getStats_ShouldReturnExistingStats() {
    when(statsRepo.findByPracticeRoutineId(1L)).thenReturn(existingStats);

    PracticeStatistics result = statsService.getStats(1L);

    assertNotNull(result);
    assertEquals(60, result.getTotalPracticeTime());
    assertEquals(1, result.getTotalSessions());
    assertEquals(100, result.getHighestBPM());
    assertEquals(0, result.getTotalBPMIncrease());
    assertEquals(sampleRoutine, result.getPracticeRoutine()); // Check that the practice routine is correctly set
    verify(statsRepo, times(1)).findByPracticeRoutineId(1L);
  }

  @Test
  void getStats_ShouldReturnNullWhenNoStatsExist() {
    when(statsRepo.findByPracticeRoutineId(1L)).thenReturn(null);

    PracticeStatistics result = statsService.getStats(1L);

    assertNull(result);
    verify(statsRepo, times(1)).findByPracticeRoutineId(1L);
  }
}
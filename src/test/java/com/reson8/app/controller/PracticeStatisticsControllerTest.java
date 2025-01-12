package com.reson8.app.controller;

import com.reson8.app.model.PracticeStatistics;
import com.reson8.app.service.PracticeStatisticsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PracticeStatisticsControllerTest {

  private MockMvc mockMvc;

  @Mock
  private PracticeStatisticsService statisticsService;

  @InjectMocks
  private PracticeStatisticsController statisticsController;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(statisticsController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void getRoutineStats_ShouldReturnStatistics() throws Exception {
    Long routineId = 1L;
    PracticeStatistics practiceStatistics = new PracticeStatistics();
    practiceStatistics.setTotalPracticeTime(120);
    practiceStatistics.setTotalSessions(10);
    practiceStatistics.setHighestBPM(130);
    practiceStatistics.setTotalBPMIncrease(15);

    when(statisticsService.getStats(routineId)).thenReturn(practiceStatistics);

    mockMvc.perform(get("/api/statistics/routine/{routineId}", routineId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalPracticeTime").value(120))
        .andExpect(jsonPath("$.totalSessions").value(10))
        .andExpect(jsonPath("$.highestBPM").value(130))
        .andExpect(jsonPath("$.totalBPMIncrease").value(15));

    verify(statisticsService, times(1)).getStats(routineId);
  }

  @Test
  public void updateStats_ShouldReturnUpdatedStatistics() throws Exception {
    Long routineId = 1L;
    PracticeStatistics updatedStatistics = new PracticeStatistics();
    updatedStatistics.setTotalPracticeTime(150);
    updatedStatistics.setTotalSessions(12);
    updatedStatistics.setHighestBPM(140);
    updatedStatistics.setTotalBPMIncrease(20);

    when(statisticsService.updateStats(routineId)).thenReturn(updatedStatistics);

    mockMvc.perform(put("/api/statistics/routine/{routineId}/update", routineId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedStatistics)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalPracticeTime").value(150))
        .andExpect(jsonPath("$.totalSessions").value(12))
        .andExpect(jsonPath("$.highestBPM").value(140))
        .andExpect(jsonPath("$.totalBPMIncrease").value(20));

    verify(statisticsService, times(1)).updateStats(routineId);
  }
}
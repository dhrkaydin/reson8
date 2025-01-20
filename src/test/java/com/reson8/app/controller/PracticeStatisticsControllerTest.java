package com.reson8.app.controller;

import com.reson8.app.dto.PracticeStatisticsDTO;
import com.reson8.app.service.PracticeStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PracticeStatisticsControllerTest {

  @Mock
  private PracticeStatisticsService statisticsService;

  @InjectMocks
  private PracticeStatisticsController statisticsController;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(statisticsController).build();
  }

  @Test
  public void getRoutineStatsTest() throws Exception {
    // Create a mock PracticeStatisticsDTO object
    PracticeStatisticsDTO statsDTO = new PracticeStatisticsDTO();
    statsDTO.setTotalPracticeTime(120);
    statsDTO.setTotalSessions(3);
    statsDTO.setHighestBPM(150);
    statsDTO.setTotalBPMIncrease(20);

    // Mock the behavior of statisticsService.getStats() method
    when(statisticsService.getStats(1L)).thenReturn(statsDTO);

    // Perform a GET request and validate the response
    mockMvc.perform(get("/api/statistics/routine/{routineId}", 1L)
            .contentType("application/json"))
        .andExpect(status().isOk())  // Expect 200 status code
        .andExpect(jsonPath("$.totalPracticeTime").value(120))
        .andExpect(jsonPath("$.totalSessions").value(3))
        .andExpect(jsonPath("$.highestBPM").value(150))
        .andExpect(jsonPath("$.totalBPMIncrease").value(20));
  }

  @Test
  public void updateStatsTest() throws Exception {
    // Create a mock PracticeStatisticsDTO object
    PracticeStatisticsDTO updatedStatsDTO = new PracticeStatisticsDTO();
    updatedStatsDTO.setTotalPracticeTime(150);
    updatedStatsDTO.setTotalSessions(4);
    updatedStatsDTO.setHighestBPM(160);
    updatedStatsDTO.setTotalBPMIncrease(30);

    // Mock the behavior of statisticsService.updateStats() method
    when(statisticsService.updateStats(1L)).thenReturn(updatedStatsDTO);

    // Perform a PUT request and validate the response
    mockMvc.perform(put("/api/statistics/routine/{routineId}/update", 1L)
            .contentType("application/json"))
        .andExpect(status().isOk())  // Expect 200 status code
        .andExpect(jsonPath("$.totalPracticeTime").value(150))
        .andExpect(jsonPath("$.totalSessions").value(4))
        .andExpect(jsonPath("$.highestBPM").value(160))
        .andExpect(jsonPath("$.totalBPMIncrease").value(30));
  }
}
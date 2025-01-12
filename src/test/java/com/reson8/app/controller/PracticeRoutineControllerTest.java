package com.reson8.app.controller;

import com.reson8.app.model.PracticeRoutine;
import com.reson8.app.service.PracticeRoutineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PracticeRoutineControllerTest {

  @Mock
  private PracticeRoutineService routineService;

  @InjectMocks
  private PracticeRoutineController routineController;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);  // Initialize mocks
    mockMvc = MockMvcBuilders.standaloneSetup(routineController).build();  // Setup MockMvc for the controller
  }

  @Test
  public void createRoutineTest() throws Exception {
    // Create a practice routine mock object
    PracticeRoutine routine = new PracticeRoutine();
    routine.setId(1L);  // Set ID to mock the returned value

    // Mock the behavior of routineService.createRoutine() method
    when(routineService.createRoutine(routine)).thenReturn(routine);

    // Perform a POST request to create a new routine and validate the response
    mockMvc.perform(post("/api/routines")
            .contentType("application/json")
            .content("{\"id\":1}"))  // JSON body for the POST request
        .andExpect(status().isCreated())  // Expect 201 status code
        .andExpect(jsonPath("$.id").value(1));  // Expect the response body to contain the correct ID
  }

  @Test
  public void updateRoutineTest() throws Exception {
    // Create mock objects for both existing and updated routine
    PracticeRoutine existingRoutine = new PracticeRoutine();
    existingRoutine.setId(1L);

    PracticeRoutine updatedRoutine = new PracticeRoutine();
    updatedRoutine.setId(1L);

    // Mock the behavior of routineService.updateRoutine() method
    when(routineService.updateRoutine(1L, updatedRoutine)).thenReturn(updatedRoutine);

    // Perform a PUT request to update the routine and validate the response
    mockMvc.perform(put("/api/routines/{routineId}", 1L)  // Use 'put' instead of 'post'
            .contentType("application/json")
            .content("{\"id\":1}"))  // JSON body for the PUT request
        .andExpect(status().isOk())  // Expect 200 status code
        .andExpect(jsonPath("$.id").value(1));  // Expect the response body to contain the correct ID
  }
}
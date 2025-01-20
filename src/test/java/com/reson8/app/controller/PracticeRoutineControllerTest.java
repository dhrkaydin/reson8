package com.reson8.app.controller;

import com.reson8.app.dto.PracticeRoutineDTO;
import com.reson8.app.model.Category;
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
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(routineController).build();
  }

  @Test
  public void createRoutineTest() throws Exception {
    // Create a PracticeRoutineDTO mock object
    PracticeRoutineDTO routineDTO = new PracticeRoutineDTO();
    routineDTO.setTitle("Morning Routine");
    routineDTO.setCategory(Category.TECHNIQUE);  // Correct enum value
    routineDTO.setDescription("A quick morning warm-up session");

    // Mock the behavior of routineService.createRoutine() method
    when(routineService.createRoutine(routineDTO)).thenReturn(routineDTO);

    // Perform a POST request to create a new routine and validate the response
    mockMvc.perform(post("/api/routines")
            .contentType("application/json")
            .content("{\"title\":\"Morning Routine\", \"category\":\"TECHNIQUE\", \"description\":\"A quick morning warm-up session\"}"))  // Updated category value
        .andExpect(status().isCreated())  // Expect 201 status code
        .andExpect(jsonPath("$.title").value("Morning Routine"))
        .andExpect(jsonPath("$.category").value("TECHNIQUE"))
        .andExpect(jsonPath("$.description").value("A quick morning warm-up session"));
  }

  @Test
  public void updateRoutineTest() throws Exception {
    // Create mock objects for both existing and updated routine
    PracticeRoutineDTO updatedRoutineDTO = new PracticeRoutineDTO();
    updatedRoutineDTO.setId(1L);
    updatedRoutineDTO.setTitle("Updated Routine");
    updatedRoutineDTO.setCategory(Category.TECHNIQUE);  // Correct enum value
    updatedRoutineDTO.setDescription("An updated cool-down session");

    // Mock the behavior of routineService.updateRoutine() method
    when(routineService.updateRoutine(1L, updatedRoutineDTO)).thenReturn(updatedRoutineDTO);

    // Perform a PUT request to update the routine and validate the response
    mockMvc.perform(put("/api/routines/{routineId}", 1L)  // Correctly use URL parameter
            .contentType("application/json")
            .content("{\"id\":1, \"title\":\"Updated Routine\", \"category\":\"TECHNIQUE\", \"description\":\"An updated cool-down session\"}"))  // Updated category value
        .andExpect(status().isOk())  // Expect 200 status code
        .andExpect(jsonPath("$.title").value("Updated Routine"))
        .andExpect(jsonPath("$.category").value("TECHNIQUE"))
        .andExpect(jsonPath("$.description").value("An updated cool-down session"));
  }
}
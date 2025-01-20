package com.reson8.app.controller;

import com.reson8.app.dto.PracticeSessionDTO;
import com.reson8.app.service.PracticeSessionService;
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

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PracticeSessionControllerTest {

  private MockMvc mockMvc;

  @Mock
  private PracticeSessionService sessionService;

  @InjectMocks
  private PracticeSessionController sessionController;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    // Initialize MockMvc with the controller
    mockMvc = MockMvcBuilders.standaloneSetup(sessionController).build();
    objectMapper = new ObjectMapper(); // Initialize ObjectMapper for JSON conversion
  }

  @Test
  public void createSession_ShouldReturnCreatedSession() throws Exception {
    // Given
    PracticeSessionDTO practiceSessionDTO = new PracticeSessionDTO();
    practiceSessionDTO.setId(1L);
    practiceSessionDTO.setDuration(120);
    practiceSessionDTO.setBpm(100);
    practiceSessionDTO.setPracticeRoutineId(1L);

    when(sessionService.createSession(any(PracticeSessionDTO.class))).thenReturn(practiceSessionDTO);

    // When & Then
    mockMvc.perform(post("/api/sessions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(practiceSessionDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.duration").value(120))
        .andExpect(jsonPath("$.bpm").value(100));

    verify(sessionService, times(1)).createSession(any(PracticeSessionDTO.class));
  }

  @Test
  public void getSessions_ShouldReturnListOfSessions() throws Exception {
    // Given
    PracticeSessionDTO practiceSessionDTO = new PracticeSessionDTO();
    practiceSessionDTO.setId(1L);
    practiceSessionDTO.setDuration(120);
    practiceSessionDTO.setBpm(100);
    practiceSessionDTO.setPracticeRoutineId(1L);

    when(sessionService.getSessions(1L)).thenReturn(Collections.singletonList(practiceSessionDTO));

    // When & Then
    mockMvc.perform(get("/api/sessions/routine/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].duration").value(120))
        .andExpect(jsonPath("$[0].bpm").value(100));

    verify(sessionService, times(1)).getSessions(1L);
  }

  @Test
  public void updateSession_ShouldReturnUpdatedSession() throws Exception {
    // Given
    Long sessionId = 1L;
    PracticeSessionDTO updatedSessionDTO = new PracticeSessionDTO();
    updatedSessionDTO.setId(sessionId);
    updatedSessionDTO.setDuration(150);
    updatedSessionDTO.setBpm(110);
    updatedSessionDTO.setPracticeRoutineId(1L);

    when(sessionService.updateSession(eq(sessionId), any(PracticeSessionDTO.class))).thenReturn(updatedSessionDTO);

    // When & Then
    mockMvc.perform(put("/api/sessions/session/{sessionId}", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedSessionDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(sessionId))
        .andExpect(jsonPath("$.duration").value(150))
        .andExpect(jsonPath("$.bpm").value(110));

    verify(sessionService, times(1)).updateSession(eq(sessionId), any(PracticeSessionDTO.class));
  }

  @Test
  public void deleteSession_ShouldReturnSuccessMessage() throws Exception {
    // Given
    Long sessionId = 1L;

    doNothing().when(sessionService).deleteSession(sessionId);

    // When & Then
    mockMvc.perform(delete("/api/sessions/session/{sessionId}", sessionId))
        .andExpect(status().isOk())
        .andExpect(content().string("Session deleted"));

    verify(sessionService, times(1)).deleteSession(sessionId);
  }
}
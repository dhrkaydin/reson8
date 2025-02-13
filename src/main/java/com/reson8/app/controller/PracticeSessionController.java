package com.reson8.app.controller;

import com.reson8.app.dto.PracticeSessionDTO;
import com.reson8.app.service.PracticeSessionService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class PracticeSessionController {

  @Autowired
  private PracticeSessionService sessionService;

  @PostMapping
  public ResponseEntity<PracticeSessionDTO> createSession(@RequestBody PracticeSessionDTO session) {
    PracticeSessionDTO createdSession = sessionService.createSession(session);
    return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
  }

  @GetMapping("/routine/{routineId}")
  public ResponseEntity<List<PracticeSessionDTO>> getSessions(@PathVariable Long routineId) {
    List<PracticeSessionDTO> sessions = sessionService.getSessions(routineId);
    return new ResponseEntity<>(sessions, HttpStatus.OK);
  }

  @PutMapping("/session/{sessionId}")
  public ResponseEntity<PracticeSessionDTO> updateSession(@PathVariable Long sessionId, @RequestBody PracticeSessionDTO updatedSession) {
    PracticeSessionDTO session = sessionService.updateSession(sessionId, updatedSession);
    return ResponseEntity.ok(session); // Return updated session with status 200 OK
  }

  @DeleteMapping("/session/{sessionId}")
  public ResponseEntity<String> deleteSession(@PathVariable Long sessionId) {
    sessionService.deleteSession(sessionId);
    return new ResponseEntity<>("Session deleted", HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<PracticeSessionDTO>> getSessionsInRange(
      @RequestParam(value = "routineId", required = false) Long routineId,
      @RequestParam(value = "startDate", required = false) LocalDate startDate,
      @RequestParam(value = "endDate", required = false) LocalDate endDate) {

    List<PracticeSessionDTO> sessions;

    if (startDate != null && endDate != null) {
      if (routineId != null) {
        sessions = sessionService.getSessionsInRange(routineId, startDate, endDate);
      } else {
        sessions = sessionService.getSessionsInRangeForAllRoutines(startDate, endDate);
      }
    } else {
      if (routineId != null && routineId != 0) {
        sessions = sessionService.getSessions(routineId);
      } else {
        sessions = sessionService.getAllSessions();
      }
    }

    return ResponseEntity.ok(sessions);
  }
}
package com.reson8.app.controller;

import com.reson8.app.model.PracticeSession;
import com.reson8.app.service.PracticeSessionService;
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
  public ResponseEntity<PracticeSession> createSession(@RequestBody PracticeSession session) {
    PracticeSession createdSession = sessionService.createSession(session);
    return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
  }

  @GetMapping("/routine/{routineId}")
  public ResponseEntity<List<PracticeSession>> getSessions(@PathVariable Long routineId) {
    List<PracticeSession> sessions = sessionService.getSessions(routineId);
    return new ResponseEntity<>(sessions, HttpStatus.OK);
  }

  @PutMapping("/session/{sessionId}")
  public ResponseEntity<PracticeSession> updateSession(@PathVariable Long sessionId, @RequestBody PracticeSession updatedSession) {
    PracticeSession session = sessionService.updateSession(sessionId, updatedSession);
    return ResponseEntity.ok(session); // Return updated session with status 200 OK
  }
}
package com.reson8.app.controller;

import com.reson8.app.model.PracticeRoutine;
import com.reson8.app.service.PracticeRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * A simple controller to create, update, and delete routines.
 */
@RestController
@RequestMapping("/api/routines")
public class PracticeRoutineController {

  @Autowired
  private PracticeRoutineService routineService;

  @PostMapping
  public ResponseEntity<PracticeRoutine> createRoutine(@RequestBody PracticeRoutine routine) {
    PracticeRoutine createdRoutine = routineService.createRoutine(routine);
    return new ResponseEntity<>(createdRoutine, HttpStatus.CREATED);
  }

  @PutMapping("/{routineId}")
  public ResponseEntity<PracticeRoutine> updateRoutine(@PathVariable Long routineId, @RequestBody PracticeRoutine routine) {
    PracticeRoutine updatedRoutine = routineService.updateRoutine(routineId, routine);
    return new ResponseEntity<>(updatedRoutine, HttpStatus.OK);
  }

  @GetMapping("/{routineId}")
  public ResponseEntity<PracticeRoutine> getRoutine(@PathVariable Long routineId) {
    PracticeRoutine routine = routineService.getRoutine(routineId);
    return new ResponseEntity<>(routine, HttpStatus.OK);
  }

  @DeleteMapping("/{routineId}")
  public ResponseEntity<String> deleteRoutine(@PathVariable Long routineId) {
    routineService.deleteRoutine(routineId);
    return new ResponseEntity<>("Successfully deleted routine.", HttpStatus.OK);
  }
}
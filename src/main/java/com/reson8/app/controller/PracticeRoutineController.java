package com.reson8.app.controller;

import com.reson8.app.dto.PracticeRoutineDTO;
import com.reson8.app.dto.PracticeRoutineTitleDTO;
import com.reson8.app.service.PracticeRoutineService;
import java.util.List;
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
  public ResponseEntity<PracticeRoutineDTO> createRoutine(@RequestBody PracticeRoutineDTO routine) {
    PracticeRoutineDTO createdRoutine = routineService.createRoutine(routine);
    return new ResponseEntity<>(createdRoutine, HttpStatus.CREATED);
  }

  @PutMapping("/{routineId}")
  public ResponseEntity<PracticeRoutineDTO> updateRoutine(@PathVariable Long routineId, @RequestBody PracticeRoutineDTO routine) {
    PracticeRoutineDTO updatedRoutine = routineService.updateRoutine(routineId, routine);
    return new ResponseEntity<>(updatedRoutine, HttpStatus.OK);
  }

  @GetMapping("/{routineId}")
  public ResponseEntity<PracticeRoutineDTO> getRoutine(@PathVariable Long routineId) {
    PracticeRoutineDTO routine = routineService.getRoutine(routineId);
    return new ResponseEntity<>(routine, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<PracticeRoutineDTO>> getAllRoutines() {
    List<PracticeRoutineDTO> routines = routineService.getAllRoutines(); // Call the service to fetch all routines
    return new ResponseEntity<>(routines, HttpStatus.OK);
  }

  @DeleteMapping("/{routineId}")
  public ResponseEntity<String> deleteRoutine(@PathVariable Long routineId) {
    routineService.deleteRoutine(routineId);
    return new ResponseEntity<>("Successfully deleted routine.", HttpStatus.OK);
  }

  @GetMapping("/categories")
  public ResponseEntity<List<String>> getCategories() {
    List<String> categories = routineService.getCategories();
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @GetMapping("/titles")
  public ResponseEntity<List<PracticeRoutineTitleDTO>> getTitles() {
    List<PracticeRoutineTitleDTO> titles = routineService.getTitles();
    return new ResponseEntity<>(titles, HttpStatus.OK);
  }
}
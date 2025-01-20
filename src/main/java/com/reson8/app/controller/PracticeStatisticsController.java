package com.reson8.app.controller;

import com.reson8.app.dto.PracticeStatisticsDTO;
import com.reson8.app.model.PracticeStatistics;
import com.reson8.app.service.PracticeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
public class PracticeStatisticsController {

  @Autowired
  private PracticeStatisticsService statisticsService;

  @GetMapping("/routine/{routineId}")
  public ResponseEntity<PracticeStatisticsDTO> getRoutineStats(@PathVariable Long routineId) {
    PracticeStatisticsDTO stats = statisticsService.getStats(routineId);
    return new ResponseEntity<>(stats, HttpStatus.OK);
  }

  // This endpoint is for internal use, to trigger when a session changes.
  @PutMapping("/routine/{routineId}/update")
  public ResponseEntity<PracticeStatisticsDTO> updateStats(@PathVariable Long routineId) {
    PracticeStatisticsDTO stats = statisticsService.updateStats(routineId);
    return new ResponseEntity<>(stats, HttpStatus.OK);
  }
}
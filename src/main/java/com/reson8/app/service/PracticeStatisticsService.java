package com.reson8.app.service;

import com.reson8.app.dto.PracticeStatisticsDTO;
import com.reson8.app.mapper.PracticeStatisticsMapper;
import com.reson8.app.model.PracticeSession;
import com.reson8.app.model.PracticeStatistics;
import com.reson8.app.repository.PracticeSessionRepository;
import com.reson8.app.repository.PracticeStatisticsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PracticeStatisticsService {

  @Autowired
  private PracticeStatisticsRepository statsRepo;
  @Autowired
  private PracticeSessionRepository sessionRepo;
  @Autowired
  private PracticeStatisticsMapper mapper;

  public PracticeStatisticsDTO updateStats(Long routineId) {
    List<PracticeSession> sessions = sessionRepo.findByPracticeRoutineId(routineId);

    int totalPracticeTime = 0;
    int totalSessions = sessions.size();
    int highestBPM = 0;
    int lowestBPM = Integer.MAX_VALUE;

    for (PracticeSession session : sessions) {
      totalPracticeTime += session.getDuration();
      highestBPM = Math.max(highestBPM, session.getBpm());
      lowestBPM = Math.min(lowestBPM, session.getBpm());
    }

    int totalBPMIncrease = highestBPM - lowestBPM;

    PracticeStatistics stats = statsRepo.findByPracticeRoutineId(routineId);
    if (stats == null) stats = new PracticeStatistics();

    stats.setTotalPracticeTime(totalPracticeTime);
    stats.setTotalSessions(totalSessions);
    stats.setHighestBPM(highestBPM);
    stats.setTotalBPMIncrease(totalBPMIncrease);

    return saveStats(stats);
  }

  public PracticeStatisticsDTO saveStats(PracticeStatistics stats) {
    return mapper.toDto(statsRepo.save(stats));
  }

  public PracticeStatisticsDTO getStats(Long id) {
    return mapper.toDto(statsRepo.findByPracticeRoutineId(id));
  }
}
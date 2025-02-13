package com.reson8.app.service;

import com.reson8.app.dto.PracticeRoutineDTO;
import com.reson8.app.dto.PracticeRoutineTitleDTO;
import com.reson8.app.mapper.PracticeRoutineMapper;
import com.reson8.app.model.Category;
import com.reson8.app.model.PracticeRoutine;
import com.reson8.app.repository.PracticeRoutineRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PracticeRoutineService {

  @Autowired
  private PracticeRoutineRepository routineRepo;
  @Autowired
  private PracticeSessionService sessionService;
  @Autowired
  private PracticeStatisticsService statisticsService;
  @Autowired
  private PracticeRoutineMapper mapper;

  @Transactional
  public PracticeRoutineDTO createRoutine(PracticeRoutineDTO routine) {
    PracticeRoutine entity = routineRepo.save(mapper.toEntity(routine));
    routine.setId(entity.getId());

    return routine;
  }

  public PracticeRoutineDTO getRoutine(Long routineId) {
    return routineRepo.findById(routineId)
        .map(mapper::toDto)
        .orElseThrow(() -> new NoSuchElementException("Routine not found"));
  }

  public List<PracticeRoutineDTO> getAllRoutines() {
    return routineRepo.findAll().stream()
        .map(mapper::toDto)
        .toList();
  }

  //TODO: Make this dynamic based on the changed fields in the PracticeRoutine object. Might be overkill though.
  public PracticeRoutineDTO updateRoutine(Long routineId, PracticeRoutineDTO routine) {
    PracticeRoutine entity = routineRepo.findById(routineId)
        .orElseThrow(() -> new NoSuchElementException("Routine not found"));

    entity.setTitle(routine.getTitle());
    entity.setDescription(routine.getDescription());
    entity.setCategory(routine.getCategory());

    return mapper.toDto(routineRepo.save(entity));
  }

  @Transactional
  public void deleteRoutine(Long routineId) {
    routineRepo.deleteById(routineId);
  }

  public List<String> getCategories() {
    List<String> categoryList = new ArrayList<>();

    for (Category category : Category.values()) {
      categoryList.add(category.name());
    }

    return categoryList;
  }

  public List<PracticeRoutineTitleDTO> getTitles() {
    List<PracticeRoutineTitleDTO> titles = new ArrayList<>();

    routineRepo.findAll().forEach(
        routine -> titles.add(new PracticeRoutineTitleDTO(routine.getId(), routine.getTitle()))
    );

    return titles;
  }
}

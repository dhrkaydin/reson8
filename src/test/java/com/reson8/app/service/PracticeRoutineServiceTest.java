package com.reson8.app.service;

import com.reson8.app.dto.PracticeRoutineDTO;
import com.reson8.app.mapper.PracticeRoutineMapper;
import com.reson8.app.model.Category;
import com.reson8.app.model.PracticeRoutine;
import com.reson8.app.repository.PracticeRoutineRepository;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PracticeRoutineServiceTest {

  @Mock
  private PracticeRoutineRepository routineRepo;
  @Mock
  private PracticeRoutineMapper mapper;
  @InjectMocks
  private PracticeRoutineService routineService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createRoutine_ShouldSaveAndReturnRoutineDTO() {
    // Arrange
    PracticeRoutineDTO routineDTO = new PracticeRoutineDTO();
    routineDTO.setTitle("Morning Routine");
    routineDTO.setCategory(Category.TECHNIQUE);
    routineDTO.setDescription("A quick warm-up session");

    PracticeRoutine routineEntity = new PracticeRoutine();
    routineEntity.setTitle("Morning Routine");
    routineEntity.setCategory(Category.TECHNIQUE);
    routineEntity.setDescription("A quick warm-up session");

    PracticeRoutine savedRoutine = new PracticeRoutine();
    savedRoutine.setId(1L);
    savedRoutine.setTitle("Morning Routine");
    savedRoutine.setCategory(Category.TECHNIQUE);
    savedRoutine.setDescription("A quick warm-up session");

    when(mapper.toEntity(routineDTO)).thenReturn(routineEntity);
    when(routineRepo.save(any(PracticeRoutine.class))).thenReturn(savedRoutine);

    // Act
    PracticeRoutineDTO result = routineService.createRoutine(routineDTO);

    // Assert
    assertEquals(routineDTO.getTitle(), result.getTitle());
    assertEquals(routineDTO.getCategory(), result.getCategory());
    assertEquals(routineDTO.getDescription(), result.getDescription());
  }

  @Test
  void getRoutine_ShouldReturnRoutineDTO() {
    // Arrange
    Long routineId = 1L;
    PracticeRoutine routineEntity = new PracticeRoutine();
    routineEntity.setId(routineId);
    routineEntity.setTitle("Morning Routine");
    routineEntity.setCategory(Category.TECHNIQUE);
    routineEntity.setDescription("A quick warm-up session");

    PracticeRoutineDTO routineDTO = new PracticeRoutineDTO();
    routineDTO.setId(routineId);
    routineDTO.setTitle("Morning Routine");
    routineDTO.setCategory(Category.TECHNIQUE);
    routineDTO.setDescription("A quick warm-up session");

    when(routineRepo.findById(routineId)).thenReturn(Optional.of(routineEntity));
    when(mapper.toDto(routineEntity)).thenReturn(routineDTO);

    // Act
    PracticeRoutineDTO result = routineService.getRoutine(routineId);

    // Assert
    assertEquals(routineDTO.getId(), result.getId());
    assertEquals(routineDTO.getTitle(), result.getTitle());
    assertEquals(routineDTO.getCategory(), result.getCategory());
    assertEquals(routineDTO.getDescription(), result.getDescription());
  }

  @Test
  void getRoutine_ShouldThrowNoSuchElementException_WhenRoutineNotFound() {
    // Arrange
    Long routineId = 1L;

    when(routineRepo.findById(routineId)).thenReturn(Optional.empty());

    // Act & Assert
    try {
      routineService.getRoutine(routineId);
    } catch (NoSuchElementException e) {
      assertEquals("Routine not found", e.getMessage());
    }
  }

  @Test
  void getAllRoutines_ShouldReturnListOfRoutines() {
    // Arrange
    PracticeRoutine routine1 = new PracticeRoutine();
    routine1.setTitle("Morning Routine");
    routine1.setCategory(Category.TECHNIQUE);
    routine1.setDescription("A quick warm-up session");

    PracticeRoutine routine2 = new PracticeRoutine();
    routine2.setTitle("Evening Routine");
    routine2.setCategory(Category.TECHNIQUE);
    routine2.setDescription("A cool-down session");

    List<PracticeRoutine> routines = Arrays.asList(routine1, routine2);

    PracticeRoutineDTO routineDTO1 = new PracticeRoutineDTO();
    routineDTO1.setTitle("Morning Routine");
    routineDTO1.setCategory(Category.TECHNIQUE);
    routineDTO1.setDescription("A quick warm-up session");

    PracticeRoutineDTO routineDTO2 = new PracticeRoutineDTO();
    routineDTO2.setTitle("Evening Routine");
    routineDTO2.setCategory(Category.TECHNIQUE);
    routineDTO2.setDescription("A cool-down session");

    when(routineRepo.findAll()).thenReturn(routines);
    when(mapper.toDto(routine1)).thenReturn(routineDTO1);
    when(mapper.toDto(routine2)).thenReturn(routineDTO2);

    // Act
    List<PracticeRoutineDTO> result = routineService.getAllRoutines();

    // Assert
    assertEquals(2, result.size());
    assertEquals(routineDTO1.getTitle(), result.get(0).getTitle());
    assertEquals(routineDTO2.getTitle(), result.get(1).getTitle());
  }

  @Test
  void updateRoutine_ShouldUpdateAndReturnUpdatedRoutineDTO() {
    // Arrange
    Long routineId = 1L;
    PracticeRoutineDTO updatedRoutineDTO = new PracticeRoutineDTO();
    updatedRoutineDTO.setTitle("Updated Routine");
    updatedRoutineDTO.setCategory(Category.TECHNIQUE);
    updatedRoutineDTO.setDescription("An updated warm-up session");

    PracticeRoutine existingRoutine = new PracticeRoutine();
    existingRoutine.setId(routineId);
    existingRoutine.setTitle("Morning Routine");
    existingRoutine.setCategory(Category.TECHNIQUE);
    existingRoutine.setDescription("A quick warm-up session");

    PracticeRoutine updatedRoutine = new PracticeRoutine();
    updatedRoutine.setId(routineId);
    updatedRoutine.setTitle("Updated Routine");
    updatedRoutine.setCategory(Category.TECHNIQUE);
    updatedRoutine.setDescription("An updated warm-up session");

    when(routineRepo.findById(routineId)).thenReturn(Optional.of(existingRoutine));
    when(routineRepo.save(any(PracticeRoutine.class))).thenReturn(updatedRoutine);
    when(mapper.toDto(updatedRoutine)).thenReturn(updatedRoutineDTO);

    // Act
    PracticeRoutineDTO result = routineService.updateRoutine(routineId, updatedRoutineDTO);

    // Assert
    assertEquals(updatedRoutineDTO.getTitle(), result.getTitle());
    assertEquals(updatedRoutineDTO.getCategory(), result.getCategory());
    assertEquals(updatedRoutineDTO.getDescription(), result.getDescription());
  }

  @Test
  void updateRoutine_ShouldThrowNoSuchElementException_WhenRoutineNotFound() {
    // Arrange
    Long routineId = 1L;
    PracticeRoutineDTO updatedRoutineDTO = new PracticeRoutineDTO();
    updatedRoutineDTO.setTitle("Updated Routine");
    updatedRoutineDTO.setCategory(Category.TECHNIQUE);
    updatedRoutineDTO.setDescription("An updated warm-up session");

    when(routineRepo.findById(routineId)).thenReturn(Optional.empty());

    // Act & Assert
    try {
      routineService.updateRoutine(routineId, updatedRoutineDTO);
    } catch (NoSuchElementException e) {
      assertEquals("Routine not found", e.getMessage());
    }
  }

  @Test
  void deleteRoutine_ShouldDeleteRoutine() {
    // Arrange
    Long routineId = 1L;

    // Act
    routineService.deleteRoutine(routineId);

    // Assert
    verify(routineRepo, times(1)).deleteById(routineId);
  }
}
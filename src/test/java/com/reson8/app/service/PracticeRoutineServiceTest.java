package com.reson8.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.reson8.app.model.Category;
import com.reson8.app.model.PracticeRoutine;
import com.reson8.app.repository.PracticeRoutineRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PracticeRoutineServiceTest {

  @Mock
  private PracticeRoutineRepository routineRepository;

  @InjectMocks
  private PracticeRoutineService routineService;

  private PracticeRoutine sampleRoutine;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    sampleRoutine = new PracticeRoutine();
    sampleRoutine.setId(1L);
    sampleRoutine.setTitle("Test Routine");
    sampleRoutine.setDescription("Test Description");
    sampleRoutine.setCategory(Category.CHORDS);
  }

  @Test
  void createRoutine_ShouldReturnSavedRoutine() {
    when(routineRepository.save(sampleRoutine)).thenReturn(sampleRoutine);

    PracticeRoutine createdRoutine = routineService.createRoutine(sampleRoutine);

    assertNotNull(createdRoutine);
    assertEquals("Test Routine", createdRoutine.getTitle());
    verify(routineRepository, times(1)).save(sampleRoutine);
  }

  @Test
  void updateRoutine_ShouldUpdateAndReturnRoutine() {
    when(routineRepository.findById(1L)).thenReturn(Optional.of(sampleRoutine));

    PracticeRoutine updatedRoutine = new PracticeRoutine();
    updatedRoutine.setId(1L);
    updatedRoutine.setTitle("Updated Title");
    updatedRoutine.setDescription("Updated Description");
    updatedRoutine.setCategory(Category.ARPEGGIOS);

    when(routineRepository.save(any(PracticeRoutine.class))).thenAnswer(invocation -> {
      PracticeRoutine updated = invocation.getArgument(0);
      updated.setId(1L);
      return updated;
    });

    PracticeRoutine result = routineService.updateRoutine(1L, updatedRoutine);

    assertNotNull(result);
    assertEquals("Updated Title", result.getTitle());
    assertEquals("Updated Description", result.getDescription());
    assertEquals(Category.ARPEGGIOS, result.getCategory());
    verify(routineRepository, times(1)).save(any(PracticeRoutine.class));  // Verify save was called
  }

  @Test
  void updateRoutine_ShouldThrowException_WhenRoutineNotFound() {
    when(routineRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      routineService.updateRoutine(1L, sampleRoutine);
    });
    assertEquals("Could not update routine, routine not found", exception.getMessage());
  }

  @Test
  void getRoutine_ShouldReturnRoutine_WhenRoutineExists() {
    when(routineRepository.findById(1L)).thenReturn(Optional.of(sampleRoutine));

    PracticeRoutine result = routineService.getRoutine(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Test Routine", result.getTitle());
    assertEquals("Test Description", result.getDescription());
    assertEquals(Category.CHORDS, result.getCategory());
    verify(routineRepository, times(1)).findById(1L);
  }

  @Test
  void getRoutine_ShouldThrowException_WhenRoutineNotFound() {
    when(routineRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      routineService.getRoutine(1L);
    });

    assertEquals("Could not get routine, routine not found", exception.getMessage());
    verify(routineRepository, times(1)).findById(1L);
  }
}
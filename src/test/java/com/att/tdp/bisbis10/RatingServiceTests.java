package com.att.tdp.bisbis10;

import com.att.tdp.bisbis10.DTO.RatingDTO;
import com.att.tdp.bisbis10.DTO.RestaurantDTO;
import com.att.tdp.bisbis10.exceptions.InvalidRatingException;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.model.Rating;
import com.att.tdp.bisbis10.model.Restaurant;
import com.att.tdp.bisbis10.repository.RatingRepository;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import com.att.tdp.bisbis10.service.Rating.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RatingServiceTests {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RatingService ratingService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testAddRating_WithValidRating() throws RestaurantNotFoundException, InvalidRatingException {
        // Arrange
        RatingDTO ratingDTO = new RatingDTO(1L, 1,4.5f);
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(ratingDTO.restaurantId())).thenReturn(Optional.of(restaurant));

        // Act
        ratingService.addRating(ratingDTO);

        // Assert
        verify(ratingRepository, times(1)).save(any(Rating.class));
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void testAddRating_WithInvalidRating() {
        // Arrange
        RatingDTO ratingDTO = new RatingDTO(1L, 2L,5.5f);
        Restaurant restaurant = new Restaurant(new RestaurantDTO(2L,"Restaurant1", 4.5f ,true, Arrays.asList("Asian")));

        // Act & Assert
        when(restaurantRepository.findById(ratingDTO.restaurantId())).thenReturn(Optional.of(restaurant));
        assertThrows(InvalidRatingException.class, () -> ratingService.addRating(ratingDTO));
        verifyNoInteractions(ratingRepository);
    }

    @Test
    void testAddRating_RestaurantNotFound() {
        // Arrange
        RatingDTO ratingDTO = new RatingDTO(1L, 100,4.5f);
        when(restaurantRepository.findById(ratingDTO.restaurantId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> ratingService.addRating(ratingDTO));
        verifyNoInteractions(ratingRepository);
    }
}
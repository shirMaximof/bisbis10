package com.att.tdp.bisbis10;

import com.att.tdp.bisbis10.DTO.Restaurant.CreateRestaurantDTO;
import com.att.tdp.bisbis10.DTO.Restaurant.RestaurantDTOWihoutDishes;
import com.att.tdp.bisbis10.DTO.Restaurant.RestaurantDTOWithDishes;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.model.Restaurant;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import com.att.tdp.bisbis10.service.Restaurant.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceTests {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;
    private Restaurant restaurant1;
    private Restaurant restaurant2;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurant1 = new Restaurant(new CreateRestaurantDTO("Restaurant1", true, Arrays.asList("Asian")));
        restaurant2 = new Restaurant(new CreateRestaurantDTO("Restaurant2", false, Arrays.asList("Italian")));
    }

    @Test
    void testGetAllRestaurants() {
        // Arrange
        List<Restaurant> mockRestaurants = Arrays.asList(restaurant1, restaurant2);
        when(restaurantRepository.findAll()).thenReturn(mockRestaurants);

        // Act
        List<RestaurantDTOWihoutDishes> result = restaurantService.getAllRestaurants();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testGetRestaurantsByCuisine() {
        // Arrange
        String cuisine = "Asian";
        List<Restaurant> mockRestaurants = Arrays.asList(restaurant1);

        when(restaurantRepository.findByCuisinesContaining(cuisine)).thenReturn(mockRestaurants);

        // Act
        List<RestaurantDTOWihoutDishes> result = restaurantService.getRestaurantsByCuisine(cuisine);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void testGetRestaurantById_WhenRestaurantExists() {
        // Arrange
        Long id = 1L;
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant1));

        // Act + assert
        try
        {
            RestaurantDTOWithDishes result = restaurantService.getRestaurantById(id);
            assertNotEquals(result, null);
        }
        catch (Exception e)
        {
            fail(e);
        }
    }

    @Test
    void testGetRestaurantById_WhenRestaurantDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.getRestaurantById(id));
    }

    @Test
    void testAddRestaurant() {
        // Arrange
        CreateRestaurantDTO createRestaurantDTO = new CreateRestaurantDTO( "New Restaurant", true, Arrays.asList("Asian"));

        // Act
        restaurantService.addRestaurant(createRestaurantDTO);

        // Assert
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void testUpdateRestaurant_WhenRestaurantExists() {
        // Arrange
        Long id = 1L;
        CreateRestaurantDTO createRestaurantDTO = new CreateRestaurantDTO("Updated Restaurant", true,  Arrays.asList("Italian"));
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant1));

        // Act
        try
        {
            restaurantService.updateRestaurant(id, createRestaurantDTO);
        }
        catch (Exception e)
        {
            fail(e);
        }

        // Assert
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void testUpdateRestaurant_WhenRestaurantDoesNotExist() {
        // Arrange
        Long id = 1L;
        CreateRestaurantDTO createRestaurantDTO = new CreateRestaurantDTO("Updated Restaurant",  true, Arrays.asList("Italian"));
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.updateRestaurant(id, createRestaurantDTO));
    }

    @Test
    void testDeleteRestaurant_WhenRestaurantExists()  {
        // Arrange
        Long id = 1L;
        when(restaurantRepository.existsById(id)).thenReturn(true);

        // Act
        try
        {
            restaurantService.deleteRestaurant(id);
        }
        catch (Exception e)
        {
            fail(e);
        }

        // Assert
        verify(restaurantRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteRestaurant_WhenRestaurantDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(restaurantRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.deleteRestaurant(id));
    }
}


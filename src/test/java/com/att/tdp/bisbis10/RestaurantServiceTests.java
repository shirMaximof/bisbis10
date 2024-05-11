package com.att.tdp.bisbis10;

import com.att.tdp.bisbis10.DTO.RestaurantDTO;
import com.att.tdp.bisbis10.DTO.RestaurantDTOWithDishes;
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
        restaurant1 = new Restaurant(new RestaurantDTO(1L,"Restaurant1", 4.5f , true, Arrays.asList("Asian")));
        restaurant2 = new Restaurant(new RestaurantDTO(2L, "Restaurant2", 4.0f, false, Arrays.asList("Italian")));
    }

    @Test
    void testGetAllRestaurants() {
        // Arrange
        List<Restaurant> mockRestaurants = Arrays.asList(restaurant1, restaurant2);
        when(restaurantRepository.findAll()).thenReturn(mockRestaurants);

        // Act
        List<RestaurantDTO> result = restaurantService.getAllRestaurants();

        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals(2L, result.get(1).id());
    }

    @Test
    void testGetRestaurantsByCuisine() {
        // Arrange
        String cuisine = "Asian";
        List<Restaurant> mockRestaurants = Arrays.asList(restaurant1);

        when(restaurantRepository.findByCuisinesContaining(cuisine)).thenReturn(mockRestaurants);

        // Act
        List<RestaurantDTO> result = restaurantService.getRestaurantsByCuisine(cuisine);

        // Assert
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
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
            assertEquals(id, result.id());
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
        RestaurantDTO restaurantDTO = new RestaurantDTO(null, "New Restaurant", 4.0f, true, Arrays.asList("Asian"));

        // Act
        restaurantService.addRestaurant(restaurantDTO);

        // Assert
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void testUpdateRestaurant_WhenRestaurantExists() {
        // Arrange
        Long id = 1L;
        RestaurantDTO restaurantDTO = new RestaurantDTO(id, "Updated Restaurant",4.2f, true,  Arrays.asList("Italian"));
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant1));

        // Act
        try
        {
            restaurantService.updateRestaurant(id, restaurantDTO);
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
        RestaurantDTO restaurantDTO = new RestaurantDTO(id, "Updated Restaurant",  4.2f, true, Arrays.asList("Italian"));
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.updateRestaurant(id, restaurantDTO));
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


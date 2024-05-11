package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.DTO.Rating.CreateRatingDTO;
import com.att.tdp.bisbis10.exceptions.InvalidRatingException;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.service.Rating.IRatingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/ratings")
@Tag(name = "Ratings-APIs")
public class RatingController {
    @Autowired
    private IRatingService ratingService;
    @PostMapping
    public ResponseEntity<String> addRestaurantRating(@RequestBody CreateRatingDTO createRatingDTO) {
        try {
            ratingService.addRating(createRatingDTO);
            return ResponseEntity.ok().build();
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidRatingException e) {
            return ResponseEntity.badRequest().body("Invalid rating value");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}


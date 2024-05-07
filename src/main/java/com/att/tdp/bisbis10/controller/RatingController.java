package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.DTO.RatingDTO;
import com.att.tdp.bisbis10.exceptions.InvalidRatingException;
import com.att.tdp.bisbis10.exceptions.RestaurantNotFoundException;
import com.att.tdp.bisbis10.service.Rating.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private IRatingService ratingService;
    @PostMapping
    public ResponseEntity<String> addRestaurantRating(@RequestBody RatingDTO ratingDTO) {
        try {
            ratingService.addRating(ratingDTO);
            return ResponseEntity.ok("");
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidRatingException e) {
            return ResponseEntity.badRequest().body("Invalid rating value");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


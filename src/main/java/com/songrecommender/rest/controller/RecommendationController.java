package com.songrecommender.rest.controller;

import com.songrecommender.exception.SongNotFoundException;
import com.songrecommender.model.Song;
import com.songrecommender.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

@RestController
@RequestMapping(path = "/recommendation")
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    @GetMapping(path = "/{songName}")
    public ResponseEntity getRecommendation(@PathVariable String songName) {
        SuggestionResponse recommendation = recommendationService.getRecommendationFor(songName);
        return ResponseEntity.ok(recommendation);
    }
}

package com.songrecommender.rest.controller;

import com.songrecommender.model.Song;

import java.util.List;

public class SuggestionResponse {
    public Song request;
    public List<Song> recommendation;

    public SuggestionResponse(Song request, List<Song> recommendations) {
        this.request = request;
        this.recommendation = recommendations;
    }
}

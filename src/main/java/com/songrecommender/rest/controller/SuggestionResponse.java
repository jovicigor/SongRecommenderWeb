package com.songrecommender.rest.controller;

import com.songrecommender.model.Song;

public class SuggestionResponse {
    public Song request;
    public Song recommendation;

    public SuggestionResponse(Song request, Song recommendation) {
        this.request = request;
        this.recommendation = recommendation;
    }
}

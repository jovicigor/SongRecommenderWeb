package com.songrecommender.rest.controller;

import com.songrecommender.model.Song;

import java.util.List;

public class Recommendation {
    private Song request;
    private List<Song> recommendation;

    public Recommendation(Song request, List<Song> recommendations) {
        this.request = request;
        this.recommendation = recommendations;
    }

    public Song getRequest() {
        return request;
    }

    public List<Song> getRecommendation() {
        return recommendation;
    }
}

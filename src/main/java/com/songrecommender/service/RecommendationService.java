package com.songrecommender.service;

import com.songrecommender.dataaccess.external.SpotifyProxyApi;
import com.songrecommender.dataaccess.repository.SongRepository;
import com.songrecommender.model.Song;
import com.songrecommender.exception.SongNotFoundException;
import com.songrecommender.rest.controller.SuggestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class RecommendationService {

    @Autowired
    private SpotifyProxyApi spotifyApi;

    @Autowired
    private SongRepository songRepository;

    public SuggestionResponse getRecommendationFor(String songName) {
        Song song = spotifyApi.getSongByName(songName)
                .orElseThrow(() -> new SongNotFoundException(format("Song %s not found.", songName)));

        MachineLearningWrapper machineLearningWrapper = new MachineLearningWrapper(song);
        String centroidRemoteId = machineLearningWrapper.findCentroid();

        int cluster = songRepository.getClusterByRemoteId(centroidRemoteId);
        String suggestionRemoteId = machineLearningWrapper.findSimmilar(cluster);
        Song recommendation = spotifyApi.getSongByRemoteId(suggestionRemoteId)
                .orElseThrow(() -> new SongNotFoundException(format("Song %s not found.", suggestionRemoteId)));

        //      songRepository.saveSong(song);
        return new SuggestionResponse(song, recommendation);
    }
}

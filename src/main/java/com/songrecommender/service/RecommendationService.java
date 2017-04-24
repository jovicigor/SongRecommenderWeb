package com.songrecommender.service;

import com.songrecommender.dataaccess.external.SpotifyProxyApi;
import com.songrecommender.dataaccess.repository.SongRepository;
import com.songrecommender.model.Song;
import com.songrecommender.exception.SongNotFoundException;
import com.songrecommender.rest.controller.SuggestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.toList;

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
        List suggestionRemoteId = machineLearningWrapper.findTopMatches(cluster, 5);
        List<Song> recommendations = (List<Song>) suggestionRemoteId
                .stream()
                .map(id -> spotifyApi.getSongByRemoteId((String) id)
                        .orElseThrow(() -> new SongNotFoundException(format("Song %s not found.", suggestionRemoteId))))
                .collect(toList());

        //TODO: save with id
        //      songRepository.saveSong(song);
        return new SuggestionResponse(song, recommendations);
    }
}

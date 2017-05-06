package com.songrecommender.service;

import com.songrecommender.dataaccess.external.SpotifyProxyApi;
import com.songrecommender.dataaccess.repository.SongRepository;
import com.songrecommender.model.Song;
import com.songrecommender.exception.SongNotFoundException;
import com.songrecommender.rest.controller.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.String.format;
import static java.lang.String.valueOf;

@Component
@Scope(value = "prototype")
public class RecommendationService {

    @Autowired
    private SpotifyProxyApi spotifyApi;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private MachineLearningWrapper machineLearningWrapper;

    RecommendationService(SpotifyProxyApi spotifyApi, SongRepository songRepository, MachineLearningWrapper machineLearningWrapper) {
        this.spotifyApi = spotifyApi;
        this.songRepository = songRepository;
        this.machineLearningWrapper = machineLearningWrapper;
    }

    public Recommendation getRecommendationFor(String songName) {
        Song song = getSong(songName);

        int cluster = getCluster(song);
        List<Song> recommendations = getTopMatchesFromCluster(5, cluster);

        //      songRepository.saveSong(song);
        return new Recommendation(song, recommendations);
    }

    private Song getSong(String songName) {
        return spotifyApi.getSongByName(songName)
                .orElseThrow(() -> new SongNotFoundException(format("Song %s not found.", songName)));
    }

    private int getCluster(Song song) {
        machineLearningWrapper.setSong(song);
        String centroidRemoteId = machineLearningWrapper.findCentroid();
        return songRepository.getClusterByRemoteId(centroidRemoteId);
    }

    private List<Song> getTopMatchesFromCluster(int numberOfMatches, int cluster) {
        List<String> topMatchesids = machineLearningWrapper.findTopMatches(cluster, numberOfMatches);
        return spotifyApi.getSongsByRemoteIds((String[]) topMatchesids.toArray());
    }
}

package com.songrecommender.service;

import com.songrecommender.dataaccess.external.SpotifyProxyApi;
import com.songrecommender.dataaccess.repository.SongRepository;
import com.songrecommender.model.Song;
import com.songrecommender.exception.SongNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class RecommendationService {

    @Autowired
    private SpotifyProxyApi spotifyApi;

    @Autowired
    private SongRepository songRepository;

    public Song getRecommendationFor(String songName) throws SongNotFoundException {
        Song song = spotifyApi.getSongByName(songName)
                .orElseThrow(() -> new SongNotFoundException(format("Song %s not found.", songName)));

//        songRepository.saveSong(song);
        return song;
    }
}

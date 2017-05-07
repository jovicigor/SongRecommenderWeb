package com.songrecommender.dataaccess.external;


import com.songrecommender.exception.SongNotFoundException;
import com.songrecommender.model.Song;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;

@Component
public class SpotifyProxyApi {

    @Value("#{'${api.spotifyProxyApi}'}")
    String URL;

    public SpotifyProxyApi(String apiUrl) {
        URL = apiUrl;
    }

    public Optional<Song> getSongByName(String trackName) {
        Optional<Song> retval = Optional.empty();
        RestTemplate restTemplate = new RestTemplate();
        Song[] songs = restTemplate.getForObject(getPathForTrackByName(trackName), Song[].class);

        if (songs.length > 0) {
            retval = Optional.of(songs[0]);
        }
        return retval;
    }

    public Optional<Song> getSongByRemoteId(String remoteId) {
        RestTemplate restTemplate = new RestTemplate();
        Song song = restTemplate.getForObject(getPathForTrackById(remoteId), Song.class);

        return Optional.ofNullable(song);
    }

    public List<Song> getSongsByRemoteIds(List<String> remoteIds) {
        return remoteIds.stream()
                .map(this::getSongByRemoteId)
                .map(song -> song.orElseThrow(() -> new SongNotFoundException("One of top matches doesn't exist.")))
                .collect(toList());
    }

    private String getPathForTrackByName(String trackName) {
        return URL + "tracks?trackNames=" + trackName;
    }

    private String getPathForTrackById(String remoteId) {
        return URL + "tracks/" + remoteId;
    }
}

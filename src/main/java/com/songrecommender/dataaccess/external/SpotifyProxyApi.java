package com.songrecommender.dataaccess.external;


import com.songrecommender.model.Song;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class SpotifyProxyApi {

    @Value("#{'${api.spotifyProxyApi}'}")
    String URL;

    public Optional<Song> getSongByName(String trackName) {
        Optional<Song> retval = Optional.empty();
        RestTemplate restTemplate = new RestTemplate();
        Song[] songs = restTemplate.getForObject(getPathForTrackByName(trackName), Song[].class);

        if (songs.length > 0) {
            retval = Optional.of(songs[0]);
        }
        return retval;
    }

    public Optional<Song> getSongByRemoteId(String trackName) {
        RestTemplate restTemplate = new RestTemplate();
        Song song = restTemplate.getForObject(getPathForTrackById(trackName), Song.class);

        return Optional.ofNullable(song);
    }

    private String getPathForTrackByName(String trackName) {
        return URL + "tracks?trackNames=" + trackName;
    }

    private String getPathForTrackById(String remoteId) {
        return URL + "tracks/" + remoteId;
    }
}

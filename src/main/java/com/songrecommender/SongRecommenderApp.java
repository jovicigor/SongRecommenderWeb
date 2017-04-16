package com.songrecommender;

import com.songrecommender.dataaccess.external.SpotifyProxyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("SpringFacetCodeInspection")
@SpringBootApplication
public class SongRecommenderApp {

    @Autowired
    private SpotifyProxyApi spotifyProxyApi;

    public static void main(String[] args) {
        SpringApplication.run(SongRecommenderApp.class, args);
    }

}

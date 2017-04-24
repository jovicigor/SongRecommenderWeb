package com.songrecommender.service;


import clojure.lang.Keyword;
import com.songrecommender.Classifier;
import com.songrecommender.model.Artist;
import com.songrecommender.model.Song;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static clojure.java.api.Clojure.*;
import static java.lang.String.valueOf;

class MachineLearningWrapper {
    //path-to-cenroids path-to-min-csv path-to-max-csv songMap]
    private String centroidsCsvPath = "Centroids.csv";
    private String minValuesCsvPath = "Min.csv";
    private String maxValuesCsvPath = "Max.csv";

    private Map<Keyword, String> songMap;

    MachineLearningWrapper(Song song) {
        songMap = songToSongMap(song);
    }

    String findCentroid() {
        return new Classifier().findClosestCentroids(centroidsCsvPath, minValuesCsvPath, maxValuesCsvPath, songMap);
    }

    String findSimmilarByEuclidean(int cluster) {
        return new Classifier().findSimilarWithEuclideanDistance("Cluster" + cluster + ".csv", minValuesCsvPath, maxValuesCsvPath, songMap);
    }

    List findTopMatches(int cluster, int numberOfMatches) {
        return new Classifier().getTopMatches("Cluster" + cluster + ".csv", minValuesCsvPath, maxValuesCsvPath, songMap, numberOfMatches);
    }

    private Map<Keyword, String> songToSongMap(Song song) {
        Map<Keyword, String> songRepresentation = new HashMap<>();
        songRepresentation.put(keyword(":acousticness"), valueOf(song.getAcousticness()));
        songRepresentation.put(keyword(":danceability"), valueOf(song.getDanceability()));
        songRepresentation.put(keyword(":energy"), valueOf(song.getEnergy()));
        songRepresentation.put(keyword(":instrumentalness"), valueOf(song.getInstrumentalness()));
        songRepresentation.put(keyword(":track_key"), valueOf(song.getKey()));
        songRepresentation.put(keyword(":liveness"), valueOf(song.getLiveness()));
        songRepresentation.put(keyword(":mode"), valueOf(song.getMode()));
        songRepresentation.put(keyword(":popularity"), valueOf(song.getPopularity()));
        songRepresentation.put(keyword(":speechiness"), valueOf(song.getSpeechiness()));
        songRepresentation.put(keyword(":tempo"), valueOf(song.getTempo()));
        songRepresentation.put(keyword(":valence"), valueOf(song.getValence()));
        songRepresentation.put(keyword(":album_year"), valueOf(song.getAlbumYear()));

        songRepresentation.put(keyword(":remote_id"), song.getRemoteId());
        songRepresentation.put(keyword(":genres"), getGenres(song));

        return songRepresentation;
    }

    private String getGenres(Song song) {
        return song.getArtists().stream()
                .flatMap(artist -> artist.getGenres().stream())
                .collect(Collectors.joining(" "));
    }

    private Keyword keyword(String keyword) {
        if (keyword.startsWith(":"))
            return (Keyword) read(keyword);
        else throw new IllegalArgumentException("Not a valid keyword.");
    }


}

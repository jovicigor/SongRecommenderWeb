package com.songrecommender.service;


import clojure.lang.Keyword;
import com.songrecommender.Classifier;
import com.songrecommender.model.Song;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static clojure.java.api.Clojure.read;
import static java.lang.String.valueOf;

@Component
@Scope(value = "prototype")
public class MachineLearningWrapper {

    @Value("#{'${csv.relativePathToCentroidsCsv}'}")
    String centroidsCsvPath;
    @Value("#{'${csv.relativePathToMinCsv}'}")
    String minValuesCsvPath;
    @Value("#{'${csv.relativePathToMaxCsv}'}")
    String maxValuesCsvPath;
    @Value("#{'${csv.relativePathToClustersCsv}'}")
    String clustersCsvPath;

    public MachineLearningWrapper(String pathToCentroids, String pathToMin, String pathToMax, String pathToClusters) {
        centroidsCsvPath = pathToCentroids;
        minValuesCsvPath = pathToMin;
        maxValuesCsvPath = pathToMax;
        clustersCsvPath = pathToClusters;
    }

    private Map<Keyword, String> songMap;

    public void setSong(Song song) {
        songMap = songToSongMap(song);
    }

    public String findCentroid() {
        return new Classifier().findClosestCentroids(centroidsCsvPath, minValuesCsvPath, maxValuesCsvPath, songMap);
    }

    public String findSimilarByEuclidean(int cluster) {
        return new Classifier().findSimilarWithEuclideanDistance(clustersCsvPath + "Cluster" + cluster + ".csv", minValuesCsvPath, maxValuesCsvPath, songMap);
    }

    public List<String> findTopMatches(int cluster, int numberOfMatches) {
        return new Classifier().getTopMatches(clustersCsvPath + "Cluster" + cluster + ".csv", minValuesCsvPath, maxValuesCsvPath, songMap, numberOfMatches);
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

package com.songrecommender.service;

import com.songrecommender.dataaccess.external.SpotifyProxyApi;
import com.songrecommender.dataaccess.repository.SongRepository;
import com.songrecommender.exception.SongNotFoundException;
import com.songrecommender.model.Song;
import com.songrecommender.rest.controller.Recommendation;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class RecommendationServiceTest {

    @Test(expected = SongNotFoundException.class)
    public void getRecommendationFor_songDoesntExist_throwsSongNotFoundException() {
        String songName = "unknown song";
        SpotifyProxyApi spotifyApiMock = mock(SpotifyProxyApi.class);
        when(spotifyApiMock.getSongByName(songName)).thenReturn(Optional.empty());

        RecommendationService testee = new RecommendationService(spotifyApiMock, null, null);
        testee.getRecommendationFor(songName);
    }

    @Test
    public void getRecommendationFor_songExists_returnsCorrectNumberOfMatces() {
        Song song = song("known song", "remoteid");
        List<String> recommendationIds = Arrays.asList("remoteid1", "remoteid2", "remoteid3", "remoteid4", "remoteid4");

        SpotifyProxyApi spotifyApiMock = mock(SpotifyProxyApi.class);
        when(spotifyApiMock.getSongByName(song.getName())).thenReturn(Optional.of(song));
        when(spotifyApiMock.getSongsByRemoteIds(recommendationIds)).thenReturn(topMatches(recommendationIds));

        MachineLearningWrapper machineLearningMock = mock(MachineLearningWrapper.class);
        doNothing().when(machineLearningMock).setSong(song);
        when(machineLearningMock.findCentroid()).thenReturn("centroidRemoteId");
        when(machineLearningMock.findTopMatches(anyInt(), anyInt())).thenReturn(recommendationIds);

        SongRepository songRepositoryMock = mock(SongRepository.class);
        when(songRepositoryMock.getClusterByRemoteId(any())).thenReturn(0);

        RecommendationService testee = new RecommendationService(spotifyApiMock, songRepositoryMock, machineLearningMock);
        Recommendation recommendation = testee.getRecommendationFor(song.getName());

        assertEquals(5, recommendation.getRecommendation().size());
        verify(spotifyApiMock, times(1)).getSongByName(song.getName());
        verify(spotifyApiMock, times(1)).getSongsByRemoteIds(recommendationIds);
        verifyNoMoreInteractions(spotifyApiMock);

        verify(machineLearningMock, times(1)).findCentroid();
        verify(machineLearningMock, times(1)).findTopMatches(anyInt(), anyInt());
        verify(machineLearningMock, times(1)).setSong(song);
        verify(songRepositoryMock, times(1)).getClusterByRemoteId(any());
        verifyNoMoreInteractions(songRepositoryMock);
    }

    private Song song(String name, String remoteId) {
        Song song = new Song();
        song.setName(name);
        song.setRemoteId(remoteId);
        return song;
    }

    private List<Song> topMatches(List<String> remoteIds) {
        return remoteIds.stream()
                .map(s -> new Song())
                .collect(Collectors.toList());
    }
}
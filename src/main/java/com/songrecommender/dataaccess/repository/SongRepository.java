package com.songrecommender.dataaccess.repository;

import com.songrecommender.dataaccess.DBConnectionPool;
import com.songrecommender.dataaccess.repository.SQL.artist.InsertArtistQuery;
import com.songrecommender.dataaccess.repository.SQL.artist.SelectArtistIdQuery;
import com.songrecommender.dataaccess.repository.SQL.associations.InsertArtistGenreAssociationQuery;
import com.songrecommender.dataaccess.repository.SQL.associations.InsertTrackArtistAssociationQuery;
import com.songrecommender.dataaccess.repository.SQL.genre.InsertGenreQuery;
import com.songrecommender.dataaccess.repository.SQL.genre.SelectGenreIdQuery;
import com.songrecommender.dataaccess.repository.SQL.song.InsertSongQuery;
import com.songrecommender.dataaccess.repository.SQL.song.SelectSongIdQuery;
import com.songrecommender.exception.DataAccessException;
import com.songrecommender.model.Artist;
import com.songrecommender.model.Song;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO: Write tests and refactor
@Component
public class SongRepository {

    public void saveSong(Song song) {
        try (Connection connection = DBConnectionPool.getConnection()) {
            int trackId = new SelectSongIdQuery(connection)
                    .byRemoteId(song.getRemoteId())
                    .execute()
                    .orElseGet(() -> insertTrack(connection, song));
            List<Integer> artistIds = saveArtists(connection, song.getArtists());
            associateSongWithArtists(connection, trackId, artistIds);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void associateSongWithArtists(Connection connection, int trackId, List<Integer> artistIds) throws SQLException {
        InsertTrackArtistAssociationQuery query = new InsertTrackArtistAssociationQuery(connection)
                .forTrack(trackId);
        for (int artistId : artistIds) {
            query.withArtistId(artistId);
        }
        query.execute();
    }

    private List<Integer> saveArtists(Connection connection, List<Artist> artists) throws SQLException {
        List<Integer> artistIds = new ArrayList<>();
        for (Artist artist : artists) {
            int newArtistId = new SelectArtistIdQuery(connection)
                    .byRemoteId(artist.getId())
                    .execute()
                    .orElseGet(() -> insertArtist(connection, artist));
            artistIds.add(newArtistId);
            insertGenresAndAssociateWithArtist(connection, artist.getGenres(), newArtistId);
        }
        return artistIds;
    }

    private void insertGenresAndAssociateWithArtist(Connection connection, List<String> genres, int newArtistId) throws SQLException {
        InsertArtistGenreAssociationQuery associationQuery = new InsertArtistGenreAssociationQuery(connection);
        associationQuery.forArtist(newArtistId);
        List<Integer> genreIds = saveGenres(connection, genres);
        for (int genreId : genreIds) {
            associationQuery.withGenreId(genreId);
        }
        associationQuery.execute();
    }

    private List<Integer> saveGenres(Connection connection, List<String> genres) throws SQLException {
        List<Integer> genreIds = new ArrayList<>();
        for (String genre : genres) {
            int newGenreId = new SelectGenreIdQuery(connection)
                    .byName(genre)
                    .execute()
                    .orElseGet(() -> insertGenre(connection, genre));
            genreIds.add(newGenreId);
        }
        return genreIds;
    }

    private int insertTrack(Connection connection, Song song) {
        try {
            return new InsertSongQuery(connection).withSong(song).execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private int insertArtist(Connection connection, Artist artist) {
        try {
            return new InsertArtistQuery(connection).withArtist(artist).execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private int insertGenre(Connection connection, String genre) {
        try {
            return new InsertGenreQuery(connection).withGenre(genre).execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}

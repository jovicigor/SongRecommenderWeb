package com.songrecommender.dataaccess.repository.SQL.song;

import com.songrecommender.model.Song;

import java.sql.*;

import static com.songrecommender.dataaccess.repository.SQL.Util.getLastInsertId;

public class InsertSongQuery {

    private Connection connection;
    private Song song;
    private static final String SQL = "INSERT INTO track " +
            "(acousticness, album, danceability, duration, energy, remote_id, instrumentalness, track_key, liveness, loudness, mode, name, popularity, speechiness, tempo, time_signature, valence, album_year) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public InsertSongQuery(Connection connection) {
        this.connection = connection;
    }

    public InsertSongQuery withSong(Song song) {
        this.song = song;
        return this;
    }

    public int execute() throws SQLException {
        if (song == null) {
            throw new IllegalStateException("The song is not provided.");
        }
        PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setDouble(1, song.getAcousticness());
        statement.setString(2, song.getAlbum());
        statement.setDouble(3, song.getDanceability());
        statement.setLong(4, song.getDuration());
        statement.setDouble(5, song.getEnergy());
        statement.setString(6, song.getRemoteId());
        statement.setDouble(7, song.getInstrumentalness());
        statement.setInt(8, song.getKey());
        statement.setDouble(9, song.getLiveness());
        statement.setDouble(10, song.getLoudness());
        statement.setInt(11, song.getMode());
        statement.setString(12, song.getName());
        statement.setInt(13, song.getPopularity());
        statement.setDouble(14, song.getSpeechiness());
        statement.setInt(15, song.getTempo());
        statement.setInt(16, song.getTime_signature());
        statement.setDouble(17, song.getValence());
        statement.setInt(18, song.getAlbumYear());
        statement.executeUpdate();
        return getLastInsertId(statement);
    }


}

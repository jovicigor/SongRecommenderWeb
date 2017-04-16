package com.songrecommender.dataaccess.repository.SQL.associations;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTrackArtistAssociationQuery {

    private int trackId = -1;
    private static final String SQL = "INSERT INTO track_artist (track_Id, artist_Id) values (?, ?)";
    private PreparedStatement preparedStatement;

    public InsertTrackArtistAssociationQuery(Connection connection) throws SQLException {
        preparedStatement = connection.prepareStatement(SQL);
    }

    public InsertTrackArtistAssociationQuery forTrack(int trackId) {
        this.trackId = trackId;
        return this;
    }

    public InsertTrackArtistAssociationQuery withArtistId(int artistId) throws SQLException {
        preparedStatement.setInt(1, trackId);
        preparedStatement.setInt(2, artistId);
        preparedStatement.addBatch();
        return this;
    }

    public void execute() throws SQLException {
        if (trackId < 0) {
            throw new IllegalStateException("The genreId or artistIds is not provided.");
        }
        preparedStatement.executeBatch();
    }
}

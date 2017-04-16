package com.songrecommender.dataaccess.repository.SQL.associations;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertArtistGenreAssociationQuery {

    private int artistId = -1;
    private static final String SQL = "INSERT INTO artist_genre (artist_id, genre_Id) values (?, ?)";
    private PreparedStatement preparedStatement;

    public InsertArtistGenreAssociationQuery(Connection connection) throws SQLException {
        preparedStatement = connection.prepareStatement(SQL);
    }

    public InsertArtistGenreAssociationQuery forArtist(int artistId) {
        this.artistId = artistId;
        return this;
    }

    public InsertArtistGenreAssociationQuery withGenreId(int genreId) throws SQLException {
        preparedStatement.setInt(1, artistId);
        preparedStatement.setInt(2, genreId);
        preparedStatement.addBatch();
        return this;
    }

    public void execute() throws SQLException {
        if (artistId < 0) {
            throw new IllegalStateException("The genreId or artistIds is not provided.");
        }
        preparedStatement.executeBatch();
    }
}

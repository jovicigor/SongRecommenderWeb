package com.songrecommender.dataaccess.repository.SQL.artist;


import com.songrecommender.model.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.songrecommender.dataaccess.repository.SQL.Util.getLastInsertId;

public class InsertArtistQuery {
    private Connection connection;
    private Artist artist;
    private static final String SQL = "INSERT INTO artist (remote_Id, name, popularity) values (?,?,?)";

    public InsertArtistQuery(Connection connection) {
        this.connection = connection;
    }

    public InsertArtistQuery withArtist(Artist artist) {
        this.artist = artist;
        return this;
    }

    public int execute() throws SQLException {
        if (artist == null) {
            throw new IllegalStateException("The artist is not provided.");
        }
        PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, artist.getId());
        statement.setString(2, artist.getName());
        statement.setInt(3, artist.getPopularity());
        statement.executeUpdate();
        return getLastInsertId(statement);
    }
}

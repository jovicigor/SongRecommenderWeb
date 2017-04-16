package com.songrecommender.dataaccess.repository.SQL.genre;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.songrecommender.dataaccess.repository.SQL.Util.getLastInsertId;

public class InsertGenreQuery {
    private Connection connection;
    private String genre;
    private static final String SQL = "INSERT INTO genre (name) values (?)";

    public InsertGenreQuery(Connection connection) {
        this.connection = connection;
    }

    public InsertGenreQuery withGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public int execute() throws SQLException {
        if (genre == null) {
            throw new IllegalStateException("The genre is not provided.");
        }
        PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, genre);
        statement.executeUpdate();
        return getLastInsertId(statement);
    }
}

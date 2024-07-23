package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBManager<T, S> {
    protected Connection connection;
    public DBManager() throws SQLException, IOException {
        this.connection = SQLConnection.getConnection();
    }

    public abstract void remove(S parameter) throws SQLException;
    public abstract void add(T object) throws SQLException;
    public abstract void update(S parameter, T object) throws SQLException;
    public abstract void init() throws SQLException;
    public void close() throws SQLException{
        SQLConnection.close();
    }
}

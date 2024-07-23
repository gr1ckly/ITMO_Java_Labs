package model;

import commonModule.utils.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDBManager extends DBManager<User, String>{
    private static final String INIT_REQUEST;
    private static final String INSERT_REQUEST;
    private static final String UPDATE_REQUEST;
    private static final String REMOVE_REQUEST;
    private static final String CHECK_REQUEST;
    private static final String PEPPER;
    public UserDBManager() throws SQLException, IOException {
        Properties props = new Properties();
        props.load(Main.class.getClassLoader().getResourceAsStream("DBInfo.properties"));
        UserDBManager.PEPPER = props.getProperty("db.pepper");
        UserDBManager.INIT_REQUEST = props.getProperty("db.init");
        UserDBManager.INSERT_REQUEST = props.getProperty("db.insert");
        UserDBManager.UPDATE_REQUEST = props.getProperty("db.update");
        UserDBManager.REMOVE_REQUEST = props.getProperty("db.remove");
        UserDBManager.CHECK_REQUEST = props.getProperty("db.check");
    }

    @Override
    public void remove(String parameter) throws SQLException {
        PreparedStatement remove = this.connection.prepareStatement(UserDBManager.REMOVE_REQUEST);
        remove.setString(1, parameter);
        remove.execute();
        remove.close();
    }

    @Override
    public void add(User object) throws SQLException {
        PreparedStatement insert = this.connection.prepareStatement(UserDBManager.INSERT_REQUEST);
        insert.setString(1, object.getUserName());
        try {
            byte[] hash = this.hash(object.getPassword(), object.getSalt());
            insert.setBytes(2, hash);
            insert.setString(3, object.getSalt());
            insert.execute();
        } catch (NoSuchAlgorithmException e) {
        }
        insert.close();
    }

    @Override
    public void update(String parameter, User object) throws SQLException {
        PreparedStatement update = this.connection.prepareStatement(UserDBManager.UPDATE_REQUEST);
        update.setString(1, object.getUserName());
        try {
            update.setBytes(2, this.hash(object.getPassword(), object.getSalt()));
            update.setString(3, object.getSalt());
            update.execute();
            update.close();
        } catch (NoSuchAlgorithmException e) {
        }
        update.close();
    }

    public boolean login(User user) throws SQLException {
        PreparedStatement check = this.connection.prepareStatement(UserDBManager.CHECK_REQUEST);
        check.setString(1, user.getUserName());
        ResultSet result = check.executeQuery();
        if (result.next()){
            String salt = result.getString("SALT");
            try {
                if (new String(this.hash(user.getPassword(), salt), StandardCharsets.UTF_8).equals(new String(result.getBytes("PASSWORD"), StandardCharsets.UTF_8))){
                    result.close();
                    check.close();
                    return true;
                }
            } catch (NoSuchAlgorithmException e) {
                result.close();
                check.close();
                return false;
            }
        }
        result.close();
        check.close();
        return false;
    }

    public boolean authorization(User user) throws SQLException {
        PreparedStatement check = this.connection.prepareStatement(UserDBManager.CHECK_REQUEST);
        check.setString(1, user.getUserName());
        ResultSet result = check.executeQuery();
        if (result.next()) {
            check.close();
            result.close();
            return false;
        }
        check.close();
        result.close();
        this.add(user);
        return true;
    }

    @Override
    public void init() throws SQLException {
        PreparedStatement init = this.connection.prepareStatement(UserDBManager.INIT_REQUEST);
        init.execute();
        init.close();
    }

    public byte[] hash(String password, String salt) throws NoSuchAlgorithmException {
        Properties props = new Properties();
        props.load(Main.class.getClassLoader().getResourceAsStream("DBInfo.properties"));
        MessageDigest digest = MessageDigest.getInstance(props.getProperty("db.encryption"));
        return digest.digest((salt + password + UserDBManager.PEPPER).getBytes());
    }
}

package model;

import commonModule.collectionElements.*;

import java.io.IOException;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SpaceMarineDBManager extends DBManager<SpaceMarine, Long>{
    private static final String INIT_REQUEST = "CREATE TABLE IF NOT EXISTS SPACE_MARINE_STORAGE(" +
            "ID BIGSERIAL PRIMARY KEY," +
            "NAME TEXT NOT NULL," +
            "COORDINATES_X INT NOT NULL," +
            "COORDINATES_Y FLOAT NOT NULL CHECK(COORDINATES_Y > -873)," +
            "CREATION_DATE TIMESTAMP WITH TIME ZONE NOT NULL," +
            "HEALTH BIGINT NOT NULL CHECK (HEALTH>0)," +
            "CATEGORY ASTARTES_CATEGORY NOT NULL," +
            "WEAPON_TYPE WEAPON," +
            "MELEE_WEAPON MELEE_WEAPON," +
            "CHAPTER_NAME TEXT," +
            "CHAPTER_MARINES_COUNT BIGINT CHECK(CHAPTER_MARINES_COUNT>0 AND CHAPTER_MARINES_COUNT<=1000)," +
            "OWNER VARCHAR(100) NOT NULL REFERENCES USER_STORAGE(USER_NAME)" +
            ");";
    private static final String INSERT_REQUEST = "INSERT INTO SPACE_MARINE_STORAGE(NAME, COORDINATES_X, COORDINATES_Y, CREATION_DATE, HEALTH, CATEGORY, WEAPON_TYPE, MELEE_WEAPON, CHAPTER_NAME, CHAPTER_MARINES_COUNT, OWNER)" +
            "VALUES (?, ?, ?, ?, ?, (?::ASTARTES_CATEGORY), (?::WEAPON), (?::MELEE_WEAPON), ?, ?, ?) RETURNING ID;";
    private static final String REMOVE_REQUEST = "DELETE FROM SPACE_MARINE_STORAGE WHERE ID=?;";
    private static final String UPDATE_REQUEST = "UPDATE SPACE_MARINE_STORAGE " +
            "SET NAME = ?, COORDINATES_X = ?, COORDINATES_Y = ?, HEALTH = ?, CATEGORY = (?::ASTARTES_CATEGORY), WEAPON_TYPE = (?::WEAPON), MELEE_WEAPON = (?::MELEE_WEAPON), CHAPTER_NAME = ?, CHAPTER_MARINES_COUNT = ?" +
            "WHERE ID = ?;";
    private static final String LOAD_COLLECTION_REQUEST = "SELECT * FROM SPACE_MARINE_STORAGE;";
    private Connection connection;
    public SpaceMarineDBManager() throws SQLException, IOException {
        this.connection = SQLConnection.getConnection();
    }

    public synchronized void remove(Long parameter) throws SQLException {
        PreparedStatement remove = this.connection.prepareStatement(SpaceMarineDBManager.REMOVE_REQUEST);
        remove.setLong(1, parameter);
        remove.execute();
    }
    public synchronized void add(SpaceMarine object) throws SQLException{
            PreparedStatement insert = this.connection.prepareStatement(SpaceMarineDBManager.INSERT_REQUEST);
            insert.setString(1, object.getName());
            insert.setInt(2, object.getCoordinatesX());
            insert.setFloat(3, object.getCoordinatesY());
            insert.setObject(4, object.getCreationDate().toOffsetDateTime());
            insert.setLong(5, object.getHealth());
            insert.setString(6, object.getCategory().toString());
            if (object.getWeaponType() != null) {
                insert.setString(7, object.getWeaponType().toString());
            } else {
                insert.setNull(7, Types.VARCHAR);
            }
            if (object.getMeleeWeapon() != null) {
                insert.setString(8, object.getMeleeWeapon().toString());
            } else {
                insert.setNull(8, Types.VARCHAR);
            }
            if (object.getChapter() != null) {
                insert.setString(9, object.getChapterName());
                insert.setLong(10, object.getChapterMarinesCount());
            } else {
                insert.setNull(9, Types.VARCHAR);
                insert.setNull(10, Types.BIGINT);
            }
            insert.setString(11, object.getOwnerName());
            ResultSet result = insert.executeQuery();
            if (result.next()){
                object.setId(result.getLong("ID"));
            }
            insert.close();
            result.close();
    }
    public synchronized void update(Long parameter, SpaceMarine object) throws SQLException {
        PreparedStatement update = this.connection.prepareStatement(SpaceMarineDBManager.UPDATE_REQUEST);
        update.setString(1, object.getName());
        update.setInt(2, object.getCoordinatesX());
        update.setFloat(3, object.getCoordinatesY());
        update.setLong(4, object.getHealth());
        update.setString(5, object.getCategory().toString());
        if (object.getWeaponType() != null) {
            update.setString(6, object.getWeaponType().toString());
        } else {
            update.setNull(6, Types.VARCHAR);
        }
        if (object.getMeleeWeapon() != null) {
            update.setString(7, object.getMeleeWeapon().toString());
        } else {
            update.setNull(7, Types.VARCHAR);
        }
        if (object.getChapter() != null) {
            update.setString(8, object.getChapterName());
            update.setLong(9, object.getChapterMarinesCount());
        } else {
            update.setNull(8, Types.VARCHAR);
            update.setNull(9, Types.BIGINT);
        }
        update.setLong(10, parameter);
        update.execute();
        update.close();
    }
    public synchronized void init() throws SQLException {
        PreparedStatement init = this.connection.prepareStatement(SpaceMarineDBManager.INIT_REQUEST);
        init.execute();
        init.close();
    }
    public synchronized List<SpaceMarine> loadCollection() throws SQLException {
        PreparedStatement load = this.connection.prepareStatement(SpaceMarineDBManager.LOAD_COLLECTION_REQUEST);
        ResultSet result = load.executeQuery();
        List<SpaceMarine> collection = new LinkedList<>();
        while(result.next()) {
            SpaceMarine spaceMarine = new SpaceMarine();
            spaceMarine.setId(result.getLong("ID"));
            spaceMarine.setName(result.getString("NAME"));
            spaceMarine.setCoordinates(new Coordinates(result.getInt("COORDINATES_X"), result.getFloat("COORDINATES_Y")));
            spaceMarine.setCreationDate(result.getTimestamp("CREATION_DATE").toInstant().atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.systemDefault()));
            spaceMarine.setHealth(result.getLong("HEALTH"));
            spaceMarine.setCategory(AstartesCategory.valueOf(result.getString("CATEGORY")));
            String weaponType = result.getString("WEAPON_TYPE");
            String meleeWeapon = result.getString("MELEE_WEAPON");
            String chapterName = result.getString("CHAPTER_NAME");
            long chapterMarinesCount = result.getLong("CHAPTER_MARINES_COUNT");
            if (weaponType != null){
                spaceMarine.setWeaponType(Weapon.valueOf(weaponType));
            }
            if (meleeWeapon != null){
                spaceMarine.setMeleeWeapon(MeleeWeapon.valueOf(meleeWeapon));
            }
            if (chapterName != null){
                spaceMarine.setChapter(new Chapter(chapterName, chapterMarinesCount));
            }
            spaceMarine.setOwnerName(result.getString("OWNER"));
            collection.add(spaceMarine);
        }
        load.close();
        result.close();
        return collection;
    }

    public void close() throws SQLException {
        SQLConnection.close();
    }
}

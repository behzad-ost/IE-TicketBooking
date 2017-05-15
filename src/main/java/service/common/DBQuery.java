package service.common;

import data.Person;
import org.apache.log4j.Logger;
import service.searchManagement.SearchAll;
import service.searchManagement.SearchAllInfo;

import java.sql.ResultSet;
import java.sql.*;

/**
 * Created by ali on 5/13/17.
 */
public class DBQuery {
    final static Logger logger = Logger.getLogger(DBQuery.class);

    public Connection setupDB() throws ClassNotFoundException, SQLException {
        String url = "jdbc:hsqldb:hsql://localhost/testdb";
        String username = "SA";
        String password = "";
        logger.info("Connecting to Database");
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        Connection connection = DriverManager.getConnection(url, username, password);
        logger.info("Connected To Database!");
        return connection;
    }

    public ResultSet getSeatData(Connection connection, int sid) throws SQLException {
        Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM seat_class WHERE SID = " + sid;
        logger.debug("GET Seat: " + sql);
        return st.executeQuery(sql);
    }

    public ResultSet getFlight(Connection connection, String flightNum, String origin, String dest, String date) throws SQLException {
        Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM flight WHERE flight_num = \'" + flightNum +
                "\' AND origin = \'" + origin +
                "\' AND dest = \'" + dest +
                "\' AND flight_date = \'" + date + "\'";

        logger.debug("GET Flight: " + sql);
        return st.executeQuery(sql);
    }

    public ResultSet searchForFlights(Connection connection, String startDate, String src, String dest) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql;
        sql = "SELECT * FROM flight WHERE FLIGHT_DATE = \'" + startDate +
                "\' AND ORIGIN = \'" + src +
                "\' AND DEST = \'" + dest + "\'";
        logger.debug(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public ResultSet searchForSeatsInFlight(Connection connection, int fid) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM flight_seat_class WHERE FID = " + fid;
        return statement.executeQuery(sql);
    }

    public void addPerson(Connection connection, Person person, String tid) throws SQLException {
        PreparedStatement preparedStatement;
        String sql;
        sql = "INSERT INTO PERSON " +
                "(PID, TID, FIRST_NAME, SUR_NAME, GENDER, AGE_TYPE)\n" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, person.getNationalId());
        preparedStatement.setString(2, tid);
        preparedStatement.setString(3, person.getFirstName());
        preparedStatement.setString(4, person.getSurName());
        preparedStatement.setString(5, person.getGender());
        preparedStatement.setString(6, person.getAgeType());

        logger.debug("return type: " + preparedStatement.executeUpdate());
    }

    public void addReservation(Connection connection, String token, int fid, int sid, int adults, int infants, int childs) throws SQLException {
        PreparedStatement preparedStatement;
        String sql;
        sql = "INSERT INTO reserve " +
                "(token, fid, sid, adult_count, child_count, infant_count) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, token);
        preparedStatement.setInt(2, fid);
        preparedStatement.setInt(3, sid);
        preparedStatement.setInt(4, adults);
        preparedStatement.setInt(5, childs);
        preparedStatement.setInt(6, infants);
        preparedStatement.executeUpdate();
    }
}

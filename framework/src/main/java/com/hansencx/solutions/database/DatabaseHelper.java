package com.hansencx.solutions.database;

import org.testng.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.driver.OracleDriver;

/**
 * DatabaseHelper class
 *
 * @author Vi Nguyen
 * @version 1.0
 * @see
 * @since 2019-01-03
 */
public class DatabaseHelper {

    private static final String jdbcOracleDriver = "oracle.jdbc.driver.OracleDriver";
    private static final String dbUsername = "nguyenv";
    private static final String dbPassword = "qr5mkq15";
    private static final String dbUrl = "jdbc:oracle:thin:" + dbUsername + "/" + dbPassword;
    private static final String dbAdminFile = "C:\\oracle\\product\\12.2.0\\client_1\\network\\admin";

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public Statement getStatement() {
        return statement;
    }

    /**
     * Constructor
     */
    public DatabaseHelper() {
        try {
            Class.forName(jdbcOracleDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.setProperty("oracle.net.tns_admin", dbAdminFile);
        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Open the connection to database.
     *
     * @param
     * @return nothing.
     * @author Huong Trinh
     * @see
     * @since 2019-01-22
     */
    public void createConnection(String aliasName) {
        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(dbUrl + "@" + aliasName);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * execute Query to database.
     *
     * @param
     * @return ResultSet.
     * @author Huong Trinh
     * @see
     * @since 2019-01-22
     */
    public ResultSet executeDatabaseQuery(String queryStr) {
        try {
            resultSet = statement.executeQuery(queryStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Execute query for single data type output, give out a list string
     *
     * @param querySingleFieldValue that would like to get output
     * @return List<String> of output following column
     * @author Vi Nguyen, Huong Trinh
     * @see
     * @since 2019-01-23
     */
    public List<String> executeQueryReturnString(String querySingleFieldValue) {

        List<String> listResult = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(querySingleFieldValue);

            while (resultSet.next()) {
                listResult.add(resultSet.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listResult;
    }

    public List<Integer> executeQueryReturnInteger(String querySingleFieldValue) {

        List<Integer> listResult = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(querySingleFieldValue);

            while (resultSet.next()) {
                listResult.add(resultSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listResult;
    }

    /**
     * Execute query for single data type output, give out a list string
     *
     * @param query query a single field
     * @return String, contains values of 1 column with separator ","
     * @author Vi Nguyen
     * @see
     * @since 2019-01-28
     */
    public String returnQueriedStringField(String query) {

        String queryResult = "";
        List<String> list = executeQueryReturnString(query);
        for (int i = 0; i < list.size(); i++)
            if (list.size() == 1)
                return list.get(0);
            else
                queryResult += list.get(i) + ",";

        return queryResult;
    }

    /**
     * Execute query for single data type output, give out a list of Integer member
     *
     * @param observedResult , expectedResult
     * @return Pass if observedResult = expectedResult, otherwise, fail
     * @author Vi Nguyen
     * @see
     * @since 2019-01-28
     */
    public void checkResult(String observedResult, String expectedResult) {
        Assert.assertEquals(observedResult, expectedResult);
    }

    /**
     * close the connection to database.
     *
     * @param
     * @return Nothing.
     * @author Vi Nguyen
     * @see
     * @since 2019-01-03
     */
    public void closeDatabaseConnection() {
        try {
            if (null != resultSet)
                resultSet.close();
            if (null != statement)
                statement.close();
            if (null != connection)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

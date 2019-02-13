package com.hansencx.solutions.database;

import oracle.jdbc.driver.OracleDriver;
import org.testng.Assert;
import utilities.helper.SoftAssert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    SoftAssert softAssert;

    /**
     * Getters and Setters
     */
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
     * Execute query only single column output, give out a list string
     *
     * @param query is supposed to output values of only 1 column
     * @return List<String> of the queried column
     * @author Vi Nguyen, Huong Trinh
     * @see
     * @since 2019-01-23
     */
    public List<String> executeQueryReturnString(String query) {

        List<String> listResult = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                listResult.add(resultSet.getString(1));
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
    public void checkResultByAssert(String observedResult, String expectedResult) {
        Assert.assertEquals(observedResult, expectedResult);
    }
    /**
     * get soft assert obj
     *
     * @param softAssert
     * @author HuonG trINH
     * @see
     * @since 2019-02-11
     */
    public void getSoftAssertObj(SoftAssert softAssert){
        this.softAssert = softAssert;
    }
    /**
     * check result by soft assert
     *
     * @param observedResult , expectedResult
     * @return Pass if observedResult = expectedResult, otherwise, fail
     * @author HuonG trINH
     * @see
     * @since 2019-02-11
     */
    public void checkResultBySoftAssert(String observedResult, String expectedResult) {
        softAssert.assertEquals(observedResult, expectedResult, "SQL Query Validation: ");
    }

    public void checkResultBySoftAssertWithMsg(String observedResult, String expectedResult, String messageContainSQLKeyWord) {
        if(messageContainSQLKeyWord.toUpperCase().contains("SQL")) {
            softAssert.assertEquals(observedResult, expectedResult, messageContainSQLKeyWord);
        }else{
            softAssert.assertEquals(observedResult, expectedResult, "SQL Query Validation: " +messageContainSQLKeyWord );
        }
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
    public void closeConnection() {
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

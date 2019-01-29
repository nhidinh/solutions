package com.hansencx.solutions.database;

import org.testng.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.driver.OracleDriver;

/**
 * DatabaseHelper class
 *
 * @author  Vi Nguyen
 * @version 1.0
 * @since   2019-01-03
 * @see
 */
public class DatabaseHelper  {

    private static final String jdbcOracleDriver = "oracle.jdbc.driver.OracleDriver";
    private static final String dbUsername = "nguyenv";
    private static final String dbPassword = "qr5mkq15";
    private static final String dbUrl = "jdbc:oracle:thin:" + dbUsername + "/" + dbPassword;
    private static final String dbAdminFile = "C:\\oracle\\product\\12.2.0\\client_1\\network\\admin";

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;


    /**
     * Constructor
     */
    public DatabaseHelper(){
        try {
            Class.forName(jdbcOracleDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.setProperty("oracle.net.tns_admin",dbAdminFile);
        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Open the connection to database.
     * @author Huong Trinh
     * @param
     * @return nothing.
     * @since   2019-01-22
     * @see
     */
    public void createConnection(String aliasName){
        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection= DriverManager.getConnection(dbUrl + "@" + aliasName);
            statement =  connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * execute Query to database.
     * @author Huong Trinh
     * @param
     * @return ResultSet.
     * @since   2019-01-22
     * @see
     */
    public ResultSet executeDBQuery( String queryStr){
        try {
            resultSet = statement.executeQuery(queryStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Execute query for single data type output, give out a list string
     * @author Vi Nguyen, HuONG tRINH
     * @param querySingleFieldValue that would like to get output
     * @return List<String> of output following column
     * @since   2019-01-23
     * @see
     */
    public List<String> executeQueryReturnString(String querySingleFieldValue){

        List<String> dbList = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(querySingleFieldValue);

            while (resultSet.next()){
                dbList.add(resultSet.getString(1));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return dbList;
    }
    public List<Integer> executeQueryReturnInteger(String querySingleFieldValue){

        List<Integer> dbList = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(querySingleFieldValue);

            while (resultSet.next()){
                    dbList.add(resultSet.getInt(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dbList;
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
    public String returnQueriedStringField(String query){

        String queryResult = "";
        List<String> list = executeQueryReturnString(query);
        for(int i=0; i<list.size(); i++)
            if(list.size() == 1)
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
    public void checkResult(String observedResult, String expectedResult){
        Assert.assertEquals(observedResult, expectedResult);
    }

    /**
     * close the connection to database.
     * @author Vi Nguyen
     * @param
     * @return Nothing.
     * @since   2019-01-03
     * @see
     */
    public void closeDBConnection(){
        try {
            if(null != resultSet)
                resultSet.close();
            if (null != statement)
                statement.close();
            if (null != connection)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Statement getStatement() {
        return statement;
    }
}

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
public class DatabaseHelper {

    private static final String jdbcOracleDriver = "oracle.jdbc.driver.OracleDriver";
    private static final String dbUsername = "nguyenv";
    private static final String dbPassword = "qr5mkq15";
    private static final String dbUrl = "jdbc:oracle:thin:" + dbUsername + "/" + dbPassword;
    private static final String dbAdminFile = "C:\\oracle\\product\\12.2.0\\client_1\\network\\admin";

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private List<String> list = new ArrayList();

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
     * Execute query for multiple data output, give out a list string
     * @author Vi Nguyen, HuONG tRINH
     * @param query and numberColumns that would like to get output
     * @return List<String> of output following column
     * @since   2019-01-23
     * @see
     */
    public List<String> executeQueryToStringColumn(String query, int numberColumns){

        List<String> dbList = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                for(int i = 1;i<=numberColumns;i++){
                    dbList.add(resultSet.getString(i));
                }
            }
            System.out.println("resultset: "+ dbList.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
        return dbList;
    }

    public List<String> executeQueryToStringRow(String query, int columnIndex){

        List<String> dbList = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                    dbList.add(resultSet.getString(columnIndex));
            }
            System.out.println("resultset: "+ dbList.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
        return dbList;
    }

    public List<String> exQueryColumnLable(String query, String columnName){
        List<String> dbList = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                dbList.add(resultSet.getString(columnName));
            }
            System.out.println("resultset: "+ dbList.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
        return dbList;
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
}

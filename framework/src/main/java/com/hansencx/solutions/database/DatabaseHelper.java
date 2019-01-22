package com.hansencx.solutions.database;

import org.testng.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    private static final String dbUrl = "jdbc:oracle:thin:@10.50.172.72:49161:xe";

    private static final String dbUsername = "system";
    private static final String dbPassword = "oracle";

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
    }

    /**
     * Open the connection to database.
     * @author Huong Trinh
     * @param
     * @return Nothing.
     * @since   2019-01-03
     * @see
     */
    public void open(){
        try {
            connection= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * close the connection to database.
     * @author Vi Nguyen
     * @param
     * @return Nothing.
     * @since   2019-01-03
     * @see
     */
    public void close(){
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

    /**
     * Execute query. Supported only one field for now.
     * @author Vi Nguyen
     * @param query
     * @return Nothing.
     * @since   2019-01-03
     * @see
     */
    public void executeQuery(String query){
        try {
            resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                list.add(resultSet.getString(1));
                System.out.println(resultSet.getString(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Assert Result. Supported only one field for now.
     * @author Vi Nguyen
     * @param expectedData
     * @return Nothing.
     * @since   2019-01-03
     * @see
     */
    public void assertResult(String expectedData){
        List<String> expectedList = new ArrayList<String>();
        expectedList.add(expectedData);

        Assert.assertEquals(expectedList, list);
        // Temporarily put here, will remove later
        System.out.println("WIN");
    }

}

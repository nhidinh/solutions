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
    private String outputString = null;
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
    public String querySpecificInfoFollowingKyEnroll(String queryLabel, String tranID, String purposeCode){

        String queryInput = "select " + queryLabel + " from custpro.cpm_pnd_tran_hdr where ky_enroll in(select ky_enroll " +
                "from custpro.cpm_pnd_tran_hdr " +
                "where ky_pnd_seq_trans =" + tranID + ") " +
                "and cd_tran_status = 28 " +
                "and cd_purpose = "+ purposeCode;
        try {
            resultSet = statement.executeQuery(queryInput);
            while(resultSet.next()){
                outputString = resultSet.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outputString;
    }
    public String querySpecificInfoInProcessedTrans(String queryLabel, String tranID){

        String queryInput = "select "+ queryLabel + " from custpro.cpm_pnd_tran_hdr where ky_pnd_seq_trans ="+ tranID;

        try {
            resultSet = statement.executeQuery(queryInput);
            while(resultSet.next()){
                outputString = resultSet.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outputString;
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

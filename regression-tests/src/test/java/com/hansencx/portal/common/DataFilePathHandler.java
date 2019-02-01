package com.hansencx.portal.common;

import utilities.configuration.InitialData;

/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/30/2019
 */


public class DataFilePathHandler {
    public static String DATA_DIRECTORY_PATH = InitialData.PARENT_DIR + "\\regression-tests\\src\\test\\java\\com\\hansencx\\portal\\datatest\\";
    public static String PORTAL_DATA_FILE_NAME = "PortalDataTest.xlsx";
    public static String PORTAL_DATA_SHEET_NAME = "EnrollmentNumber";
    public static String PORTAL_DATA_TEST_PATH = DATA_DIRECTORY_PATH+PORTAL_DATA_FILE_NAME;

    public static String CREATE_CANCEL_REBILL_FILE_NAME = "Create Cancel Rebill - Filled In Template.xlsx";
    public static String CREATE_CANCEL_REBILL_SHEET_NAME = "Create Cancel Rebill";
    public static String CREATE_CANCEL_REBILL_PATH = DATA_DIRECTORY_PATH+CREATE_CANCEL_REBILL_FILE_NAME;

    public static String USER_CREDENTIAL_JSON_PATH = DATA_DIRECTORY_PATH + "UserCredential.json";
}

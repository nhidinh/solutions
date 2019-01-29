package com.hansencx.portal.tests;

import com.hansencx.solutions.database.DatabaseHelper;

public class DatabaseTests {
    private static DatabaseHelper databaseHelper = new DatabaseHelper();

    public static void main(String args[]) {
        databaseHelper.createConnection("PSOLQ");
        String query = "select count(KY_BA) from custpro.cpm_pnd_tran_hdr where ky_enroll in(select ky_enroll from custpro.cpm_pnd_tran_hdr where ky_pnd_seq_trans = 61761469)";

        String queryResult = databaseHelper.returnQueriedStringField(query);

        System.out.println("result = " + queryResult);

        if(queryResult.equals("42"))
            System.out.println("WIN");
    }
}



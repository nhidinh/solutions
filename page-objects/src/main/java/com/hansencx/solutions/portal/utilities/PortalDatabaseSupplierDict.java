package com.hansencx.solutions.portal.utilities;

import java.util.HashMap;
import java.util.Map;

/** Author : HUONG TRINH
 * SCOPE: The text name is different between Oracle db and Web presentation
 * this class will build a dictionary to map them as main purpose
 */
public class PortalDatabaseSupplierDict {

    private static Map<String, String> map = new HashMap<String, String>(){
        {
            put("THINK2", "Think Energy2");
            put("THINK", "Think Energy");
            put("SMART", "Smart Energy, LLC");
            put("SPERIAN", "Sperian Energy Corp.");
            put("TESI", "ENGIE Resources");
        }
    };
    /* Get supplier name in Web by provide supplier key from database
     */
    public static String getValue(String keySupplierName){
        return map.get(keySupplierName);
    }

}

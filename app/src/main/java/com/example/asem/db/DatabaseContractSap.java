package com.example.asem.db;

import android.provider.BaseColumns;

public class DatabaseContractSap {
    static String TABLE_NAME = "sap";
    static final class SapColumns implements BaseColumns {
        static String  SAPID = "sap_id";
        static String SAPDESC = "sap_desc";
        static String SAPNAME = "sap_name";
        static String UNITID = "unit_id ";
    }
}

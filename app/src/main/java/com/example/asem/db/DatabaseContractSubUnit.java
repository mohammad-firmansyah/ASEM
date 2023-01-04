package com.example.asem.db;

import android.provider.BaseColumns;

public class DatabaseContractSubUnit {
    static String TABLE_NAME = "sub_unit";
    static final class SubUnitColumns implements BaseColumns {
        static String SUBUNITID = "sub_unit_id";
        static String SUBUNITDESC = "sub_unit_desc";
    }
}

package com.example.asem.db;

import android.provider.BaseColumns;

public class DatabaseContractAlatPengangkutan {
    static String TABLE_NAME = "aset_pengangkutan";
    static final class AlatPengangkutanColumns implements BaseColumns {
        static String APID  = "ap_id";
        static String APDESC = "ap_desc";
}
}

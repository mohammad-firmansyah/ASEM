package com.example.asem.db;

import android.provider.BaseColumns;

public class DatabaseContractAsetTipe {

    static String TABLE_NAME = "aset_tipe";
    static final class AsetTipeColumns implements BaseColumns {
        static String ASETTIPEID  = "aset_tipe_id";
        static String ASETTIPEDESC = "aset_tipe_desc";
    }
}

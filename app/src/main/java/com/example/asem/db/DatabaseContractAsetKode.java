package com.example.asem.db;

import android.provider.BaseColumns;

public class DatabaseContractAsetKode {
    static String TABLE_NAME = "aset_kode";
    static final class AsetKodeColumns implements BaseColumns {
        static String ASETKODEID  = "aset_tipe_id";
        static String CREATEDAT = "created_at";
        static String UPDATEDAT = "updated_at";
        static String ASETCLASS = "aset_class";
        static String ASETGROUP = "aset_group";
        static String ASETDESC = "aset_desc";
        static String ASETJENIS = "aset_jenis";
    }
}

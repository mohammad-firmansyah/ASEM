package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractSistemTanam {
    public static String TABLE_NAME = "sistem_tanam";
    public static final class SistemTanamColumns implements BaseColumns {
        public static String SISTEMTANAMID  = "st_id";
        public static String SISTEMTANAMDESC = "st_desc";
    }
}

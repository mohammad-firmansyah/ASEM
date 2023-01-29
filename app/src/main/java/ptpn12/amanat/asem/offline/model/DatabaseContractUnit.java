package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractUnit {
    public static String TABLE_NAME = "unit";
    public static final class UnitColumns implements BaseColumns {
        public static String UNITID = "unit_id";
        public static String UNITDESC = "unit_desc";
    }
}
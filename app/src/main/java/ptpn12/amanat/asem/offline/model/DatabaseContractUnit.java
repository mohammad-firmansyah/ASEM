package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractUnit {
    static String TABLE_NAME = "unit";
    static final class UnitColumns implements BaseColumns {
        static String UNITID = "unit_id";
        static String UNITDESC = "unit_desc";
    }
}
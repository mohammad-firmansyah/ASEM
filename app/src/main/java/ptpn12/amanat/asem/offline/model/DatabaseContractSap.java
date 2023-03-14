package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractSap {
    public static String TABLE_NAME = "sap";
    public static final class SapColumns implements BaseColumns {
        public static String SAPID = "sap_id";
        public static String SAPDESC = "sap_desc";
        public static String SAPNAME = "sap_name";
        public static String UNITID = "unit_id";
        public static String NILAIOLEH = "nilai_oleh";
        public static String NILAIRESIDU = "nilai_residu";
        public static String MASASUSUT = "masa_susut";
        public static String TGLOLEH = "tgl_oleh";
    }
}

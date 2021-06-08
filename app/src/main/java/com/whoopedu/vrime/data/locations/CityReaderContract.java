package com.whoopedu.vrime.data.locations;

import android.provider.BaseColumns;

public class CityReaderContract {
    private CityReaderContract() {};

    public static class CityEntry implements BaseColumns {
        public static final String TABLE_NAME = "Cities";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_STATE = "state";
        public static final String COLUMN_NAME_COUNTRY = "country";
        public static final String COLUMN_NAME_COORD_LON = "coord.lon";
        public static final String COLUMN_NAME_COORD_LAT = "coord.lat";
    }
}

package com.whoopedu.vrime.data.locations;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class LocationsDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 1;

    public LocationsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }
}

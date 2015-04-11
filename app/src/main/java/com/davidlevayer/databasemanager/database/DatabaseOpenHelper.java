package com.davidlevayer.databasemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.davidlevayer.databasemanager.table.DatabaseTable;

import java.util.List;

/**
 * Created by David Levayer on 11/04/15.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private Context mContext;
    private String mDatabaseName;
    private int mVersion;
    private List<DatabaseTable> mTables;

    private final static String DEFAULT_DATABASE_NAME = "mDatabase.db";
    private final static int DEFAULT_VERSION = 0;

    public DatabaseOpenHelper(Context context) {
        super(context, DEFAULT_DATABASE_NAME, null, DEFAULT_VERSION);
        mContext = context;
        mDatabaseName = DEFAULT_DATABASE_NAME;
        mVersion = DEFAULT_VERSION;
    }

    public DatabaseOpenHelper(Context context, String databaseName) {
        super(context, databaseName, null, DEFAULT_VERSION);
        mContext = context;
        mDatabaseName = databaseName;
        mVersion = DEFAULT_VERSION;
    }

    public DatabaseOpenHelper(Context context, String databaseName, int version) {
        super(context, databaseName, null, version);
        mContext = context;
        mDatabaseName = databaseName;
        mVersion = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(DatabaseTable table : mTables)
            db.execSQL(table.getConstructionQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(DatabaseTable table : mTables)
            db.execSQL("DROP TABLE IF EXISTS "+table.getTableName());
        mVersion = newVersion;
        onCreate(db);
    }
}

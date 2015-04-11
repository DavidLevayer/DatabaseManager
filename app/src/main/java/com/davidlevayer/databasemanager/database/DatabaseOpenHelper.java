package com.davidlevayer.databasemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.davidlevayer.databasemanager.table.DatabaseTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by David Levayer on 11/04/15.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private Context mContext;
    private String mDatabaseName;
    private int mVersion;
    private Map<String,DatabaseTable> mTables;

    private final static String DEFAULT_DATABASE_NAME = "mDatabase.db";
    private final static int DEFAULT_VERSION = 0;

    public DatabaseOpenHelper(Context context) {
        super(context, DEFAULT_DATABASE_NAME, null, DEFAULT_VERSION);
        mContext = context;
        mTables = new HashMap<>();
        mDatabaseName = DEFAULT_DATABASE_NAME;
        mVersion = DEFAULT_VERSION;
    }

    public DatabaseOpenHelper(Context context, String databaseName) {
        super(context, databaseName, null, DEFAULT_VERSION);
        mContext = context;
        mTables = new HashMap<>();
        mDatabaseName = databaseName;
        mVersion = DEFAULT_VERSION;
    }

    public DatabaseOpenHelper(Context context, String databaseName, int version) {
        super(context, databaseName, null, version);
        mContext = context;
        mTables = new HashMap<>();
        mDatabaseName = databaseName;
        mVersion = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(DatabaseTable table : mTables.values())
            db.execSQL(table.getConstructionQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(DatabaseTable table : mTables.values())
            db.execSQL("DROP TABLE IF EXISTS "+table.getTableName());
        mVersion = newVersion;
        onCreate(db);
    }

    private void checkTableValidity(DatabaseTable table){

        if(table == null)
            throw new NullPointerException("DatabaseTable parameter has null value");

        String tableName = table.getTableName();
        if(tableName == null || tableName.isEmpty())
            throw new NullPointerException("DatabaseTable table name is null or empty");
    }

    private boolean containsTable(DatabaseTable table){
        return mTables.containsKey(table.getTableName());
    }

    public boolean addTable(SQLiteDatabase db, DatabaseTable table){

        if(db == null)
            throw new NullPointerException("SQLiteDatabase parameter has null value");

        checkTableValidity(table);

        if(containsTable(table))
            return false;

        this.mTables.put(table.getTableName(), table);
        db.execSQL(table.getConstructionQuery());
        return true;
    }

    public boolean removeTable(SQLiteDatabase db, DatabaseTable table){

        if(db == null)
            throw new NullPointerException("SQLiteDatabase parameter has null value");

        checkTableValidity(table);

        if(!containsTable(table))
            return false;

        this.mTables.remove(table);
        db.execSQL("DROP TABLE IF EXISTS "+table.getTableName());
        return true;
    }
}

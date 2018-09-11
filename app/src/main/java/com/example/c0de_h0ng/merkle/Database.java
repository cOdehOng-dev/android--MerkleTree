package com.example.c0de_h0ng.merkle;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by c0de_h0ng on 2018. 8. 12..
 */

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Test.db";
    private static final String[] TABLE_NAMES = {"'Number'", "`LeafNode`", "`BottomLevel`", "`InternalLevel`"
            , "'MerkleRoot'"};


    public Database(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String CREATE = "CREATE TABLE `Number`( `employeeNo` INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, `Number` TEXT )";

        db.execSQL(CREATE);

        CREATE = "CREATE TABLE `LeafNode`( `LeafNode` TEXT )";

        db.execSQL(CREATE);

        CREATE = "CREATE TABLE `BottomLevel`( `BottomLevelNo` INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, `BottomLevelNode` TEXT )";

        db.execSQL(CREATE);

        CREATE = "CREATE TABLE `InternalLevel`( `InternalLevelNo` INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, `InternalLevelNode` TEXT )";

        db.execSQL(CREATE);

        CREATE = "CREATE TABLE `MerkleRoot`( `MerkleRoot` TEXT )";

        db.execSQL(CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES[0]);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES[1]);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES[2]);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES[3]);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES[4]);

        // Create tables again
        onCreate(db);
    }

    public void insertNumber(String Number) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Number", Number);
        db.insert(TABLE_NAMES[0], null, values);
    }

    public void insertLeafNode(String LeafNode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("LeafNode", LeafNode);
        db.insert(TABLE_NAMES[1], null, values);
    }

    public void insertBottomLevel(String BottomLevelNode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("BottomLevelNode", BottomLevelNode);
        db.insert(TABLE_NAMES[2], null, values);
    }

    public void insertInternalLevel(String InternalLevelNode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("InternalLevelNode", InternalLevelNode);
        db.insert(TABLE_NAMES[3], null, values);
    }


    public void insertMerkleRoot(String MerkleRoot) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("MerkleRoot", MerkleRoot);
        db.insert(TABLE_NAMES[4], null, values);
    }

}
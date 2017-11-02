package com.codepath.mypizza.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codepath.mypizza.entity.Item;

public class ItemsDbAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";
    public static final String KEY_PATH_TO_PHOTO = "path_to_photo";
    public static final String KEY_IS_CHECKED = "is_checked";

    private static final String TAG = "ItemsDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "World";
    private static final String SQLITE_TABLE = "Item";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_NAME + "," +
                    KEY_PRICE + "," +
                    KEY_IS_CHECKED + "," +
                    KEY_PATH_TO_PHOTO + " BLOB" +
                    ");";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public ItemsDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public ItemsDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createItem(String name, float price, boolean isChecked,
                           byte[] pathToPhoto) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_PRICE, price);
        initialValues.put(KEY_IS_CHECKED, isChecked);
        initialValues.put(KEY_PATH_TO_PHOTO, pathToPhoto);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllItems() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null, null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;
    }

    public boolean deleteItem(long id) {
        return mDb.delete(SQLITE_TABLE, KEY_ROWID + "=" + id, null) > 0;
    }

    public int updateItem(Item item) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_ROWID, item.getId());
        initialValues.put(KEY_NAME, item.getName());
        initialValues.put(KEY_PRICE, item.getPrice());
        initialValues.put(KEY_IS_CHECKED, item.getSelected());
       // initialValues.put(KEY_PATH_TO_PHOTO, item.getPathToPhoto());

        return mDb.update(SQLITE_TABLE, initialValues, ItemsDbAdapter.KEY_ROWID + "=" + Long.toString(item.getId()), null);

    }

    public Cursor fetchItemsByName(String inputText) throws SQLException {

        Cursor mCursor = null;
        if (inputText == null || inputText.length() == 0) {
            mCursor = mDb.query(SQLITE_TABLE, new String[]{KEY_ROWID,
                            KEY_NAME, KEY_PRICE, KEY_IS_CHECKED, KEY_PATH_TO_PHOTO},
                    null, null, null, null, null);

        } else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[]{KEY_ROWID,
                            KEY_NAME, KEY_PRICE, KEY_IS_CHECKED, KEY_PATH_TO_PHOTO},
                    KEY_NAME + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchAllItems() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[]{KEY_ROWID,
                        KEY_NAME, KEY_PRICE, KEY_IS_CHECKED, KEY_PATH_TO_PHOTO},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeItems() {

        createItem("iPhone", 999.9f, false, new byte[]{});
        createItem("samsung", 919.9f, false, new byte[]{});
        createItem("pixel", 929.9f, false, new byte[]{});
        createItem("iPad", 939.9f, false, new byte[]{});

    }
}

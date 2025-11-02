package com.example.application_navibountho;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import java.net.URI;

public class MyContentProvider extends ContentProvider {

    public final static String  DB_NAME  = "PokedexDataBase";
    public final static String TABLENAME = "pokedex";
    public final static String NATIONAL_NUM_COL = "NationalNum";
    public final static String NAME_COL = "Name";
    public final static String SPECIES_COL = "Species";
    public final static String HEIGHT_COL = "Height";
    public final static String WEIGHT_COL = "Weight";
    public final static String LEVEL_COL = "Level";
    public final static String GENDER_COL = "Gender";
    public final static String HP_COL = "HP";
    public final static String ATTACK_COL = "Attack";
    public final static String DEFENSE_COL = "Defense";
    public final static String ID_COL = "ID";
    private static final String  SQL_CREATE_MAIN = "CREATE TABLE pokedex ( " + ID_COL +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + NATIONAL_NUM_COL + " INTEGER UNIQUE, " +
            NAME_COL + " TEXT, " +
            SPECIES_COL + " TEXT, " +
            HEIGHT_COL + " REAL, " +
            WEIGHT_COL + " REAL, " +
            GENDER_COL + " TEXT, " +
            HP_COL + " INTEGER, " +
            ATTACK_COL + " INTEGER, " +
            LEVEL_COL + " INTEGER, " +
            DEFENSE_COL + " INTEGER);";

    public static final Uri CONTENT_URI = Uri.parse("content://com.example.application_navibountho.MyContentProvider/pokedex");
    private MainDatabaseHelper mHelper;


protected final class MainDatabaseHelper extends SQLiteOpenHelper {

    MainDatabaseHelper (Context context){
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MAIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }
}
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        mHelper = new MainDatabaseHelper(getContext());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String id = uri.getLastPathSegment();
        String newSelection = ID_COL + " = ?";
        String[] newSelectionArgs = {id};
        selection = newSelection;
        selectionArgs = newSelectionArgs;
        return db.delete(TABLENAME, selection, selectionArgs);


//        int rowsDelete = db.delete(TABLENAME, selection, selectionArgs);
//        if(rowsDelete > 0 && getContext() != null){
//            getContext().getContentResolver().notifyChange(uri, null);
//        }

       // return rowsDelete;

      //  throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        long id = mHelper.getWritableDatabase().insert(TABLENAME, null,values);
        if(id > 0 && getContext() != null){
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.withAppendedPath(CONTENT_URI, "" + id);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLENAME, projection,selection,selectionArgs,null,null,sortOrder);
        if(getContext() != null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;

        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase db = mHelper.getWritableDatabase();// 2. Perform the update and get the number of rows affected.
        int rowsUpdated = db.update(TABLENAME, values, selection, selectionArgs);

        // 3. If rows were updated, notify the content resolver to update the UI.
        if (rowsUpdated > 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // 4. Return the number of rows updated.
        return rowsUpdated;


       // throw new UnsupportedOperationException("Not yet implemented");
    }
}
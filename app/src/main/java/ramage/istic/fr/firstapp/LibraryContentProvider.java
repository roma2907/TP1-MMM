package ramage.istic.fr.firstapp;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

import ramage.istic.fr.firstapp.model.User;

/**
 * Created by ramage on 21/03/16.
 */
public class LibraryContentProvider extends ContentProvider {

    public static User data[];

    public static final String _ID = "_ID";
    public static final String USER_NAME = "name";
    public static final String USER_LASTNAME = "lastname";
    public static final String USER_DATE = "date";
    public static final String USER_CITY = "city";
    public static final String USER_DEPARTEMENT = "department";

    public static final String PROVIDER_NAME = "com.example.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME+"/users");

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    static final int STUDENTS = 1;
    static final int STUDENT_ID = 2;
    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "users", STUDENTS);
        uriMatcher.addURI(PROVIDER_NAME, "users/#", STUDENT_ID);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(STUDENTS_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                break;

            case STUDENT_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder == ""){
            /**
             * By default sort on student names
             */
            sortOrder = USER_NAME;
        }
        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all student records
             */
            case STUDENTS:
                return "vnd.android.cursor.dir/vnd.example.users";

            /**
             * Get a particular student
             */
            case STUDENT_ID:
                return "vnd.android.cursor.item/vnd.example.users";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new student record
         */
        long rowID = db.insert(	STUDENTS_TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */

        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "BDD";
    static final String STUDENTS_TABLE_NAME = "users";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE = "DROP TABLE "+STUDENTS_TABLE_NAME+";"+
            " CREATE TABLE " + STUDENTS_TABLE_NAME +
                    " ("+ _ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NAME+" TEXT NOT NULL, " +
                    USER_LASTNAME+" TEXT NOT NULL," +
                    USER_DATE+" TEXT NOT NULL," +
                    USER_DEPARTEMENT+" TEXT NOT NULL," +
                    USER_CITY+" TEXT NOT NULL)";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  STUDENTS_TABLE_NAME);
            onCreate(db);
        }
    }
}

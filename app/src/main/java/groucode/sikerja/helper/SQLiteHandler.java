package groucode.sikerja.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper{

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "tryn6646_sikerja";

    private static final String TABLE_USER = "user";

    private static final String KEY_ID = "id";
    private static final String KEY_NIK = "nik";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TOKEN = "token";


    public SQLiteHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NIK + " TEXT UNIQUE," + KEY_NAMA + " TEXT," + KEY_EMAIL + " TEXT," + KEY_TOKEN + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }


    public void addUser(String nik, String nama, String email, String token){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NIK, nik);
        values.put(KEY_NAMA, nama);
        values.put(KEY_EMAIL, email);
        values.put(KEY_TOKEN, token);
        long uid = db.insert(TABLE_USER, null, values);
        db.close();

        Log.d(TAG, "New user Inserted into sqlite: " +uid);
    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM "+ TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            user.put("nik", cursor.getString(1));
            user.put("nama", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("token", cursor.getString(4));
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching user from Sqlite: "+ user.toString());

        return user;
    }

    public void deleteUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}

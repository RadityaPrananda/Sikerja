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

    private static final String DATABASE_NAME = "xcloudsy_arsipsurat";

    private static final String TABLE_USER = "user";

    private static final String KEY_ID = "id";
    private static final String KEY_NIP = "nip";

    public SQLiteHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
//        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NIP + " TEXT UNIQUE," + KEY_NAMA + " TEXT," + KEY_NRP + " TEXT," + KEY_TTL + " TEXT," + KEY_AGAMA + " TEXT," + KEY_JK + " TEXT," + KEY_ALAMAT + " TEXT," + KEY_PENDIDIKAN + " TEXT," + KEY_GOLRU + " TEXT," + KEY_PANGKAT + " TEXT," + KEY_STRUKTURAL + " TEXT," + KEY_FUNGSIONAL + " TEXT," + KEY_TMT_JAKSA + " TEXT," + KEY_PASSWORD + " TEXT" + ")";
//        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }


    public void addUser(String nip, String nama, String nrp, String ttl, String agama, String jk, String alamat, String pendidikan, String golru, String pangkat, String struktural, String fungsional, String tmt_jaksa, String password ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NIP, nip);


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
            user.put("nip", cursor.getString(1));
            user.put("nama", cursor.getString(2));
            user.put("nrp", cursor.getString(3));
            user.put("ttl", cursor.getString(4));
            user.put("agama", cursor.getString(5));
            user.put("jk", cursor.getString(6));
            user.put("alamat", cursor.getString(7));
            user.put("pendidikan", cursor.getString(8));
            user.put("golru", cursor.getString(9));
            user.put("pangkat", cursor.getString(10));
            user.put("struktural", cursor.getString(11));
            user.put("fungsional", cursor.getString(12));
            user.put("tmt_jaksa", cursor.getString(13));
            user.put("password", cursor.getString(14));

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

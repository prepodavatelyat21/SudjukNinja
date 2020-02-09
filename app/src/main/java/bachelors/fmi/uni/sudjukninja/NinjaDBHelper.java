package bachelors.fmi.uni.sudjukninja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NinjaDBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "ninjadb.sqlite";
    public static final int DB_VERSION = 1;

    public static final String USER_TABLE = "User";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_GENDER = "gender";

    public static final String CREATE_TABLE_USER =
                    "CREATE TABLE " + USER_TABLE + "('" +
                    COLUMN_ID  + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "'" + COLUMN_USERNAME + "' VARCHAR(50) NOT NULL UNIQUE," +
                    "'" + COLUMN_EMAIL + "' VARCHAR(250)," +
                    "'" + COLUMN_PASSWORD + "' VARCHAR(50) NOT NULL," +
                    "'" + COLUMN_GENDER + "' VARCHAR(50) DEFAULT 'APACHE HELICOPTER')";

    public NinjaDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    public boolean registerUser(User user){

        SQLiteDatabase db = null;

        try{
            db = getWritableDatabase();

            ContentValues cv = new ContentValues();

            cv.put(COLUMN_EMAIL, user.email);
            cv.put(COLUMN_GENDER, user.gender);
            cv.put(COLUMN_PASSWORD, user.password);
            cv.put(COLUMN_USERNAME, user.username);

            long id = db.insert(USER_TABLE, null, cv);

            if(id != -1){
                return true;
            }

        }catch (SQLException e){
            Log.wtf("WTF ERROR", e.getMessage());
        }finally {
            if(db != null)
                db.close();
        }

        return false;
    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = null;
        Cursor c = null;

        try{

            db = getReadableDatabase();

            String query = "SELECT * FROM " + USER_TABLE
                    + " WHERE " + COLUMN_USERNAME +
                    " = '" + username + "' AND " +
                    COLUMN_PASSWORD + " = '" + password + "'";

            c = db.rawQuery(query, null);

            return c.moveToFirst();

        }catch (SQLException e){
            Log.wtf("ERROR", e.getMessage());
        }finally {
            if(db != null)
            {
                if(c != null)
                    c.close();

                db.close();
            }

        }

        return false;
    }
}

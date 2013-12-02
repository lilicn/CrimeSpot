package edu.vanderbilt.cs278.safespot.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "zones.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_ZONE = "zone";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_ZONEID = "zoneID";
	public static final String COLUMN_SCORE = "score";
	public static final String COLUMN_REVIEW = "review";
	public static final String COLUMN_AVEREV = "aveRev";
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_ZONE
			+ " (" + COLUMN_ID + " INTEGER primary key autoincrement, "
			+ COLUMN_ZONEID + " INTEGER not null, " + COLUMN_SCORE
			+ " REAL not null, " + COLUMN_REVIEW + " INTEGER not null, "
			+ COLUMN_AVEREV + " REAL not null);";

	public MySqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySqliteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZONE);
		onCreate(db);
	}

}

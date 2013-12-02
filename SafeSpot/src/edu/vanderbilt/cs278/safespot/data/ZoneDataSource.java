package edu.vanderbilt.cs278.safespot.data;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Comment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ZoneDataSource {
	// Database fields
	  private SQLiteDatabase database;
	  private MySqliteHelper dbHelper;
	  private String[] allColumns = { MySqliteHelper.COLUMN_ID,
	      MySqliteHelper.COLUMN_ZONEID,MySqliteHelper.COLUMN_SCORE, MySqliteHelper.COLUMN_REVIEW, MySqliteHelper.COLUMN_AVEREV };

	  public ZoneDataSource(Context context) {
	    dbHelper = new MySqliteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Zone createZone(String comment) {
	    ContentValues values = new ContentValues();
	    values.put(MySqliteHelper.COLUMN_ZONEID, comment);
	    long insertId = database.insert(MySqliteHelper.TABLE_ZONE, null,
	        values);
	    Cursor cursor = database.query(MySqliteHelper.TABLE_ZONE,
	        allColumns, MySqliteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Zone newComment = cursorToComment(cursor);
	    cursor.close();
	    return newComment;
	  }

	  public void deleteZone(Zone zone) {
	    long id = zone.getID();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(MySqliteHelper.TABLE_ZONE, MySqliteHelper.TABLE_ZONE
	        + " = " + id, null);
	  }

	  public List<Zone> getAllComments() {
	    List<Zone> zones = new ArrayList<Zone>();

	    Cursor cursor = database.query(MySqliteHelper.TABLE_ZONE,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    Zone comment = cursorToComment(cursor);
	      zones.add(comment);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return zones;
	  }

	  private Zone cursorToComment(Cursor cursor) {
		  Zone zone = new Zone();
//	    zone.setId(cursor.getLong(0));
//	    zone.set(cursor.getString(1));
	    return zone;
	  }
}

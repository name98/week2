package com.example.week2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.week2.items.NoteItem;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NOTES_DB";
    private static final String TABLE_NAME = "NOTES_TABLE";
    private static final String PRIMARY_KEY = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String COLOR = "color";
    private static final String SIGNIFICANCE = "significance";

    private DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" +
                PRIMARY_KEY + " integer primary key, " +
                TITLE + " varchar(255), " +
                DESCRIPTION + " text, " +
                COLOR + " varchar(30), " +
                SIGNIFICANCE + " integer" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public static void insert(Context contextForInsertNote, NoteItem note) {
        DataBaseHelper helper = new DataBaseHelper(contextForInsertNote);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put(TITLE, note.getTitle());
        insertValues.put(DESCRIPTION, note.getDescription());
        insertValues.put(COLOR, note.getColor());
        insertValues.put(SIGNIFICANCE, note.getSignificance());
        db.insert(TABLE_NAME, null, insertValues);
    }

    public static void upDate(Context contextForUpdateNote, NoteItem note) {
        DataBaseHelper helper = new DataBaseHelper(contextForUpdateNote);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put(TITLE, note.getTitle());
        updateValues.put(DESCRIPTION, note.getDescription());
        updateValues.put(COLOR, note.getColor());
        updateValues.put(SIGNIFICANCE, note.getSignificance());
        db.update(TABLE_NAME, updateValues, PRIMARY_KEY + "=" + note.getId(), null);
        db.close();
    }

    public static ArrayList<NoteItem> getNotes(Context contextForDataBase) {
        DataBaseHelper helper = new DataBaseHelper(contextForDataBase);
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<NoteItem> notes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                + TABLE_NAME + ";", null);
        if (cursor.moveToFirst()) {
            do {
                NoteItem note = new NoteItem();
                note.setId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setDescription(cursor.getString(2));
                note.setColor(cursor.getString(3));
                if (cursor.getInt(4) == 0)
                    note.setSignificance(false);
                else note.setSignificance(true);
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public static void deleteNote(Context contextForDeleteNote, NoteItem note) {
        DataBaseHelper helper = new DataBaseHelper(contextForDeleteNote);
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete(TABLE_NAME, PRIMARY_KEY + " = " + note.getId(), null);
    }

    public static ArrayList<NoteItem> getSortedNotes(Context contextForSortingNotes) {
        DataBaseHelper helper = new DataBaseHelper(contextForSortingNotes);
        ArrayList<NoteItem> significanceNotes = helper.getPinedNotes();
        ArrayList<NoteItem> unSignificanceNotes = helper.getUnPinedNotes();
        ArrayList<NoteItem> notes = new ArrayList<>();
        for (int i = significanceNotes.size() - 1; i > -1; i--) {
            notes.add(significanceNotes.get(i));
        }
        for (int i = unSignificanceNotes.size() - 1; i > -1; i--)
            notes.add(unSignificanceNotes.get(i));
        return notes;
    }

    private ArrayList<NoteItem> getPinedNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NoteItem> notes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                + TABLE_NAME + " where " + SIGNIFICANCE + "!=0" + ";", null);
        if (cursor.moveToFirst()) {
            do {
                NoteItem note = new NoteItem();
                note.setId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setDescription(cursor.getString(2));
                note.setColor(cursor.getString(3));
                note.setSignificance(true);
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    private ArrayList<NoteItem> getUnPinedNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NoteItem> notes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                + TABLE_NAME + " where " + SIGNIFICANCE + "=0" + ";", null);
        if (cursor.moveToFirst()) {
            do {
                NoteItem note = new NoteItem();
                note.setId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setDescription(cursor.getString(2));
                note.setColor(cursor.getString(3));
                note.setSignificance(false);
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public static boolean isEmpty(Context contextForDataBase) {
        return getNotes(contextForDataBase).size() == 0;
    }

    public static NoteItem getNoteById(Context contextForProvide, int noteId) {
        DataBaseHelper helper = new DataBaseHelper(contextForProvide);
        return helper.provideNoteItemById(noteId);
    }

    private NoteItem provideNoteItemById(int noteId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                + TABLE_NAME + " where " + PRIMARY_KEY
                + "=" + noteId + " ;", null);
        NoteItem note = new NoteItem();
        if (cursor.moveToFirst()) {
            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setDescription(cursor.getString(2));
            note.setColor(cursor.getString(3));
            if (cursor.getInt(4) == 0)
                note.setSignificance(false);
            else note.setSignificance(true);
        }
        cursor.close();
        db.close();
        return note;
    }

}

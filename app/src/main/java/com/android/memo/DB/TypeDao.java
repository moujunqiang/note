package com.android.memo.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.memo.Bean.NoteBean;
import com.android.memo.Bean.TypeBean;

import java.util.ArrayList;
import java.util.List;


public class TypeDao {
    Context context;
    noteTypeDBHelper dbHelper;

    public TypeDao(Context context) {
        this.context = context;
        dbHelper = new noteTypeDBHelper(context, "note_type.db", null, 1);
    }

    public void insertNote(TypeBean bean) {

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("type_tittle", bean.getTitle());
        sqLiteDatabase.insert("note_type", null, cv);
    }

    public int DeleteNote(int id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int ret = 0;
        ret = sqLiteDatabase.delete("note_type", "type_id=?", new String[]{id + ""});
        return ret;
    }

    public Cursor getAllData() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "select * from note_type";
        return sqLiteDatabase.rawQuery(sql, null);
    }

    public void updateNote(TypeBean note) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("type_tittle", note.getTitle());


        db.update("note_type", cv, "type_id=?", new String[]{note.getId() + ""});
        db.close();
    }


    public List<TypeBean> queryTypesAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        List<TypeBean> noteList = new ArrayList<>();
        TypeBean note;
        Cursor cursor = null;
        String  sql = "select * from note_type";

        cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            note = new TypeBean("");
            note.setId(cursor.getInt(cursor.getColumnIndex("type_id")));
            note.setTitle(cursor.getString(cursor.getColumnIndex("type_tittle")));
            noteList.add(note);
        }

        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }

        return noteList;
    }


    public int countType(String login_user, int mark) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select count(*) from note_data where note_owner=? and note_mark=?";
        Cursor cursor = db.rawQuery(sql, new String[]{login_user, mark + ""});
        int i = 0;
        while (cursor.moveToNext()) {
            i = cursor.getInt(0);
        }
        return i;
    }


}

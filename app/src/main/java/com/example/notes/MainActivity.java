package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    public static final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;
    private NotesDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        dbHelper = new NotesDBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        if (notes.isEmpty()) {
            notes.add(new Note("задача_1", "подзадача_1", "понедельник", 4));
            notes.add(new Note("задача_2", "подзадача_2", "вторник", 2));
            notes.add(new Note("задача_3", "подзадача_3", "среда", 3));
            notes.add(new Note("задача_4", "подзадача_4", "четверг", 2));
            notes.add(new Note("задача_5", "подзадача_5", "пятница", 7));
            notes.add(new Note("задача_6", "подзадача_6", "понедельник", 5));
            notes.add(new Note("задача_7", "подзадача_7", "суббота", 2));
            notes.add(new Note("задача_8", "подзадача_8", "понедельник", 1));
            notes.add(new Note("задача_9", "подзадача_9", "воскресенье", 6));
        }
        for (Note note : notes) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, note.getTitle());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, note.getDescription());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, note.getDayOfWeek());
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, note.getPriority());
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
        }
        ArrayList<Note> notesFromDB = new ArrayList<>();
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
           String title = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_TITLE));
           String description= cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
           String dayOfWeek = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
           int priority = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_PRIORITY));
//            String title =cursor.getString(cursor.getColumnIndex("COLUMN_TITLE"));
//            int ix0=cursor.getColumnIndex("COLUMN_TITLE");
//            String description =cursor.getString(cursor.getColumnIndex("COLUMN_DESCRIPTION"));
//            int ix1=cursor.getColumnIndex("COLUMN_TITLE");
//            String dayOfWeek =cursor.getString(cursor.getColumnIndex("COLUMN_DAY_OF_WEEK"));
//            int ix2=cursor.getColumnIndex("COLUMN_TITLE");
//            int priority=cursor.getInt(cursor.getColumnIndex("COLUMN_PRIORITY"));
//            int ix3=cursor.getColumnIndex("COLUMN_TITLE");
            Note note=new Note(title,description,dayOfWeek,priority);
            notesFromDB.add(note);
        }
        cursor.close();
        adapter = new NotesAdapter(notesFromDB);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListner(new NotesAdapter.OnNoteClickListner() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(MainActivity.this, "клик", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    private void remove(int position) {
        notes.remove(position);
        adapter.notifyDataSetChanged();
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}
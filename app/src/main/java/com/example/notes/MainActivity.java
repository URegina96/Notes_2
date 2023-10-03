package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNote;
    public static final ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNote = findViewById(R.id.recyclerViewNotes);
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
        NotesAdapter adapter = new NotesAdapter(notes);
        recyclerViewNote.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNote.setAdapter(adapter);
        adapter.setOnNoteClickListner(new NotesAdapter.OnNoteClickListner() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(MainActivity.this, "клик", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                notes.remove(position);
                adapter.notifyDataSetChanged();
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
        itemTouchHelper.attachToRecyclerView(recyclerViewNote);
    }

    private void remove(int position) {
        notes.remove(position);
//        adapter.notifyDataSetChanged(); - вот тут выдает ошибку и ломает все приложение, поэтому не могу использовать в public void onLongClick(int position) просто  remove(position) и приходится дублировать код
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}
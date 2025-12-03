package com.example.dzienniktreningowy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TrainingAdapter adapter;
    private ArrayList<Training> trainingList;
    private TrainingDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Upewnij się, że w tym XML jest RecyclerView i Button

        dbHelper = new TrainingDbHelper(this);
        trainingList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TrainingAdapter(trainingList);
        recyclerView.setAdapter(adapter);

        Button btnAdd = findViewById(R.id.buttonAddTraining);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Przejście do ekranu dodawania
                startActivity(new Intent(MainActivity.this, AddTrainingActivity.class));
            }
        });
    }

    // Wywoływane zawsze, gdy wracamy do tej aktywności
    @Override
    protected void onResume() {
        super.onResume();
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        trainingList.clear(); // Czyścimy listę, żeby nie dublować wpisów
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Pobieramy wszystkie wiersze
        Cursor cursor = db.query(TrainingDbHelper.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            // Odczytujemy dane z kolumn (indeksy kolumn)
            String name = cursor.getString(cursor.getColumnIndexOrThrow(TrainingDbHelper.COLUMN_NAME));
            int reps = cursor.getInt(cursor.getColumnIndexOrThrow(TrainingDbHelper.COLUMN_REPS));

            // Dodajemy do listy
            trainingList.add(new Training(name, reps));
        }
        cursor.close();

        // Odświeżamy widok
        adapter.notifyDataSetChanged();
    }
}
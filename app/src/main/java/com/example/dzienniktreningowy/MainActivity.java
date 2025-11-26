package com.example.dzienniktreningowy;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private EditText editName, editReps,editDuration;
    private Button buttonSave,buttonDelate;
    private TrainingDbHelper dbHelper;
    private Spinner dificultySpinner;

    private final String[] difficulty  = {"HARD", "MEDIUM", "LOW"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        dbHelper = new TrainingDbHelper(this);

        editName = findViewById(R.id.editExerciseName);
        editReps = findViewById(R.id.editRepsCount);
        editDuration = findViewById(R.id.editRepsDuration);
        dificultySpinner = findViewById(R.id.DificultySpinner);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelate = findViewById(R.id.buttonDelate);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,difficulty );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        dificultySpinner.setAdapter(adapter);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTraining();
            }
        });

        buttonDelate.setOnClickListener(v->{
            finish();
        });
    }

    private void saveTraining() {
        String name = editName.getText().toString().trim();
        String repsString = editReps.getText().toString().trim();
        String repsDuration = editDuration.getText().toString().trim();
        String difficultyString = dificultySpinner.getSelectedItem().toString();
        // Walidacja
        if (name.isEmpty() || repsString.isEmpty()) {
            Toast.makeText(this, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show();
            return;
        }

        int reps = Integer.parseInt(repsString);
        int duration = Integer.parseInt(repsDuration);
        LocalDate date = LocalDate.now();

        // Zapis do bazy
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TrainingDbHelper.COLUMN_NAME, name);
        values.put(TrainingDbHelper.COLUMN_REPS, reps);
        values.put(TrainingDbHelper.COLUMN_DURATION, duration);
        values.put(TrainingDbHelper.COLUMN_DATE, String.valueOf(date));
        values.put(TrainingDbHelper.COLUMN_DIFFICULTY, difficultyString);

        long newRowId = db.insert(TrainingDbHelper.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Błąd zapisu", Toast.LENGTH_SHORT).show();
        } else if (reps <=0 ) {
            Toast.makeText(this,"ilosc powtorzen musi byc wieksza od zera!!",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Trening zapisany", Toast.LENGTH_SHORT).show();
            finish(); // Zamyka aktywność i wraca do poprzedniej
        }
    }
}
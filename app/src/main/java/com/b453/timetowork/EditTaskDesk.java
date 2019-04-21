package com.b453.timetowork;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class EditTaskDesk extends AppCompatActivity {

    TextView date_Add;
    EditText title_Add, desc_Add;
    Button btnSaveUpdate, btnDelete;
    ImageButton btnChangeDate;
    String uid = FirebaseAuth.getInstance().getUid();
    DatabaseReference myRef;
    RadioGroup radioGroup_change;
    RadioButton radioButton;
    String keydoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_desk);


        title_Add = findViewById(R.id.title_Add);
        desc_Add = findViewById(R.id.desc_Add);
        date_Add = findViewById(R.id.date_Add);
        btnChangeDate = findViewById(R.id.btnChangeDate);
        radioGroup_change = findViewById(R.id.radioGroup_change);

        btnSaveUpdate= findViewById(R.id.btnSaveUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        //Get pass data
        title_Add.setText(getIntent().getStringExtra("titledoes"));
        desc_Add.setText(getIntent().getStringExtra("descdoes"));
        date_Add.setText(getIntent().getStringExtra("datedoes"));
        final String keykeyDoes = getIntent().getStringExtra("keydoes");

        myRef = FirebaseDatabase.getInstance().getReference().child(uid).child("todo").
                child("todo"+keykeyDoes);


        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                new DatePickerDialog(EditTaskDesk.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_Add.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedId = radioGroup_change.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                myRef.child("titledoes").setValue(title_Add.getText().toString());
                myRef.child("descdoes").setValue(desc_Add.getText().toString());
                myRef.child("datedoes").setValue(date_Add.getText().toString());
                myRef.child("pridoes").setValue(radioButton.getText().toString());
                myRef.child("keydoes").setValue(keykeyDoes);

                Intent a = new Intent(EditTaskDesk.this, todo_list.class);
                finish();
                startActivity(a);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent a = new Intent(EditTaskDesk.this, todo_list.class);
                            finish();
                            startActivity(a);
                        } else{
                            Toast.makeText(getApplicationContext(), "Can't delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

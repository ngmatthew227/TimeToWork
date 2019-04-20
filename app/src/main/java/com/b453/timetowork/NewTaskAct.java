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
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class NewTaskAct extends AppCompatActivity {

    TextView addtitle, adddesc, adddate;
    EditText title_add, desc_add, date_add;
    Button btnSaveTask, btnCancel;
    String uid = FirebaseAuth.getInstance().getUid();
    DatabaseReference myRef;
    Integer todonum = new Random().nextInt();
    int add_date, addmonth, addyear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);

        title_add = findViewById(R.id.title_add);
        desc_add = findViewById(R.id.desc_add);
        date_add = findViewById(R.id.date_add);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);

        date_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                add_date=c.get(Calendar.DAY_OF_MONTH);
                addmonth=c.get(Calendar.MONTH);
                addyear=c.get(Calendar.YEAR);
                new DatePickerDialog(NewTaskAct.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date_add.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },add_date, addmonth, addyear).show();
            }
        });

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert data to database
                myRef = FirebaseDatabase.getInstance().getReference().child(uid).child("todo").
                        child("todo"+todonum);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titledoes").setValue(title_add.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(desc_add.getText().toString());
                        dataSnapshot.getRef().child("datedoes").setValue(date_add.getText().toString());

                        Intent a = new Intent(NewTaskAct.this, todo_list.class);
                        startActivity(a);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(NewTaskAct.this, todo_list.class);
                startActivity(b);
            }
        });



    }
}

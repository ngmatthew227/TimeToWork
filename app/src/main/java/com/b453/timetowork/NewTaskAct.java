package com.b453.timetowork;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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

    TextView addtitle, adddesc, adddate, date_add, priority;
    EditText title_add, desc_add;
    Button btnSaveTask, btnCancel;
    ImageButton btnChoseDate;
    String uid = FirebaseAuth.getInstance().getUid();
    DatabaseReference myRef;
    Integer todonum = new Random().nextInt();
    RadioGroup radioGroup;
    RadioButton radioButton;
    String keydoes = Integer.toString(todonum);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);
        priority = findViewById(R.id.priority);

        title_add = findViewById(R.id.title_add);
        desc_add = findViewById(R.id.desc_add);
        date_add = findViewById(R.id.date_add);
        btnChoseDate = findViewById(R.id.btnChoseDate);
        radioGroup = findViewById(R.id.radioGroup);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnChoseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                new DatePickerDialog(NewTaskAct.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_add.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                // insert data to database
                myRef = FirebaseDatabase.getInstance().getReference().child(uid).child("todo").
                        child("todo"+todonum);
                myRef.child("titledoes").setValue(title_add.getText().toString());
                myRef.child("descdoes").setValue(desc_add.getText().toString());
                myRef.child("datedoes").setValue(date_add.getText().toString());
                myRef.child("pridoes").setValue(radioButton.getText().toString());
                myRef.child("keydoes").setValue(keydoes);

                Intent a = new Intent(NewTaskAct.this, todo_list.class);
                finish();
                startActivity(a);
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

package com.b453.timetowork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class addContact extends AppCompatActivity {

    TextView addname, addemail, addphone, addposition;
    EditText name_add, email_add, phone_add, position_add;
    Button contactsave, contactcancel;
    FirebaseUser user;
    String uid;
    DatabaseReference myRef;
    Integer todonum = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        addname = findViewById(R.id.addname);
        addemail = findViewById(R.id.addemail);
        addphone = findViewById(R.id.addphone);
        addposition = findViewById(R.id.addposition);

        name_add = findViewById(R.id.name_add);
        email_add = findViewById(R.id.email_add);
        phone_add = findViewById(R.id.phone_add);
        position_add = findViewById(R.id.position_add);

        contactsave = findViewById(R.id.contactsave);
        contactcancel = findViewById(R.id.contactcancel);

        contactsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert data to database
                final ProgressDialog mDialog = new ProgressDialog(addContact.this);
                mDialog.setMessage("Please waiting....");
                mDialog.show();
                myRef = FirebaseDatabase.getInstance().getReference().child(uid).child("department");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mDialog.dismiss();
                        dataSnapshot.getRef().child("1006").child("name").setValue(name_add.getText().toString());
                        dataSnapshot.getRef().child("1006").child("email").setValue(email_add.getText().toString());
                        dataSnapshot.getRef().child("1006").child("phone").setValue(phone_add.getText().toString());
                        dataSnapshot.getRef().child("1006").child("position").setValue(position_add.getText().toString());

                        Intent a = new Intent(addContact.this, department.class);
                        startActivity(a);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        contactcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(addContact.this, department.class);
                startActivity(b);
            }
        });
    }
}

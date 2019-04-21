package com.b453.timetowork;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class department extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView ourdepartment;
    ArrayList<String> list;
    ArrayAdapter<String> mdepartmentAdapter;

    DrawerLayout mDrawerlayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    private MenuItem menuItem;
    View addcontact;

    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer2);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_todolist);

        ourdepartment = findViewById(R.id.ourdepartment);
        list = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        addcontact = findViewById(R.id.addcontact);

        addcontact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent c = new Intent(department.this, addContact.class);
                startActivity(c);
            }
        });

        getDataFromFirebase();
    }

    private void getDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child(uid).child("department");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                for(int i = 1; i<=dataSnapshot.getChildrenCount();i++){
                    String name= "Name: " + dataSnapshot.child("100" + i).child("name").getValue(String.class);
                    String email = "E-mail: " + dataSnapshot.child("100" + i).child("email").getValue(String.class);
                    String phone = "Phone: " + dataSnapshot.child("100" + i).child("phone").getValue(String.class);
                    String position = "Position: " + dataSnapshot.child("100" + i).child("position").getValue(String.class);

                    list.add(name);
                    list.add(phone);
                    list.add(email);
                    list.add(position);
                    list.add("");
                }

                mdepartmentAdapter = new ArrayAdapter<>(department.this, android.R.layout.simple_list_item_1,list);
                ourdepartment.setAdapter(mdepartmentAdapter);
            Toast.makeText(department.this,"Data is updated!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Error", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        this.menuItem = menuItem;
        switch (menuItem.getItemId()){
            case R.id.nav_todolist:
                Intent a = new Intent(department.this, todo_list.class);
                startActivity(a);
                break;
            case R.id.nav_department:
                Intent b = new Intent(department.this, department.class);
                startActivity(b);
                break;
            case R.id.nav_logout:
                Intent c = new Intent(department.this, LoginActivity.class);
                finish();
                startActivity(c);
                break;
        }
        return true;
    }

}

package com.example.cherry.databaseex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText et,et1,et2,u,u1,s;
    DatabaseReference reference;
    RecyclerView rv;
    ArrayList<Pojo> list;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.name);
        et1 = findViewById(R.id.roll);
        et2 = findViewById(R.id.number);
        u = findViewById(R.id.uroll);
        u1 = findViewById(R.id.unumber);
        rv = findViewById(R.id.rv);
        s = findViewById(R.id.rollnumber);
        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Data");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                   Pojo pojo = dataSnapshot.getValue(Pojo.class);
                   list.add(pojo);
                   MyAdapter myAdapter = new MyAdapter(MainActivity.this,list);
                   rv.setAdapter(myAdapter);
                   rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void save(View view) {
        String name = et.getText().toString();
        String roll = et1.getText().toString();
        String number = et2.getText().toString();
        // one way is using hashmap
        String id = reference.push().getKey();
        Pojo pojo = new Pojo(name,roll,number);
        reference.child(roll).setValue(pojo);
    }

    public void update(View view) {
        String roll = u.getText().toString();
        final String num = u1.getText().toString();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Data").orderByChild("roll").equalTo(roll);
        final HashMap<String,Object> map = new HashMap<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    map.put("number",num);
                    dataSnapshot.getRef().updateChildren(map);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void search(View view) {
        String roll = s.getText().toString();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Data").orderByChild("roll").equalTo(roll);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    list.clear();
                    Pojo pojo = dataSnapshot.getValue(Pojo.class);
                    list.add(pojo);
                    MyAdapter myAdapter = new MyAdapter(MainActivity.this,list);
                    rv.setAdapter(myAdapter);
                    rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
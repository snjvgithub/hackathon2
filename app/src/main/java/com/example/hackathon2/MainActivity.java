package com.example.hackathon2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference fb= FirebaseDatabase.getInstance().getReference("agri");
    TextView f,m,mois,humi,need;
    Button mon,moff,fon,foff;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f=findViewById(R.id.f);
        m=findViewById(R.id.m);
        mois=findViewById(R.id.mois);
        humi=findViewById(R.id.humi);
        need=findViewById(R.id.need);
        mon=findViewById(R.id.button);
        moff=findViewById(R.id.button2);
        fon=findViewById(R.id.button3);
        foff=findViewById(R.id.button4);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor e=sp.edit();
        m.setText("Motor State : "+sp.getString("ms","not started"));
        f.setText("Fence State : "+sp.getString("fs","not started"));

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int ms=1;
                e.putString("ms",String.valueOf(ms));
                fb.child("motor").setValue(ms);
                Toast.makeText(getApplicationContext(),"fence turned on",Toast.LENGTH_LONG).show();
                m.setText("Motor State : "+ms);


            }
        });
        moff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ms=0;
                fb.child("motor").setValue(ms);
                e.putString("ms",String.valueOf(ms));
                Toast.makeText(getApplicationContext(),"fence turned off",Toast.LENGTH_LONG).show();
                m.setText("Motor State : "+ms);
            }
        });

        fon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fs=1;
                fb.child("fence").setValue(fs);
                e.putString("fs",String.valueOf(fs));
                Toast.makeText(getApplicationContext(),"fence turned on",Toast.LENGTH_LONG).show();
                f.setText("Fence State : "+fs);
            }
        });
        foff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fs=0;
                fb.child("fence").setValue(fs);
                e.putString("fs",String.valueOf(fs));
                Toast.makeText(getApplicationContext(),"fence turned off",Toast.LENGTH_LONG).show();
                f.setText("Fence State : "+fs);
            }
        });
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String hum= String.valueOf(snapshot.child("humi").getValue());
                mois.setText("Moisture level : "+hum);
                String moisture= String.valueOf(snapshot.child("mois").getValue());
                humi.setText("Humidity level : "+moisture);
                String i=String.valueOf(snapshot.child("need").getValue());
                need.setText("Irrigation : "+i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
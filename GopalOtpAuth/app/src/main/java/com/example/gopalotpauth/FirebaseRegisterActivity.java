package com.example.gopalotpauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FirebaseRegisterActivity extends AppCompatActivity {

    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_register);
    }

    public void register(View view)
    {

    }

    public void forgotpwd(View view)
    {
    }
}

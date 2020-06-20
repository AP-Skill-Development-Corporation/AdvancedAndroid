package com.example.firebaseemailpwdauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    EditText editemail,editpwd;
    FirebaseAuth auth;
    ProgressDialog dialog;

    EditText editsignin,editpassword;

    EditText remail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        remail=findViewById(R.id.resetemailid);

        editsignin=findViewById(R.id.et1);
        editpassword=findViewById(R.id.et2);
        dialog=new ProgressDialog(this);
        auth=FirebaseAuth.getInstance();
        editemail=findViewById(R.id.email);
        editpwd=findViewById(R.id.password);
    }

    public void registerhere(View view) {

       String e= editemail.getText().toString();
       String p= editpwd.getText().toString();
        if (e.isEmpty()||p.isEmpty())
        {
            Toast.makeText(this, "please enter the details", Toast.LENGTH_SHORT).show();
        }
        else
        {
            dialog.setTitle("Registrating the user");
            dialog.setMessage("please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
            auth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {



                    if (task.isSuccessful())
                    {
                        editemail.setText("");
                        editpwd.setText("");
                        Toast.makeText(MainActivity.this, "Registration Sucess", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();

                }
            });

        }

    }

    public void signinhere(View view)
    {
        String se=editsignin.getText().toString();
        String sp=editpassword.getText().toString();

        dialog.setTitle("Sign In the user");
        dialog.setMessage("please wait");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        auth.signInWithEmailAndPassword(se,sp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    Intent i=new Intent(getApplicationContext(),Profile.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(MainActivity.this, "invalid creadentials", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });

    }

    public void reset(View view)
    {

      String re=  remail.getText().toString();
      dialog.setTitle("Forgot pwd send email");
      dialog.setMessage("please wait");
      dialog.show();
      auth.sendPasswordResetEmail(re).addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {

              if (task.isSuccessful())
              {
                  Toast.makeText(MainActivity.this, "reset link send to email id", Toast.LENGTH_SHORT).show();
              }
              else {
                  Toast.makeText(MainActivity.this, "Invalid email id", Toast.LENGTH_SHORT).show();
              }
              dialog.dismiss();
          }
      });
    }
}
package com.example.gopalotpauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText phone,otp;
    FirebaseAuth auth;
    String vid;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        phone=findViewById(R.id.phone);
        otp=findViewById(R.id.otp);

        findViewById(R.id.sendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendotpauth();
            }

            private void sendotpauth()
            {
                String ph=phone.getText().toString();
                if (ph.isEmpty())
                {
                    phone.setError("PHONE NO IS REQUIRED");
                    phone.requestFocus();
                    return;
                }
                if (phone.length()<10)
                {
                    phone.setError("ENTER VALID PHONE NUMBER");
                    phone.requestFocus();
                    return;
                }
                PhoneAuthProvider.getInstance().verifyPhoneNumber(ph,60,
                        TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mcallback);
            }

            PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback=new
                    PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                        @Override
                        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(s, forceResendingToken);
                            vid=s;

                        }

                        @Override
                        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onVerificationFailed(FirebaseException e) {

                        }
                    };



        });




        findViewById(R.id.sendsignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signinAuth();
            }

            private void signinAuth() {

                String entercode=otp.getText().toString();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(vid,entercode);

                signincredential(credential);
            }

            private void signincredential(PhoneAuthCredential credential) {
                auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "sucess", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                Toast.makeText(MainActivity.this, "wrong otp", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                });

            }
        });
    }
}

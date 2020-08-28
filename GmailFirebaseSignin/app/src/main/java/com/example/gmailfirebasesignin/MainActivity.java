package com.example.gmailfirebasesignin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {


       //step1:connect the firebase

      //step2:add the two dependency in build.gradle(Module:app)

     // implementation 'com.google.firebase:firebase-auth:16.0.5'
     //    implementation 'com.google.android.gms:play-services-auth:18.1.0'

     // open the firebase console select firebase connect  the project open go for auth section

     // enable the gmail signin option

     // go for main.xml file and just type signinbutton give id and get the id and setonclick

   /* findViewById(R.id.b).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            signin();
        }
    });
    */

    // declare the  private FirebaseAuth auth; and get the instance oncreate method auth=FirebaseAuth.getInstance();

   // create the global variable  private static final int gsignin=20;  GoogleSignInClient mgoogle;

    //step3

    // GoogleSignInOptions go=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

    // mgoogle= GoogleSignIn.getClient(this,go);

    // step4

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == gsignin)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
            }
        }
    */

   // step5

   /* private void firebaseAuthWithGoogle(GoogleSignInAccount idToken)
    {

        AuthCredential credential= GoogleAuthProvider.getCredential(idToken.getIdToken(),null);

        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {

                if (task.isSuccessful())
                {
                    FirebaseUser user=auth.getCurrentUser();

                    // display the gmail user name

                    Toast.makeText(MainActivity.this, ""+user.getDisplayName(), Toast.LENGTH_SHORT).show();

                    // create the empty activity

                    Intent i=new Intent(getApplicationContext(),Profile.class);

                    startActivity(i);

                    finish();

                }
            }
        });
   */

    private static final int gsignin=20;

    GoogleSignInClient mgoogle;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();

        GoogleSignInOptions go=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        mgoogle= GoogleSignIn.getClient(this,go);

        findViewById(R.id.b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signin();
            }
        });


    }

    private void signin()
    {
        Intent intent=mgoogle.getSignInIntent();

        startActivityForResult(intent,gsignin);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == gsignin)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount idToken)
    {

        AuthCredential credential= GoogleAuthProvider.getCredential(idToken.getIdToken(),null);

        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {

                if (task.isSuccessful())
                {
                    FirebaseUser user=auth.getCurrentUser();

                    Toast.makeText(MainActivity.this, ""+user.getDisplayName(), Toast.LENGTH_SHORT).show();

                    Intent i=new Intent(getApplicationContext(),Profile.class);

                    startActivity(i);

                    finish();

                }
            }
        });

    }
}

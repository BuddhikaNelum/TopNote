package com.example.topnote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.login);
        btnSignUp = findViewById(R.id.signup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void login() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myview = inflater.inflate(R.layout.reg_log_input_layout, null);
        mydialog.setView(myview);

        final AlertDialog dialog = mydialog.create();

        final EditText email = myview.findViewById(R.id.email_login);
        final EditText pass = myview.findViewById(R.id.password_login);
        Button btnLogin = myview.findViewById(R.id.login_btn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail = email.getText().toString().trim();
                String mPass = pass.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)) {
                    email.setError("Email Required");
                    return;
                }
                if (TextUtils.isEmpty(mPass)) {
                    pass.setError("Password Required");
                    return;
                }

                mAuth.signInWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        dialog.show();
    }

    public void signup() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myview = inflater.inflate(R.layout.signup_layout, null);
        mydialog.setView(myview);

        final AlertDialog dialog = mydialog.create();

        final EditText email = myview.findViewById(R.id.email_login);
        final EditText pass = myview.findViewById((R.id.password_login));
        Button btnSignup = myview.findViewById(R.id.signup_btn);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail = email.getText().toString().trim();
                String mpass = pass.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)) {
                    email.setError("Email Required");
                }
                if (TextUtils.isEmpty(mpass)) {
                    pass.setError("Password Required");
                }

                mAuth.createUserWithEmailAndPassword(mEmail,mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Resgistration Complete",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            dialog.dismiss();
                        }
                    }
                });
            }
        });


        dialog.show();
    }
}

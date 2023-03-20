package com.example.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseDatabase database;
    Button btnLogin;
    TextView goToRegister;

    TextInputEditText Email,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin=findViewById(R.id.btnLogin);
        goToRegister=findViewById(R.id.goToRegister);
        Email=findViewById(R.id.lEmail);
        Password=findViewById(R.id.lPassword);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = Email.getText().toString();
                password = Password.getText().toString();
                if (email.isEmpty()) {
                    Email.setError("Please enter email");
                    return;
                }

                if (password.isEmpty()) {
                    Password.setError("Please enter password");
                    return;
                }

                auth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent=new Intent(LoginActivity.this,SafetyActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        if (auth.getCurrentUser() != null) {

            Intent intent = new Intent(LoginActivity.this, SafetyActivity.class);
            startActivity(intent);
        }

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Sign_UpActivity.class);
                startActivity(intent);
            }
        });
    }
}
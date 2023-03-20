package com.example.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safety.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_UpActivity extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseDatabase database;

    Button SignUp;
    TextView goToLogin;
    TextInputEditText name,email,userId,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        SignUp=findViewById(R.id.SignUp);
        goToLogin=findViewById(R.id.goToLogin);
        name=findViewById(R.id.Name);
        email=findViewById(R.id.Email);
        userId=findViewById(R.id.Profession);
        password=findViewById(R.id.Password);


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email, Password,Name,profession;
                Email=email.getText().toString();
                Password=password.getText().toString();
                Name=name.getText().toString();
                profession=userId.getText().toString();

                if (Email.isEmpty()){
                    email.setError("Please enter email");
                    return;
                }
                if(Password.isEmpty()){
                    password.setError("Please enter password");
                    return;
                }
                if(profession.isEmpty()){
                    userId.setError("Please enter profession");
                    return;
                }
                if(Name.isEmpty()){
                    name.setError("Please enter password");
                    return;
                }


                auth.createUserWithEmailAndPassword(Email,Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user=new User(Name,profession,Email,Password);
                                    String id=task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(user);
                                    Intent intent=new Intent(Sign_UpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(Sign_UpActivity.this, "You're account created sucesessflly", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(Sign_UpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Sign_UpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
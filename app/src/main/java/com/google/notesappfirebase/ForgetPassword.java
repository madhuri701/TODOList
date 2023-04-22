package com.google.notesappfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private EditText midforgotPassword;
    private Button midRecover;
    private TextView midgobacktologin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getSupportActionBar().hide();
        midforgotPassword = findViewById(R.id.idforgotpassword);
        midRecover = findViewById(R.id.idRecover);
        midgobacktologin = findViewById(R.id.idgobacktologin);

        firebaseAuth = FirebaseAuth.getInstance();

        midgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPassword.this, MainActivity.class);
                startActivity(intent);
            }
        });

        midRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = midforgotPassword.getText().toString().trim();
                if(mail.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter your mail first",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    //we have to send recover mail

                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Mail sent,You can recover your password using mail",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgetPassword.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Email is wrong or account doesn't exist",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

    }
}
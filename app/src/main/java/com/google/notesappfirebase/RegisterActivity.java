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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    private EditText midEmailRegister;
    private EditText midPasswordRegister;
    private Button midRegisterButton;
    private TextView midAlreadyRegister;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        midEmailRegister=findViewById(R.id.idEmailregister);
        midPasswordRegister=findViewById(R.id.idPasswordregister);
        midRegisterButton=findViewById(R.id.idRegisterButton);
        midAlreadyRegister=findViewById(R.id.idAlreadyregister);

        firebaseAuth = FirebaseAuth.getInstance();


        midRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = midEmailRegister.getText().toString().trim();
                String password = midPasswordRegister.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<7)
                {
                    Toast.makeText(getApplicationContext(),"Password Should Greater than 7",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                sendEmailVerification();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Registration failed",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        midAlreadyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //send email verification
    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"Verification Email is sent, Verify and Log in again",Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                }
            });
        }

        else
        {
            Toast.makeText(getApplicationContext(),"Failed to send verification email",Toast.LENGTH_SHORT).show();
        }
    }
}
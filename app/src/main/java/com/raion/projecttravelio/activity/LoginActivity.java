package com.raion.projecttravelio.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.raion.projecttravelio.MainActivity;
import com.raion.projecttravelio.R;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.raion.projecttravelio.model.Admin;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private TextView appName;
    private EditText edtEmail;
    private EditText edtPass;
    private Button btnLogin;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String ID_KEY = "idkey";
    private String id_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
//        checkLoggedIn();

        edtEmail = findViewById(R.id.emailL_login);
        edtPass = findViewById(R.id.pass_login);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSignUpActivity = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(goToSignUpActivity);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setEnabled(false);
                btnSignUp.setText("Loading...");

                final String email = edtEmail.getText().toString();
                final String password = edtPass.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Kosong!", Toast.LENGTH_SHORT).show();
                    //mengubah state menjadi loading
                    btnLogin.setEnabled(true);
                    btnSignUp.setText("SIGN UP");
                } else {
                    if(password.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Password Kosong!", Toast.LENGTH_SHORT).show();
                        //Mengubah state menjadi loading
                        btnLogin.setEnabled(true);
                    } else {
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                            goToHome();
                                        }
                                        else {
                                            //login failed
                                            Toast.makeText(LoginActivity.this, "Email atau Password Salah!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }

    public void signUpActivity(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    private void goToHome() {
        Intent gotohome = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(gotohome);
    }

    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
        mAuth = FirebaseAuth.getInstance();
    }


//    private void checkLoggedIn() {
//        if (mAuth.getCurrentUser() != null) {
//            goToHome();
//        }
//    }

}

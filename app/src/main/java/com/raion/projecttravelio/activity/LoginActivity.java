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

                if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email Kosong!", Toast.LENGTH_SHORT).show();
                    //mengubah state menjadi loading
                    btnLogin.setEnabled(true);
                    btnSignUp.setText("SIGN IN");
                } else {
                    if(password.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Password Kosong!", Toast.LENGTH_SHORT).show();
                        //Mengubah state menjadi loading
                        btnLogin.setEnabled(true);
                        btnLogin.setText("SIGN IN");
                    } else {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("email").child(email);
                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    //ambil data password dari database
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    //validasi password dari firebase
                                    if (password.equals(passwordFromFirebase)){
                                        //berpindah activity
                                        Intent gotohome = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(gotohome);
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Password Salah!", Toast.LENGTH_SHORT).show();
                                        //mengubah state menjadi loading
                                        btnLogin.setEnabled(true);
                                        btnLogin.setText("SIGN IN");
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(),"Email tidak ada!",Toast.LENGTH_SHORT).show();
                                    //Mengubah state menjadi loading
                                    btnLogin.setEnabled(true);
                                    btnLogin.setText("SIGN IN");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),"Database Error",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        btnLogin = findViewById(R.id.btn_login);
//        btnSignUp = findViewById(R.id.signup);
//        edtEmail = findViewById(R.id.emailL_login);
//        edtPass = findViewById(R.id.pass_login);
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mAuth = FirebaseAuth.getInstance();
//        btnLogin.setOnClickListener(this);
//        btnSignUp.setOnClickListener(this);
//    }
//
    public void signUpActivity(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
//
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.btn_login) {
//            signIn();
//        }else if (i == R.id.signup){
//            Intent goSignUp = new Intent(LoginActivity.this, SignupActivity.class);
//            startActivity(goSignUp);
//        }
//    }
//
//    private void signIn() {
//        Log.d(TAG, "signIn");
//        if (!validateForm()) {
//            return;
//        }
//
//        //showProgressDialog();
//        String email = edtEmail.getText().toString();
//        String password = edtPass.getText().toString();
//
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
//                        //hideProgressDialog();
//
//                        if (task.isSuccessful()) {
//                            onAuthSuccess(task.getResult().getUser());
//                        }
//                    }
//                });
//    }
//
//    private boolean validateForm(){
//        boolean result = true;
//        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
//            edtEmail.setError("Email Required");
//            result = false;
//        } else {
//            edtEmail.setError(null);
//        }
//
//        if (TextUtils.isEmpty(edtPass.getText().toString())) {
//            edtPass.setError("Password Required");
//            result = false;
//        } else {
//            edtPass.setError(null);
//        }
//        return result;
//    }
//
//    private void onAuthSuccess(FirebaseUser user) {
//        String email = emailFromE mail(user.getEmail());
//
//        // Go to MainActivity
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//        finish();
//    }
//
//    private String emailFromEmail(String email) {
//        if (email.contains("@")) {
//            return email.split("@")[0];
//        } else {
//            return email;
//        }
//    }

}

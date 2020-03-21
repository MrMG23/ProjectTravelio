package com.raion.projecttravelio.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raion.projecttravelio.MainActivity;
import com.raion.projecttravelio.R;
import com.raion.projecttravelio.model.Admin;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
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

        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.signup);
        edtEmail = findViewById(R.id.emailL_login);
        edtPass = findViewById(R.id.pass_login);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    public void signUpActivity(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_login) {
            signIn();
        }else if (i == R.id.signup){
            Intent goSignUp = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(goSignUp);
        }
    }

    private void signIn(){
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(LoginActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm(){
        boolean result = true;
        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError("Email Required");
            result = false;
        } else {
            edtEmail.setError(null);
        }

        if (TextUtils.isEmpty(edtPass.getText().toString())) {
            edtPass.setError("Password Required");
            result = false;
        } else {
            edtPass.setError(null);
        }
        return result;
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        // membuat User admin baru
        writeNewAdmin(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    // menulis ke Database
    private void writeNewAdmin(String userId, String name, String email) {
        Admin admin = new Admin(name, email);

        mDatabase.child("Admins").child(userId).setValue(admin);
    }

//    if (password.equals(passwordFromFirebase)) {
//        //Simpan username (key) kepada local
//        SharedPreferences sharedPreferences = getSharedPreferences(DRIVERID_KEY, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(driverid_key, String.valueOf(i));
//        editor.apply();
}

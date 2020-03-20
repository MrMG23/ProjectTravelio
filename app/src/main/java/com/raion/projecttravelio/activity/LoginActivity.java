package com.raion.projecttravelio.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raion.projecttravelio.MainActivity;
import com.raion.projecttravelio.R;



public class LoginActivity extends AppCompatActivity {
    private TextView appName;
    private EditText email;
    private EditText password;
    private Button btnLogin;
    private DatabaseReference databaseReference;
    private String ID_KEY = "idkey";
    private String id_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_login);
        email = findViewById(R.id.emailL_login);
        password = findViewById(R.id.pass_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fEmail = email.getText().toString();
                final String fPaassword = password.getText().toString();
                if(fEmail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email Kosong!", Toast.LENGTH_SHORT).show();
                }else{
                    if (fPaassword.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password Anda Kosong!", Toast.LENGTH_SHORT).show();
                    }else{
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Akun");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (int i = 1; i<= dataSnapshot.getChildrenCount(); i++){
                                    if (dataSnapshot.child(String.valueOf(i)).child("email").getValue().toString().equals(fEmail)) {
                                        String passwordFromFirebase = dataSnapshot.child(String.valueOf(i)).child("pass").getValue().toString();
                                        if (fPaassword.equals(passwordFromFirebase)) {
                                            //Simpan username (key) kepada local
                                            SharedPreferences sharedPreferences = getSharedPreferences(ID_KEY, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(id_key, String.valueOf(i));
                                            editor.apply();

                                            Intent goToHome = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(goToHome);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Password salah!", Toast.LENGTH_SHORT).show();
                                        }
                                    }else
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

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

//    if (password.equals(passwordFromFirebase)) {
//        //Simpan username (key) kepada local
//        SharedPreferences sharedPreferences = getSharedPreferences(DRIVERID_KEY, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(driverid_key, String.valueOf(i));
//        editor.apply();
}

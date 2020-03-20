package com.raion.projecttravelio.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.raion.projecttravelio.R;

public class LoginActivity extends AppCompatActivity {
    private TextView appName;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    /*    appName = findViewById(R.id.app_name);
        email = findViewById(R.id.emailL_login);
        password = findViewById(R.id.pass_login);*/
    }

    public void signUpActivity(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}

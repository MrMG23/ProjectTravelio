package com.raion.projecttravelio.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.raion.projecttravelio.R;

public class LoginActivity extends AppCompatActivity {
    private TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appName = findViewById(R.id.app_name);

    }
}

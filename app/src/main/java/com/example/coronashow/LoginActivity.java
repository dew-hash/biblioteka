 package com.example.coronashow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

 public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();
                if (Validation.isUsernameValid(usernameStr) && Validation.isPasswordValid(passwordStr)) {
                    //Toast.makeText(LoginActivity.this, "Prisijungimo vardas: " + usernameStr + "\nSlaptažodis: " + passwordStr, Toast.LENGTH_LONG).show();
                    Intent goToSearchActivity = new Intent(LoginActivity.this, SearchActivity.class); //parametrai: iš kur (visad su this, nes šita klasė), į kur (visad su class)
                    startActivity(goToSearchActivity);
                }
                else {    //nereikia skliaustelių salygos nurodymui, nes bus vykdoma visais kitais atvejais
                    username.setError(getResources().getString(R.string.login_invalid_credentials));
                    username.requestFocus();
                }
            }
        });

    }
}
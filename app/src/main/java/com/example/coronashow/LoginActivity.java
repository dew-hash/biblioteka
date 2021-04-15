 package com.example.coronashow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

 public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button register = findViewById(R.id.register);
        CheckBox remember = findViewById(R.id.remember);
        remember.setChecked(User.SharedPreferencesUse.getRememberMe(this));
        if(remember.isChecked()) {
            username.setText(User.SharedPreferencesUse.getUsername(), TextView.BufferType.EDITABLE);
            password.setText(User.SharedPreferencesUse.getPassword(), TextView.BufferType.EDITABLE);
        } else {
            username.setText("", TextView.BufferType.EDITABLE);
            password.setText("", TextView.BufferType.EDITABLE);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();
                username.setError(null);
                password.setError(null);
                if (Validation.isUsernameValid(usernameStr) && Validation.isPasswordValid(passwordStr)) {
                    //Toast.makeText(LoginActivity.this, "Prisijungimo vardas: " + usernameStr + "\nSlaptažodis: " + passwordStr, Toast.LENGTH_LONG).show();
                    User user = new User(usernameStr, passwordStr);
                    if(remember.isChecked()) {
                        User.SharedPreferencesUse.setRememberMe(true);
                    }
                    else {
                        User.SharedPreferencesUse.setRememberMe(false);
                    }
                    User.SharedPreferencesUse.setUsername(usernameStr);
                    User.SharedPreferencesUse.setPassword(passwordStr);
                    Intent goToSearchActivity = new Intent(LoginActivity.this, SearchActivity.class); //parametrai: iš kur (su this), į kur (su class)
                    startActivity(goToSearchActivity);
                }
                else {
                    username.setError(getResources().getString(R.string.login_invalid_credentials));
                    username.requestFocus();
                }
            } //onClick viduje login.setOnClickListener
        });  //login.setOnClickListener

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class); //parametrai: iš kur (visad su this, nes šita klasė), į kur (visad su class)
                startActivity(goToRegisterActivity);
            } //onClick viduje register.setOnClickListener
        }); //register.setOnClickListener

    } //onCreate()
}  //class loginActivity
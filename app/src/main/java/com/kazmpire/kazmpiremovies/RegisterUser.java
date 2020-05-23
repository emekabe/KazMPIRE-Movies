package com.kazmpire.kazmpiremovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterUser extends AppCompatActivity {

    EditText firstNameField;
    EditText lastNameField;
    EditText usernameField;
    EditText passwordField;
    EditText reenterPasswordField;

    Button submitButton;

//    FirebaseDatabase database;
//    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_user);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        firstNameField = findViewById(R.id.first_name_field);
        lastNameField = findViewById(R.id.last_name_field);
        usernameField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);
        reenterPasswordField = findViewById(R.id.reenter_password_field);

        submitButton = findViewById(R.id.submit_button);




        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    String firstName = firstNameField.getText().toString().trim();
                    String lastName = lastNameField.getText().toString().trim();
                    String userName = usernameField.getText().toString().trim();
                    String password = passwordField.getText().toString();
                    String reenterPassword = reenterPasswordField.getText().toString();

                    DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("users");

                    if (!(firstName.isEmpty() || userName.isEmpty() || password.isEmpty() || reenterPassword.isEmpty())){
                        if (passWordValidator(password, reenterPassword)){
                            User newUser = new User(user.getUid(), firstName, lastName, userName, password);
                            dbReference.child(user.getUid()).setValue(newUser);
                            startActivity(new Intent(RegisterUser.this, FactsActivity.class));
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(RegisterUser.this, "Please fill out all the fields", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }

    private boolean passWordValidator(String pass1, String pass2){

        if (!pass1.equals(pass2)){
            Toast.makeText(RegisterUser.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pass1.length() < 8){
            Toast.makeText(RegisterUser.this, "Password too short", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}

package com.kazmpire.kazmpiremovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class RegisterUser extends AppCompatActivity {

    EditText firstNameField;
    EditText lastNameField;
    EditText usernameField;
    EditText passwordField;
    EditText reenterPasswordField;

    Button submitButton;

    ProgressBar progressBar;

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

        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.GONE);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    final String firstName = firstNameField.getText().toString().trim();
                    final String lastName = lastNameField.getText().toString().trim();
                    final String userName = usernameField.getText().toString().trim();
                    final String password = passwordField.getText().toString();
                    String reenterPassword = reenterPasswordField.getText().toString();

                    final DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("users");

                    if (!(firstName.isEmpty() || userName.isEmpty() || password.isEmpty() || reenterPassword.isEmpty())){
                        if (usernameValidator(userName) && passWordValidator(password, reenterPassword)){

                            Query q = dbReference.orderByChild("username").equalTo(userName);

                            q.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    // TODO 1: Toast this later
//                                    dataSnapshot.getChildren().iterator().next().getValue(User.class).getUsername().toLowerCase();

                                    if (dataSnapshot.exists()){
                                        Toast.makeText(RegisterUser.this, "Username already taken", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        User newUser = new User(user.getUid(), firstName, lastName, userName, password);
                                        dbReference.child(user.getUid()).setValue(newUser);
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(RegisterUser.this, FactsActivity.class));
                                        finish();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
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

    private boolean usernameValidator(final String username){

        if (username.contains(" ")){
            Toast.makeText(RegisterUser.this, "Can't have spaces in username", Toast.LENGTH_SHORT).show();
            return false;
        }

        final Boolean[] validate = {true};

//        Query un = FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(username);
//
//        un.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds: dataSnapshot.getChildren()) {
//                    if (username.toLowerCase().equals(Objects.requireNonNull(ds.getValue(User.class)).getUsername().toLowerCase())){
//                        //Wowwwww.. Let's hope this works
//                        validate[0] = false;
//                        Toast.makeText(RegisterUser.this, "Username already taken", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        return validate[0];
    }
}

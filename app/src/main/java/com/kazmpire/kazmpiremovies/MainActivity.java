package com.kazmpire.kazmpiremovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            openAnotherActivity();
        }
        else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(
                                    Arrays.asList( new AuthUI.IdpConfig.PhoneBuilder().build())
                            )
                            .setTheme(R.style.LoginTheme)
                            .setLogo(R.drawable.login_logo)
                            .build(),
                    RC_SIGN_IN );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if (user != null) Toast.makeText(this, "Signed in as " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                if (user != null) Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
                openAnotherActivity();
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                if (response != null) Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void openAnotherActivity(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //code to check if firebase signed in user has registered
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dbReference = database.getReference("users");

        dbReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                try {
                    assert user1 != null;
                    Toast.makeText(MainActivity.this, "Welcome " + user1.getFirstName(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, FactsActivity.class));
                    finish();
                } catch (NullPointerException e){
                    startActivity(new Intent(MainActivity.this, RegisterUser.class));
                    finish();
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Another error occurred.\nPlease register.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, RegisterUser.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

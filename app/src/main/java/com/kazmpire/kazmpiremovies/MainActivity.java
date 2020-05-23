package com.kazmpire.kazmpiremovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            Intent openFacts = new Intent(this, FactsActivity.class);
            startActivity(openFacts);
            finish();
        }
        else {
            startActivityForResult(
                    AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(
                            Arrays.asList( new AuthUI.IdpConfig.PhoneBuilder().build())
                    )
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
                Intent openFacts = new Intent(this, FactsActivity.class);
                startActivity(openFacts);
                finish();
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                if (response != null) Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}

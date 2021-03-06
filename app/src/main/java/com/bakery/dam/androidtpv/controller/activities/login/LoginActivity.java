package com.bakery.dam.androidtpv.controller.activities.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.activities.main.*;
import com.bakery.dam.androidtpv.controller.managers.LoginCallback;
import com.bakery.dam.androidtpv.controller.managers.UserLoginManager;
import com.bakery.dam.androidtpv.model.UserToken;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginCallback {

    // Elementos de la UI
    private EditText Username;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linearLayout= (LinearLayout) findViewById(R.id.login_progress_ll);
        // Set up the login form.
        Username = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL || (keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (id == EditorInfo.IME_ACTION_DONE)) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });



        Button mEmailSignInButton = (Button) findViewById(R.id.user_log_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        //TODO REGISTRO

//        TextView mRegisterButton = (TextView) findViewById(R.id.registrer_button);
//        mRegisterButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), SignUp.class);
//                startActivity(i);
//            }
//        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }


    /**
     * Attempts to log in the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        Username.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = Username.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            Username.setError(getString(R.string.error_field_required));
            focusView = Username;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            UserLoginManager.getInstance().performLogin(username, password, LoginActivity.this);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void onSuccess(UserToken userToken) {
        showProgress(false);
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e("LoginActivity->", "performLogin->onFailure ERROR " + t.getMessage());

        // TODO: Gestionar los diversos tipos de errores. Por ejemplo, no se ha podido conectar correctamente.
        showProgress(false);
        mPasswordView.setError(getString(R.string.error_incorrect_username_or_password));
        mPasswordView.requestFocus();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            linearLayout.setVisibility(show ? View.VISIBLE : View.GONE);
            linearLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    linearLayout.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            linearLayout.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
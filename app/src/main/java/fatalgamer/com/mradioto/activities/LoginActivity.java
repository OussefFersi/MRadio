package fatalgamer.com.mradioto.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.io.IOException;

import fatalgamer.com.mradioto.R;
import io.fabric.sdk.android.Fabric;


public class LoginActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    private MediaPlayer mp;
    private final static String TAG4 = "Activity_LOG";
    private static final String TAG = "googleLogin";
    private static final String TAG2 = "facebookLogin";
    private static final String TAG3 = "firebaseLogin";
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "YNzfAiNHTeT1PsTjwWM3SPBpI";
    private static final String TWITTER_SECRET = "khDdKUlBkfockXBUf4YfLX93fRH2jKVaQclYyRWne1n63qRAiJ";
    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListenner;

           /* google */
    private SignInButton mGoogleBtn;
    private static  final int RC_SIGN_IN =1;
    private GoogleApiClient mGoogleApiClient;
    /*...........*/

    /*facebook*/
    private CallbackManager mCallbackManager;
    /*.......*/

    /*twitter*/
    private TwitterLoginButton  loginButtonT;
    /*....*/
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*twitterInT*/
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig));
        //
       // FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.login);

        //
        Log.d(TAG, "onCreate");

        ((SurfaceView) findViewById(R.id.surfaceView)).getHolder().addCallback(this);
        //
        mCallbackManager = CallbackManager.Factory.create();
        mGoogleBtn = (SignInButton) findViewById(R.id.googleConnection);


        mAuth = FirebaseAuth.getInstance();
        mAuthListenner = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!= null){

                    startActivity(new Intent(LoginActivity.this,listcountryActivity.class));

                }
            }
        };
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this,new GoogleApiClient.OnConnectionFailedListener(){

                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        Toast.makeText(LoginActivity.this, "you got error", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        /*end*/

        /*facebook*/
// Initialize Facebook Login button

       // LoginButton loginButton = (LoginButton) findViewById(R.id.fbConnection);

       // loginButton.setReadPermissions("email");
       // loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
          //  @Override
          //  public void onSuccess(LoginResult loginResult) {
          //      Log.d(TAG, "facebook:onSuccess:" + loginResult);
          //      handleFacebookAccessToken(loginResult.getAccessToken());
          //  }

         //   @Override
          //  public void onCancel() {
           //     Log.d(TAG, "facebook:onCancel");
                // ...
           // }

         //   @Override
         //   public void onError(FacebookException error) {
          //      Log.d(TAG, "facebook:onError", error);
                // ...
         //   }
      //  });

        /*end*/


        /*Twitter*/
        loginButtonT = (TwitterLoginButton) findViewById(R.id.twitterConnection);

        loginButtonT.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                handleTwitterSession(session);
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        /*end*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListenner);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListenner != null) {
            mAuth.removeAuthStateListener(mAuthListenner);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //facebookpart
      //  mCallbackManager.onActivityResult(requestCode, resultCode, data);
        loginButtonT.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account){

        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });

    }

/*
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG2, "handleFacebookAccessToken:" + token);


        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG2, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG2, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }*/


    private void handleTwitterSession(TwitterSession session) {
        Log.d(TAG3, "handleTwitterSession:" + session);

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG3, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG3, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
//for the animated background
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        try {
            initPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPlayer() throws IOException {
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"+R.raw.timelapsecity);
        mp = new MediaPlayer();
        mp.setDataSource(this, video);
        mp.setDisplay(((SurfaceView) findViewById(R.id.surfaceView)).getHolder());
        mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        mp.prepare();
        mp.setLooping(true);
        mp.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        mp.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp!=null){
            mp.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp!=null){
            mp.stop();
        }
    }
}

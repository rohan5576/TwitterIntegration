package com.example.admin.twitterintegration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "iJ1KG8DsHTxCEaEIfGTjnB3Qt";
    private static final String TWITTER_SECRET = "RV21vBg366TTvqzUN03LpY0NM2F1CNJHQS8TDrKHIPzSKenWj9";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PROFILE_IMAGE_URL = "image_url";

    TwitterLoginButton twitterLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

       twitterLoginButton= (TwitterLoginButton) findViewById(R.id.twitterLogin);


        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                TwitterSession session = result.data;

                Call<User> userResult = Twitter.getApiClient(session).getAccountService().verifyCredentials(true,false);
                userResult.enqueue(new Callback<User>() {

                    @Override
                    public void failure(TwitterException e) {

                    }

                    @Override
                    public void success(Result<User> userResult) {

                        User user = userResult.data;


                        try {
                            Log.d("imageurl", user.profileImageUrl);
                            Log.d("name", user.name);
                            Log.d("email",user.email);
                            Log.d("des", user.description);
                            Log.d("followers ", String.valueOf(user.followersCount));
                            Log.d("createdAt", user.createdAt);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //Getting the profile image url


                        //Creating an Intent
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);

                        //Adding the values to intent
                        intent.putExtra(KEY_USERNAME, user.name);
                        intent.putExtra(KEY_PROFILE_IMAGE_URL,  user.profileImageUrl);
                        startActivity(intent);

                    }

                });

            }

            @Override
            public void failure(TwitterException exception) {

                Log.d("Twitter", "Failure", exception);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Adding the login result back to the button
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }





}

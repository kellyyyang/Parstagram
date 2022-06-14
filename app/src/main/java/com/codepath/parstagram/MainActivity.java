package com.codepath.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    EditText etDescription;
    Button btnPicture;
    Button btnPost;
    ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDescription = findViewById(R.id.etDescription);
        btnPicture = findViewById(R.id.btnPicture);
        btnPost = findViewById(R.id.btnPost);
        ivPhoto = findViewById(R.id.ivPhoto);

        queryPosts();

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postDescription = etDescription.getText().toString();
                if (postDescription.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Sorry, your description cannot be empty.", Toast.LENGTH_LONG).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(postDescription, currentUser);
            }
        });
    }

    private void savePost(String postDescription, ParseUser currentUser) {
        Post post = new Post();
        post.setDescription(postDescription);
        post.setUser(currentUser);

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with saving post");
                    Toast.makeText(MainActivity.this, "Issue with saving post!", Toast.LENGTH_SHORT);
                } else {
                    Log.i(TAG, "Post has been saved");
                    Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT);
                    post.setDescription("");
                }
                return;
            }
        });
    }

    // get the Posts in Parse.
    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts ", e);
                    return;
                } for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", user: " + post.getUser().getUsername());
                }
            }
        });
    }

    public void onLogoutButton(View view) {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login ", e);
                    Toast.makeText(MainActivity.this, "Issue with logout!", Toast.LENGTH_SHORT);
                    return;
                } else {
                    goLoginActivity();
                    Log.i(TAG, "Going to login activity.");
                    Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
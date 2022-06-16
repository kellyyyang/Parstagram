package com.codepath.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;

import org.parceler.Parcels;
import org.w3c.dom.Text;

public class PostDetails extends AppCompatActivity {

    public static final String TAG = "PostDetails";

    Post post;
    TextView tvUsernameDetails;
    ImageView ivImageDetails;
    TextView tvDescriptionDetails;
    TextView tvTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvUsernameDetails = findViewById(R.id.tvUsernameDetails);
        ivImageDetails = findViewById(R.id.ivImageDetails);
        tvDescriptionDetails = findViewById(R.id.tvDescriptionDetails);
        tvTimeStamp = findViewById(R.id.tvTimeStamp);

        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        Log.d(TAG, "Showing details for " + post.getUser().getUsername() + post.getDescription());
        tvUsernameDetails.setText(post.getUser().getUsername());
        String username = post.getUser().getUsername();
        tvDescriptionDetails.setText(String.format("%s%s", Html.fromHtml("<b>" + username + "</b> "), post.getDescription()));
        try {
            String filePath = post.getImage().getFile().getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            ivImageDetails.setImageBitmap(bitmap);
        } catch (ParseException e) {
            Log.e(TAG, "unable to parse image " + e);
//            e.printStackTrace();
        }

    }
}
package com.codepath.parstagram;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvUsernameBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUsernameBody = itemView.findViewById(R.id.tvUsernameBody);

            // add this as the itemView's OnClickListener
            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {
            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvUsernameBody.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
        }

        @Override
        public void onClick(View v) {
            // get position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. it actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                Post post = posts.get(position);
                Intent intent = new Intent(context, PostDetails.class);
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                context.startActivity(intent);
            }
        }

    }
}
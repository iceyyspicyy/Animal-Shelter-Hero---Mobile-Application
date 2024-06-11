package com.bloodbank.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bloodbank.bookexchange.Adapter.AchievementAdapter;
import com.bloodbank.bookexchange.Model.Post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AchievementActivity extends AppCompatActivity {


    private RecyclerView achRecyclerView;
    private DatabaseReference achRef;
    private LinearLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        achRef = FirebaseDatabase.getInstance().getReference().child("Achievement");
        achRecyclerView = findViewById(R.id.achievementRecyclerView);
        achRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        achRecyclerView.setLayoutManager(linearLayoutManager);
        
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new
//                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
//        cropsRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        progressBar = findViewById(R.id.ach_loading);
        progressBar.setVisibility(View.VISIBLE);
        
        posts();
    }

    private void posts() {
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(achRef, Post.class)
                .build();
        FirebaseRecyclerAdapter<Post, AchievementAdapter> cropAdapter = new FirebaseRecyclerAdapter<Post,
                AchievementAdapter>(options) {
            @NonNull
            @Override
            public AchievementAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent,
                        false);
                AchievementAdapter holder = new AchievementAdapter(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull AchievementAdapter holder, final int position,
                                            @NonNull final Post model) {

                progressBar.setVisibility(View.GONE);
                holder.postT.setText(model.getRescueBy());
                holder.postDesc.setText(model.getDescription());
                Picasso.get().load(model.getImage()).into(holder.image);
                //Glide.with(HomeActivity.this).load(model.getImage()).into(holder.image);
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(CropsActivity.this, CropsDetailsActivity.class);
//                        intent.putExtra("cId", model.getcId());
//                        startActivity(intent);
//
//                    }
//                });
            }
        };

        achRecyclerView.setAdapter(cropAdapter);
        cropAdapter.startListening();
    }
}
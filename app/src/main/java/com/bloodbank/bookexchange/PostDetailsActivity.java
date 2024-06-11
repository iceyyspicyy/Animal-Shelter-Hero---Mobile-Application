package com.bloodbank.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bloodbank.bookexchange.Model.User;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PostDetailsActivity extends AppCompatActivity {

    private ImageView postImage, profileImage;
    private TextView username, date, time, name, description, location;
    private Button requestBtn;

    String userID="";
    String bookName="";
    String userName="";
    String postTime="";
    String postDate="";
    String postDescription="";
    String postLocation="";
    String image="";
    String profileIM="";

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        postImage = findViewById(R.id.post_image);
        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.userName);
        date = findViewById(R.id.dateTV);
        time = findViewById(R.id.timeTV);
        name = findViewById(R.id.nameTV);
        description = findViewById(R.id.descriptionTV);
        location = findViewById(R.id.locationTV);
        requestBtn = findViewById(R.id.requestBTN);

        intent = getIntent ();
        userID = intent.getStringExtra ( "userID" );
//        bookName = intent.getStringExtra ( "bookName" );
//        userName = intent.getStringExtra ( "userName" );
//        postTime = intent.getStringExtra ( "time" );
//        postDate = intent.getStringExtra ( "date" );
//        postDescription = intent.getStringExtra ( "description" );
//        postLocation = intent.getStringExtra ( "location" );


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseUser = FirebaseAuth.getInstance ().getCurrentUser ();


        reference = FirebaseDatabase.getInstance ().getReference ("Users").child (userID);

        reference.addValueEventListener ( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue ( User.class );
//                username.setText ( Objects.requireNonNull ( user ).getUsername () );
//
//                if(user.getImageURL ().equals ( "default" ))
//                {
//                    profileImage.setImageResource ( R.drawable.userphoto );
//                }
//                else
//                {
//                    Glide.with ( getApplicationContext () ).load ( user.getImageURL () ).placeholder ( R.drawable.new_loader ).into ( profileImage );
//                }
                User user = snapshot.getValue(User.class);

                if (user != null) {
                    // Accessing username only if user is not null
                    String userUsername = user.getUsername();
                    if (userUsername != null) {
                        username.setText(userUsername);
                    }

                    String imageURL = user.getImageURL();
                    if ("default".equals(imageURL)) {
                        profileImage.setImageResource(R.drawable.userphoto);
                    } else if (imageURL != null) {
                        // Load the image using Glide only if imageURL is not null
                        Glide.with(getApplicationContext()).load(imageURL).placeholder(R.drawable.new_loader).into(profileImage);
                    }
                } else {
                    // Handle the case where user is null
                    Log.e("DataSnapshotError", "User data is null");
                    // You might want to throw an exception, log an error, or handle it in another way
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );

        image = getIntent().getExtras().getString("postImage") ;
        Glide.with(this).load(image).into(postImage);

        profileIM = getIntent().getExtras().getString("profileImage") ;
        //Glide.with(this).load(profileIM).into(profileImage);

        if (profileIM != null){
            Glide.with(this).load(profileIM).into(profileImage);

        }else{
            Glide.with(this).load(R.drawable.userphoto).into(profileImage);

        }


        userName = getIntent().getExtras().getString("username");
        username.setText(userName);

        bookName = getIntent().getExtras().getString("bookName");
        name.setText(bookName);

        postTime = getIntent().getExtras().getString("time");
        time.setText(postTime);

        postDate = getIntent().getExtras().getString("date");
        date.setText(postDate);

        postDescription = getIntent().getExtras().getString("description");
        description.setText(postDescription);

        postLocation = getIntent().getExtras().getString("location");
        location.setText(postLocation);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent ( PostDetailsActivity.this, MainActivity.class );

                startActivity ( intent );
            }
        });

    }

    public void backtomainscreen(View view) {
        startActivity(new Intent(PostDetailsActivity.this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
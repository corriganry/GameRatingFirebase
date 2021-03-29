package edu.lewisu.cs.corriganry.gameratingfirebase;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {

    private GameRating gameRating;
    private EditText gameNameField;
    private EditText developerNameField;
    private RatingBar ratingBar;
    private Button addEditButton;
    private String userId;
    private String ref;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        gameNameField = findViewById(R.id.gameNameEditText);
        gameNameField.addTextChangedListener(new GameNameListener());

        developerNameField = findViewById(R.id.developerNameEditText);
        developerNameField.addTextChangedListener(new DeveloperNameListener());

        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingChangedListener());

        addEditButton = findViewById(R.id.add_edit_button);

        userId = getIntent().getStringExtra("uid");

        gameRating = new GameRating(userId);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        ref = getIntent().getStringExtra("ref");

        if (ref != null) {
            mDatabaseReference = mFirebaseDatabase.getReference().child("game_rating").child(ref);

            ValueEventListener gameRatingListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    gameRating = snapshot.getValue(GameRating.class);
                    setUi();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            mDatabaseReference.addValueEventListener(gameRatingListener);


        } else {
            addEditButton.setOnClickListener(new OnAddButtonClick());
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mFirebaseDatabase.getReference("game_rating");
        }


    }

    private void setUi() {
        if(gameRating != null) {
            gameNameField.setText(gameRating.getGameName());
            developerNameField.setText(gameRating.getDeveloperName());
            gameRating.setRating(gameRating.getRating());
            ratingBar.setRating(gameRating.getRating());
            addEditButton.setText(R.string.update);
            addEditButton.setOnClickListener(new OnUpdateButtonClick());
        }
    }

    private class GameNameListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            gameRating.setGameName(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class DeveloperNameListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            gameRating.setDeveloperName(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class RatingChangedListener implements RatingBar.OnRatingBarChangeListener{
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            gameRating.setRating((int)v);
        }
    }

    private class OnAddButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            mDatabaseReference.push().setValue(gameRating);
            finish();
        }
    }

    private class OnUpdateButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            mDatabaseReference.setValue(gameRating);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.delete) {
            mDatabaseReference.removeValue();
            finish();
        }else{
            return super.onOptionsItemSelected(item);
        }
        return true;

    }
}

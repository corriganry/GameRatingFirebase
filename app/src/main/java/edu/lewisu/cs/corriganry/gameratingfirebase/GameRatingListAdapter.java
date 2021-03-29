package edu.lewisu.cs.corriganry.gameratingfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class GameRatingListAdapter extends FirebaseRecyclerAdapter<GameRating, GameRatingListAdapter.GameRatingHolder> {
    private final GameratingListAdapterOnClickHandler clickHandler;


    public interface GameratingListAdapterOnClickHandler {
        void onClick(int position);
    }

    public GameRatingListAdapter(@NonNull FirebaseRecyclerOptions<GameRating> options, GameratingListAdapterOnClickHandler clickHandler) {
        super(options);
        this.clickHandler = clickHandler;
    }

    @Override
    protected void onBindViewHolder(@NonNull GameRatingHolder holder, int position, @NonNull GameRating model) {
        holder.titleTextView.setText(model.getGameName());
    }

    @NonNull
    @Override
    public GameRatingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new GameRatingHolder(view);
    }

    class GameRatingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView titleTextView;

        GameRatingHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            clickHandler.onClick(adapterPosition);
        }
    }
}

package com.example.flixster_metau.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.Target;
import com.example.flixster_metau.DetailActivity;
import com.example.flixster_metau.R;
import com.example.flixster_metau.modules.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false );
        return new ViewHolder(movieView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        Movie movie = movies.get(position);
        
        holder.bind(movie);


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
               tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);



        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            int radius = 25; // corner radius, higher value = more rounded
//            int margin = 10; // crop margin, set to 0 for corners with no crop

             String imageUrl;
             if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                 imageUrl = movie.getBackdropPath();
                 Glide.with(context)
                         .load(imageUrl)
//                         .centerCrop()
//                    .override(Target.SIZE_ORIGINAL* 2, Target.SIZE_ORIGINAL*2)
                         .transform(new RoundedCorners(radius))
                         .placeholder(R.drawable.backimg)
                         .into(ivPoster);
             }
             else{
                 imageUrl = movie.getPosterPath();
                 Glide.with(context)
                         .load(imageUrl)
                         .fitCenter()
//                    .override(Target.SIZE_ORIGINAL* 2, Target.SIZE_ORIGINAL*2)
                         .transform(new RoundedCorners(radius))
                         .placeholder(R.drawable.image)
                         .into(ivPoster);
             }


//            Glide.with(context)
//                    .load(imageUrl)
//                    .centerCrop()
////                    .override(Target.SIZE_ORIGINAL* 2, Target.SIZE_ORIGINAL*2)
//                    .transform(new RoundedCorners(radius))
//                    .placeholder(R.drawable.image)
//                    .into(ivPoster);


        }

        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, DetailActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }

        }
    }
}

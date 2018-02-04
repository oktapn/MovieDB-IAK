package com.example.okta.moviedb_iak.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.okta.moviedb_iak.R;
import com.example.okta.moviedb_iak.adapter.AdapterRVTrailerMovie;
import com.example.okta.moviedb_iak.helper.ItemClickView;
import com.example.okta.moviedb_iak.model.detailmovie.DetailMovie;
import com.example.okta.moviedb_iak.model.trailermovie.Result;
import com.example.okta.moviedb_iak.model.trailermovie.TrailerMovie;
import com.example.okta.moviedb_iak.rest.APIClient;
import com.example.okta.moviedb_iak.rest.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitle, tvYear, tvDuration, tvRating, tvDescription;
    ImageView ivImage;
    RecyclerView rvtrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupView();
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        int id_movie = (int) bd.get("id_detail");
        getDetailMovie(id_movie, "d5f2a6ecff6d09de0c4abf1b69ea0689", "en-US");
        setTrailerMovir(id_movie, "d5f2a6ecff6d09de0c4abf1b69ea0689", "en-US");

    }

    public static void watchYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    private void setTrailerMovir(Integer movie_id, String Apikey, String language) {
        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<TrailerMovie> trailerMovieCall = apiInterface.getTrailerMovie(movie_id, Apikey, language);
        trailerMovieCall.enqueue(new Callback<TrailerMovie>() {
            @Override
            public void onResponse(Call<TrailerMovie> call, final Response<TrailerMovie> response) {
                try {
                    final List<Result> results = response.body().getResults();
                    AdapterRVTrailerMovie adapterRVTrailerMovie = new AdapterRVTrailerMovie(DetailActivity.this);
                    adapterRVTrailerMovie.setResults(results);
                    rvtrailer.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
                    rvtrailer.setAdapter(adapterRVTrailerMovie);
                    ItemClickView.addTo(rvtrailer).setOnItemClickListener(new ItemClickView.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            String id = String.valueOf(response.body().getResults().get(position).getKey());
                            watchYoutubeVideo(DetailActivity.this, id);
                        }
                    });
                } catch (NullPointerException e) {
                    Log.e("PopularMovie", "onResponse", e);
                }
            }

            @Override
            public void onFailure(Call<TrailerMovie> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupView() {
        tvTitle = findViewById(R.id.TitleDetail);
        tvYear = findViewById(R.id.YearDetail);
        tvDuration = findViewById(R.id.DurationDetail);
        tvRating = findViewById(R.id.RatingDetail);
        ivImage = findViewById(R.id.ImageDetail);
        tvDescription = findViewById(R.id.DescriptionDetail);
        rvtrailer = findViewById(R.id.rvTrailer);
    }

    private void getDetailMovie(Integer movie_id, String Apikey, String language) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proses Mengambil Data");
        progressDialog.show();

        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<DetailMovie> detailMovieCall = apiInterface.getDetailMovie(movie_id, Apikey, language);
        detailMovieCall.enqueue(new Callback<DetailMovie>() {
            @Override
            public void onResponse(Call<DetailMovie> call, Response<DetailMovie> response) {
                try {
                    progressDialog.dismiss();
                    tvTitle.setText(response.body().getTitle());
                    String release = response.body().getReleaseDate();
                    String year = release.substring(0, 4);
                    tvYear.setText(year);
                    tvDuration.setText(response.body().getRuntime() + "min");
                    tvRating.setText(String.valueOf(response.body().getVoteAverage()) + "/10");
                    Glide.with(DetailActivity.this).load("http://image.tmdb.org/t/p/w185/" + response.body().getPosterPath()).into(ivImage);
                    String overview = response.body().getOverview();
                    tvDescription.setText(overview);
                } catch (NullPointerException e) {
                    progressDialog.dismiss();
                    Log.e("PopularMovie", "onResponse", e);
                }
            }

            @Override
            public void onFailure(Call<DetailMovie> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

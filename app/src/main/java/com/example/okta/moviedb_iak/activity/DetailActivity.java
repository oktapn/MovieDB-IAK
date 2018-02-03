package com.example.okta.moviedb_iak.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.okta.moviedb_iak.R;
import com.example.okta.moviedb_iak.model.detailmovie.DetailMovie;
import com.example.okta.moviedb_iak.rest.APIClient;
import com.example.okta.moviedb_iak.rest.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitle, tvYear, tvDuration, tvRating, tvDescription;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupView();
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        int id_movie = (int) bd.get("id_detail");
        getDetailMovie(id_movie,"d5f2a6ecff6d09de0c4abf1b69ea0689","en-US");

    }

    private void setupView() {
        tvTitle = findViewById(R.id.TitleDetail);
        tvYear = findViewById(R.id.YearDetail);
        tvDuration = findViewById(R.id.DurationDetail);
        tvRating = findViewById(R.id.RatingDetail);
        ivImage = findViewById(R.id.ImageDetail);
    }

    private void getDetailMovie(Integer movie_id,String Apikey, String language) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proses Mengambil Data");
        progressDialog.show();

        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<DetailMovie> detailMovieCall = apiInterface.getDetailMovie(movie_id, Apikey, language);
        detailMovieCall.enqueue(new Callback<DetailMovie>() {
            @Override
            public void onResponse(Call<DetailMovie> call, Response<DetailMovie> response) {
                try{
                    progressDialog.dismiss();
                    tvTitle.setText(response.body().getTitle());
                    tvYear.setText(response.body().getReleaseDate());
                    tvDuration.setText(response.body().getRuntime() +"min");
                    tvRating.setText(String.valueOf(response.body().getVoteAverage())+"/10");
                    tvDescription.setText(response.body().getOverview());
                    Glide.with(DetailActivity.this).load("http://image.tmdb.org/t/p/w185/"+response.body().getPosterPath()).into(ivImage);

                }catch (NullPointerException e){
                    progressDialog.dismiss();
                    Log.e("PopularMovie", "onResponse", e);
                }
            }

            @Override
            public void onFailure(Call<DetailMovie> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}

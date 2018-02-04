package com.example.okta.moviedb_iak.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.okta.moviedb_iak.R;
import com.example.okta.moviedb_iak.adapter.AdapterRVPopularMovie;
import com.example.okta.moviedb_iak.helper.ItemClickView;
import com.example.okta.moviedb_iak.model.popularmovie.PupularMovie;
import com.example.okta.moviedb_iak.model.popularmovie.Result;
import com.example.okta.moviedb_iak.rest.APIClient;
import com.example.okta.moviedb_iak.rest.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvPopularMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPopularMovie = findViewById(R.id.RVPopularMovie);

        getPopularMovie("d5f2a6ecff6d09de0c4abf1b69ea0689", "en-US", "1");
    }

    private void getPopularMovie(String ApiKey, String language, String Page) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Proses Mengambil Data");
        progressDialog.show();
        APIInterface apiInterface = APIClient.createService(APIInterface.class);
        Call<PupularMovie> pupularMovieCall = apiInterface.getPopularMovie(ApiKey, language, Page);
        pupularMovieCall.enqueue(new Callback<PupularMovie>() {
            @Override
            public void onResponse(Call<PupularMovie> call, final Response<PupularMovie> response) {
                try {
                    progressDialog.dismiss();
                    final List<Result> results = response.body().getResults();
                    AdapterRVPopularMovie adapterRVPopularMovie = new AdapterRVPopularMovie(MainActivity.this);
                    adapterRVPopularMovie.setListPopularMovie(results);
                    rvPopularMovie.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    rvPopularMovie.setAdapter(adapterRVPopularMovie);
                    ItemClickView.addTo(rvPopularMovie).setOnItemClickListener(new ItemClickView.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            Intent DetailIntent = new Intent(MainActivity.this, DetailActivity.class);
                            Integer id = response.body().getResults().get(position).getId();
                            String overview = response.body().getResults().get(position).getOverview();
                            DetailIntent.putExtra("id_detail", id);
                            DetailIntent.putExtra("overview", overview);
                            startActivity(DetailIntent);
                        }
                    });
                } catch (NullPointerException e) {
                    progressDialog.dismiss();
                    Log.e("PopularMovie", "onResponse", e);
                }
            }

            @Override
            public void onFailure(Call<PupularMovie> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

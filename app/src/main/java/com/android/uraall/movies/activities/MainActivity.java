package com.android.uraall.movies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.uraall.movies.R;
import com.android.uraall.movies.data.MovieAdapter;
import com.android.uraall.movies.model.Movie;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {

    public static final String KEY_TITLE = "title";
    public static final String KEY_POSTER_URL = "posterUrl";
    public static final String KEY_YEAR = "year";
    public static final String KEY_DIRECTOR = "director";
    public static final String KEY_PLOT = "plot";
    public static final String KEY_RUNTIME = "runtime";

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movies = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        
        getMovies();
    }

    private void getMovies() {

        String url = "http://www.omdbapi.com/?apikey=3b8a83ef&s=superman";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Search");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String title = jsonObject.getString("Title");
                        String year = jsonObject.getString("Year");
                        String posterUrl = jsonObject.getString("Poster");

                        Movie movie = new Movie();
                        movie.setTitle(title);
                        movie.setYear(year);
                        movie.setPosterUrl(posterUrl);


                        movies.add(movie);
                    }

                    movieAdapter = new MovieAdapter(MainActivity.this,
                            movies);

                    movieAdapter.setOnItemClickListener(MainActivity.this);
                    recyclerView.setAdapter(movieAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this,
                DetailActivity.class);
        Movie clickedMovie = movies.get(position);

        intent.putExtra(KEY_TITLE, clickedMovie.getTitle());
        intent.putExtra(KEY_POSTER_URL, clickedMovie.getPosterUrl());
        intent.putExtra(KEY_YEAR, clickedMovie.getYear());
        intent.putExtra(KEY_DIRECTOR, clickedMovie.getDirector());
        intent.putExtra(KEY_PLOT, clickedMovie.getPlot());
        intent.putExtra(KEY_RUNTIME, clickedMovie.getRuntime());


        startActivity(intent);

    }
}

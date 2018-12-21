package com.dev.aman.soundcast.SongList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dev.aman.soundcast.API.ApiClient;
import com.dev.aman.soundcast.API.SongListEndPoints;
import com.dev.aman.soundcast.Adapter.SongListAdapter;
import com.dev.aman.soundcast.R;
import com.dev.aman.soundcast.model.SongList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SongListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        SongListEndPoints apiclient = ApiClient.getClient().create(SongListEndPoints.class);
        apiclient.getSongList().enqueue(new Callback<SongList>() {
            @Override
            public void onResponse(Call<SongList> call, Response<SongList> response) {
                adapter = new SongListAdapter(response.body().getResults(),MainActivity.this);
                recyclerView.setHasFixedSize(false);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<SongList> call, Throwable t) {

            }
        });


    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

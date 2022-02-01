package com.example.lyricsfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // https://api.lyrics.ovh/v1/Adele/Hello
    EditText artistName, songName;
    Button getLyricsBtn;
    TextView lyrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        artistName = findViewById(R.id.edtArtistName);
        songName = findViewById(R.id.edtSongName);
        getLyricsBtn = findViewById(R.id.button);
        lyrics = findViewById(R.id.lyrics);

        getLyricsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.lyrics.ovh/v1/" + artistName.getText().toString() + "/" + songName.getText().toString();
                url.replace(" ", "20%");
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            lyrics.setText(response.getString("lyrics"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();


                    }
                });

                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
package com.example.bookreadingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    private ArrayList<ModelClass> arrayList;
    private EditText searchEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEdt = findViewById(R.id.edit_text_search);
        ImageButton searchBtn = findViewById(R.id.button_search);
        searchBtn.setOnClickListener(v -> {
            if (searchEdt.getText().toString().isEmpty()) {
                searchEdt.setError("Please enter valid search");
                return;
            }
            getBooksInfo(searchEdt.getText().toString());
        });
    }

    private void getBooksInfo(String query) {
        arrayList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.getCache().clear();
        String apiKey = "AIzaSyAB5Z4_rPT8tVrkanU8B0R63SyyLXtipko";
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&key=" + apiKey;
        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray itemsArray = response.getJSONArray("items");

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject itemsObj = itemsArray.getJSONObject(i);
                    JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                    String title = volumeObj.optString("title");
                    String subtitle = volumeObj.optString("subtitle");
                    JSONArray authorsArray = volumeObj.getJSONArray("authors");
                    String publisher = volumeObj.optString("publisher");
                    String publishedDate = volumeObj.optString("publishedDate");
                    String description = volumeObj.optString("description");
                    int pageCount = volumeObj.optInt("pageCount");
                    JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");
                    assert imageLinks != null;
                    String thumbnail = imageLinks.optString("thumbnail");
                    String previewLink = volumeObj.optString("previewLink");
                    String infoLink = volumeObj.optString("infoLink");
                    JSONObject saleInfoObj = itemsObj.optJSONObject("saleInfo");
                    assert saleInfoObj != null;
                    String buyLink = saleInfoObj.optString("buyLink");
                    ArrayList<String> authorsArrayList = new ArrayList<>();
                    if (authorsArray.length() != 0) {
                        for (int j = 0; j < authorsArray.length(); j++) {
                            authorsArrayList.add(authorsArray.optString(i));
                        }
                    }
                    ModelClass modelClass = new ModelClass(title, subtitle, authorsArrayList, publisher, publishedDate, description, pageCount, thumbnail, previewLink, infoLink, buyLink);
                    arrayList.add(modelClass);
                    setupRecyclerView(arrayList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(MainActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
        });
        queue.add(booksObjrequest);
    }

    private void setupRecyclerView(ArrayList<ModelClass> arrayList) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Adapter adapter = new Adapter(arrayList);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
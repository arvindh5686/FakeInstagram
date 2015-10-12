package com.walmartlabs.classwork.fakeinstagram;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {


    private static final String CLIENT_ID = "2e78fc0f61574050abce5c4ab6095c50";
    private ArrayList<Photo> photos;
    private PhotosAdaper photosAdaper;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        photos = new ArrayList<Photo>();
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        photosAdaper = new PhotosAdaper(this, photos);
        lvPhotos.setAdapter(photosAdaper);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularphotos();
            }
        });

        getPopularphotos();
    }

    public void getPopularphotos() {

        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        //creates the network client
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJson = null;
                try {
                    photosJson = response.getJSONArray("data");


                    for(int i=0; i<photosJson.length(); i++) {
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        Photo photo = new Photo();
                        photo.setUserName(photoJson.getJSONObject("user").getString("username"));
                        photo.setProfilePicUrl(photoJson.getJSONObject("user").getString("profile_picture"));
                        photo.setCaption(photoJson.getJSONObject("caption").getString("text"));
                        photo.setImageUrl(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setImageHeight(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                        photo.setLikesCount(photoJson.getJSONObject("likes").getInt("count"));
                        JSONArray commentsObj = photoJson.getJSONObject("comments").getJSONArray("data");//.getJSONArray("data");
                        String lastComment = commentsObj.getJSONObject(commentsObj.length() - 1).getString("text");

                        ArrayList<Comment> comments = new ArrayList<Comment>();
                        for(int j=0; j<commentsObj.length();j++) {
                            Comment comment = new Comment(commentsObj.getJSONObject(j).getString("text"),
                                    commentsObj.getJSONObject(j).getJSONObject("from").getString("username"));
                            comments.add(comment);
                        }
                        photo.setComments(comments);
                      //  comments.get(comments.length() - 1);
                        photos.add(photo);
                    }
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }

                photosAdaper.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

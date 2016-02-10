package com.spitfireathlete.nidhi.nytsearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private EditText etQuery;
    private GridView gvResults;
    private Button btnSearch;
    private List<Article> articles;

    private static final String NYT_API_KEY = "14886966169991e54b1a4710491c9efc:4:74352748";
    private static final String NYT_SEARCH_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        this.articles = new ArrayList<Article>();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    public void handleSearchClicked(View view) {

        String query = etQuery.getText().toString();
        searchFor(query);

    }

    private void searchFor(String query) {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("api-key", NYT_API_KEY);
        params.add("page", "0");
        params.add("q", query);
        httpClient.get(NYT_SEARCH_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray articlesJSON = null;
                try {
                    articlesJSON = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.fromJSONArray(articlesJSON));

                    Log.d("DEBUG", articles.toString());

                } catch (JSONException e) {
                    Log.e("ERROR", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // FIXME: do something
                Log.e("ERROR", errorResponse.toString());
            }
        });
    }
}
package com.spitfireathlete.nidhi.nytsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.spitfireathlete.nidhi.nytsearch.activities.ArticleActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private EditText etQuery;
    private GridView gvResults;
    private Button btnSearch;
    private List<Article> articles;
    private ArticleArrayAdapter adapter;

    private static final String NYT_API_KEY = "14886966169991e54b1a4710491c9efc:4:74352748";
    private static final String NYT_SEARCH_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

    private SettingsFragment settings;

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

        adapter = new ArticleArrayAdapter(this, this.articles);
        gvResults.setAdapter(adapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class); // getApp context bc inside anonymous class

                Article a = articles.get(position);
                i.putExtra("article", a);


                startActivity(i);

            }
        });

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
            showSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showSettings() {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        settings = new SettingsFragment();
        settings.show(ft, "FILTERS");

    }


    public void handleSearchClicked(View view) {
        String query = etQuery.getText().toString();
        searchFor(query);
    }

    public void handleApplyFiltersClicked (View view){
        settings.applyFilters();
        settings.dismiss();
    }

    public void handleCancelFiltersClicked (View view){
        settings.dismiss();
    }

    private void searchFor(String query) {
        AsyncHttpClient httpClient = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.add("api-key", NYT_API_KEY);
        params.add("page", "0");
        params.add("q", query);

        if (settings != null) {
            SettingsFragment.Filters filters = settings.getFilters();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

            if (!TextUtils.isEmpty(filters.newsDesk)) {
                params.add("fq", "news_desk:(\"" + filters.newsDesk + "\")");
            }

            if (filters.beginDate != null) {
                params.add("begin_date", format.format(filters.beginDate.getTime()));
            }

            if (filters.sortNewestFirst) {
                params.add("sort", "newest");
            } else {
                params.add("sort", "oldest");
            }
        }

        httpClient.get(NYT_SEARCH_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray articlesJSON = null;
                try {
                    articlesJSON = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJSONArray(articlesJSON));

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

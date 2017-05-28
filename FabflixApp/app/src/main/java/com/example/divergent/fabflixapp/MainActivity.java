package com.example.divergent.fabflixapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    ArrayAdapter<String> adapter;
    ListView movieListView;
    Paginator pagination;
    Button nextBtn, prevBtn;
    int totalPages = Paginator.TOTAL_NUM_MOVIES / Paginator.MOVIES_PER_PAGE;
    int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up Navigation Drawer
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String msg = getIntent().getStringExtra("jsonObj");
        try {
            JSONObject user = new JSONObject(msg);
            String firstName = user.getString("first_name");
            String lastName = user.getString("last_name");
            Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show();

            //do java servlet request
            MyTask getMovieList = new MyTask();
            String url = "http://174.77.47.211:8080/ICS122B/AndroidGetMovieList";
            getMovieList.setUrl(url);

            GetMovieRequest getMovie = new GetMovieRequest(this);
            getMovie.execute(getMovieList);

            movieListView = (ListView)findViewById(R.id.movieListView);

            //ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1);
//            View vi = getLayoutInflater().inflate(R.layout.navigation_header,null);
//            NavigationView navHeaderLayout = (NavigationView) mDrawerLayout.findViewById(R.id.headerView);
//            RelativeLayout rl = (RelativeLayout) navHeaderLayout.findViewById(R.id.header);
//            TextView welcomeMsg = (TextView) rl.findViewById(R.id.tvWelcome);
//            welcomeMsg.append(firstName + " " + lastName);
//            rl.addView(vi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public class GetMovieRequest extends AsyncTask<MyTask,String,List<String>> {

        Context context;
        public GetMovieRequest(Context context)
        {
            this.context = context.getApplicationContext();
        }
        @Override
        protected List<String> doInBackground(MyTask... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                MyTask currentTask = params[0];
                URL myUrl = new URL(currentTask.getUrl());
                connection = (HttpURLConnection) myUrl.openConnection();
                //Set request type
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setUseCaches(false);
                connection.setDoOutput(true);

                //Receive response
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));
                String line = "";
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }
                List<String> movieList = new ArrayList<String>();
                String jsonMovie = buffer.toString();
                StringBuffer finalBufferedData = new StringBuffer();
                try {
                    JSONArray parentArray = new JSONArray(jsonMovie);
                    for (int i = 0; i < parentArray.length();i++)
                    {
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        String title = finalObject.getString("title");
                        movieList.add(title);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return movieList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try{
                    if (reader != null)
                        reader.close();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            pagination = new Paginator(result);
            totalPages = Paginator.TOTAL_NUM_MOVIES / Paginator.MOVIES_PER_PAGE;
            currentPage = 0;
            nextBtn = (Button) findViewById(R.id.nextBtn);
            prevBtn = (Button) findViewById(R.id.previousBtn);

            //adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, result);
            //movieListView.setAdapter(adapter);
            movieListView.setOnItemClickListener (new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MainActivity.this,pagination.generatePage(currentPage).get(i),Toast.LENGTH_SHORT).show();
                }
            });

            movieListView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, pagination.generatePage(currentPage)));

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPage += 1;
                    movieListView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, pagination.generatePage(currentPage)));
                    toggleButton();
                }
            });

            prevBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPage -= 1;
                    movieListView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, pagination.generatePage(currentPage)));
                    toggleButton();
                }
            });
        }

        private void toggleButton(){
            if (currentPage == totalPages)
            {
                nextBtn.setEnabled(false);
                prevBtn.setEnabled(true);
            }
            else if (currentPage == 0)
            {
                prevBtn.setEnabled(false);
                nextBtn.setEnabled(true);
            }
            else if (currentPage >=1 && currentPage <= 5)
                nextBtn.setEnabled(true);
                prevBtn.setEnabled(true);
        }
    }
}

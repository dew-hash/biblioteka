package com.example.coronashow;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static final String COVID_API = "https://covid19-api.weedmark.systems/api/v1/stats";
    private ArrayList<Corona> coronaList = new ArrayList<Corona>();
    private RecyclerView recyclerView;
    private Adapter adapter;
    private SearchView searchView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //bus paleidžiama nauja gija
        AsyncFetch asyncFetch = new AsyncFetch();
        asyncFetch.execute();   //Šitas pats iškviečia tuos metodus ir naudoja.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // adds item to action bar
        getMenuInflater().inflate(R.menu.search, menu);
        // Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
            searchView.setIconified(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    // Every time when you press search button on keypad an Activity is recreated which in turn calls this function
    @Override
    protected void onNewIntent(Intent intent) {
        // Get search query
        super.onNewIntent(intent); //šitas padaro, kad metodas neraudonuotų
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY); //išsitraukia, ką vartotojas įvedė
            if (searchView != null) {
                searchView.clearFocus(); //išvalo bluksintį kursorių
            }
            //System.out.println("Lithuania corona stats: "+JSON.getCoronaListByCountry(coronaList,"Lithuania"));
            ArrayList<Corona> coronaListByCountry = JSON.getCoronaListByCountry(coronaList, query);
            if (coronaListByCountry.size() == 0) {
                Toast.makeText(this, getResources().getString(R.string.search_no_results) + query, Toast.LENGTH_SHORT);
            }
            //duomenų perdavimas adapteriui ir RecyclerView sukurimas
            recyclerView = (RecyclerView) findViewById(R.id.corona_list);
            adapter = new Adapter(this, coronaListByCountry);
            recyclerView.setAdapter(adapter);
        }
    }

    private class AsyncFetch extends AsyncTask <String, String, JSONObject> {
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);  //Pas mokytoją nėra!!!!!!!!!!!!???

        @Override
        //šitas bus vykdomas prieš doInBackground, paprašysime vartotojo palaukti
        protected void onPreExecute() { //this method will be running on UI thread
            super.onPreExecute();
            progressDialog.setMessage(getResources().getString(R.string.search_loading_data));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }//onPreExecute

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONObject jsonObject = JSON.readJsonFromUrl(COVID_API);
                return jsonObject;
            } catch (IOException e) {
                Toast.makeText(SearchActivity.this, getResources().getText(R.string.search_error_reading_data) + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (JSONException e) {
                Toast.makeText(SearchActivity.this, getResources().getString(R.string.search_error_reading_data) + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            return null;
        }//doInBackground

        @Override
        protected void onPostExecute(JSONObject jsonObject) { //this method will be running on UI thread
            progressDialog.dismiss();
            int statusCode = 0;
            try {
                statusCode = (Integer) jsonObject.get("statusCode");
            } catch (JSONException e) {
                Toast.makeText(SearchActivity.this, getResources().getString(R.string.search_error_reading_data) + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            if (statusCode == HttpURLConnection.HTTP_OK) {
                //System.err.println(jsonObject.toString());
                //Toast.makeText(SearchActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
                JSONArray jsonArray = null;
                try {
                    jsonArray = JSON.getJSONArray(jsonObject);
                    coronaList = JSON.getList(jsonArray);
                    System.out.println("Lithuania corona stats: "+JSON.getCoronaListByCountry(coronaList,"Lithuania"));
                } catch (JSONException e) {
                    System.out.println(getResources().getString(R.string.search_error_reading_data) + e.getMessage());
                    e.printStackTrace();
                }
                System.err.println(jsonObject.toString());
                Toast.makeText(SearchActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
            } else { //something went wrong
                String message = null;
                try {
                    message = (String) jsonObject.get("message");
                } catch (JSONException e) {
                    Toast.makeText(SearchActivity.this, getResources().getString(R.string.search_error_reading_data) + message, Toast.LENGTH_LONG).show();
                }
                Toast.makeText(SearchActivity.this, getResources().getString(R.string.search_error_reading_data) + message, Toast.LENGTH_LONG).show();
            }//else
        }//onPostExecute
    }//AsyncFetch

}//SearchActivity
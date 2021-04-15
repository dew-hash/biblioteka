package com.example.coronashow;

import androidx.appcompat.app.AppCompatActivity;//<-kodėl pas jus android.support.v7.app.AppCompatActivity
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static final String COVID_API = "https://covid19-api.weedmark.systems/api/v1/stats";
    private ArrayList<Corona> coronaList = new ArrayList<Corona>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //bus paleidžiama nauja gija
        AsyncFetch asyncFetch = new AsyncFetch();
        asyncFetch.execute();   //Šitas pats iškviečia tuos metodus ir naudoja.
    }

    private class AsyncFetch extends AsyncTask <String, String, JSONObject> {
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);

        @Override
        //šitas bus vykdomas prieš doInBackground, paprašysime vartotojo palaukti
        protected void onPreExecute() {
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
                Toast.makeText(SearchActivity.this, getResources().getString(R.string.search_error_reading_string) + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (JSONException e) {
                Toast.makeText(SearchActivity.this, getResources().getString(R.string.search_error_reading_string) + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            return null;
        }//doInBackground

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            progressDialog.dismiss();
            int statusCode = 0;
            try {
                statusCode = jsonObject.getInt("statusCode");
            } catch (JSONException e) {
                Toast.makeText(SearchActivity.this, getResources().getString(R.string.search_error_reading_string) + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            if (statusCode == HttpURLConnection.HTTP_OK) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = JSON.getJSONArray(jsonObject);
                    coronaList = JSON.getList(jsonArray);
                    System.out.println("Lithuania corona stats: "+JSON.getCoronaListByCountry(coronaList,"Lithuania"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.err.println(jsonObject.toString());
                Toast.makeText(SearchActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
            } else {
                String message = null;
                try {
                    message = jsonObject.getString(message);
                } catch (JSONException e) {
                    Toast.makeText(SearchActivity.this, getResources().getString(R.string.search_error_reading_string) + message, Toast.LENGTH_LONG).show();
                }
            }//else
        }//onPostExecute
    }//AsyncFetch

}//SearchActivity
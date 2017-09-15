package com.example.aarshad.socialappstart;


import android.app.SearchManager;
import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SearchView;


import java.io.UnsupportedEncodingException;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<AdapterItems> listnewsData = new ArrayList<AdapterItems>();
    int StartFrom=0;
    int userOperation =SearchType.MyFollowing;
    String searchquery;
    int totalItemCountVisible=0;
    LinearLayout channelInfo;
    TextView txtnamefollowers;
    int selectedUserID =0;
    Button buFollow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        channelInfo =(LinearLayout)findViewById(R.id.ChannelInfo) ;
        channelInfo.setVisibility(View.GONE);
        txtnamefollowers=(TextView)findViewById(R.id.txtnamefollowers) ;
        buFollow=(Button)findViewById(R.id.buFollow);

        // Data Setttings
        SaveSettings saveSettings = new SaveSettings(getApplicationContext());
        saveSettings.loadData();


        ListView lsNews=(ListView)findViewById(R.id.LVNews);
        // lsNews.setAdapter(myadapter);//intisal with data

    }

    public void buFollowers(View view) {


    }


    SearchView searchView;
    Menu myMenu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        myMenu=menu;
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (android.widget.SearchView) menu.findItem(R.id.searchbar).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //final Context co=this;
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchquery =null;
                try {
                    //for space with name
                    searchquery = java.net.URLEncoder.encode(query , "UTF-8");
                } catch (UnsupportedEncodingException e) {

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }







}

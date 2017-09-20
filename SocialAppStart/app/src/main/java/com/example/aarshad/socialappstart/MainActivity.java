package com.example.aarshad.socialappstart;


import android.app.SearchManager;
import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    MyCustomAdapter myCustomAdapter;

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

        listnewsData.add(new AdapterItems(null,null,null,"add",null,null,null));
        listnewsData.add(new AdapterItems(null,null,null,"loading",null,null,null));
        listnewsData.add(new AdapterItems(null,null,null,"notweet",null,null,null));
        listnewsData.add(new AdapterItems(null,null,null,"else",null,null,null));
        myCustomAdapter=new MyCustomAdapter(this,listnewsData);
        ListView lsNews=(ListView)findViewById(R.id.LVNews);
        lsNews.setAdapter(myCustomAdapter);

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



    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater ;
        Context context;
        public MyCustomAdapter(Context context,ArrayList<AdapterItems>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
            this.context=context;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final AdapterItems s = listnewsDataAdpater.get(position);
            // inflating different layouts depending on the tweet_date
            if(s.tweet_date.equals("add")) {
                LayoutInflater mInflater = getLayoutInflater();
                View myView = mInflater.inflate(R.layout.tweet_add, null);

                final EditText etPost = (EditText) myView.findViewById(R.id.etPost);
                ImageView iv_post=(ImageView) myView.findViewById(R.id.iv_post) ;

                ImageView iv_attach=(ImageView) myView.findViewById(R.id.iv_attach) ;
                iv_attach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                iv_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    // TODO:
                    }
                });

                return myView;
            }
            else if(s.tweet_date.equals("loading")) {
                LayoutInflater mInflater = getLayoutInflater();
                View myView = mInflater.inflate(R.layout.tweet_loading, null);
                return myView;
            }
            else if(s.tweet_date.equals("notweet")) {
                LayoutInflater mInflater = getLayoutInflater();
                View myView = mInflater.inflate(R.layout.tweet_msg, null);
                return myView;
            }


            else {
                LayoutInflater mInflater = getLayoutInflater();
                View myView = mInflater.inflate(R.layout.tweet_item, null);

                TextView txtUserName = (TextView) myView.findViewById(R.id.txtUserName);
                txtUserName.setText(s.first_name);
                txtUserName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      // TODO;

                    }
                });
                TextView txt_tweet = (TextView) myView.findViewById(R.id.txt_tweet);
                txt_tweet.setText(s.tweet_text);

                TextView txt_tweet_date = (TextView) myView.findViewById(R.id.txt_tweet_date);
                txt_tweet_date.setText(s.tweet_date);
                // TODO: Picasso Loading
                ImageView tweet_picture=(ImageView)myView.findViewById(R.id.tweet_picture);

                ImageView picture_path=(ImageView)myView.findViewById(R.id.picture_path);

                return myView;
            }
        }

    }


}

package com.leaseweb.mario.nazarethvillage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.leaseweb.mario.nazarethvillage.R.drawable.ic_connection_nointernet;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
ViewFlipper v_flipper;
    private TimerTask NoticeTimerTask;
    private final Handler handler = new Handler();
    Timer timer = new Timer();
    ArrayList<Story> MyData = null;
    ListView mListView;


    private int index =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView text = (TextView) findViewById(R.id.textview);
        TextView text2 = (TextView) findViewById(R.id.textView2);
        final ImageView test = (ImageView) findViewById(R.id.imageView4);
        text.setVisibility(View.INVISIBLE);
        text2.setVisibility(View.INVISIBLE);
        test.setVisibility(View.INVISIBLE);
        //v_flipper = findViewById(R.id.flipper);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        GetAllData();

        





        /*for(Story story: MyData){
            flipperImages(story.getImage(),MyData);
        }*/





    }
    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();



    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();

    }
    private void setViews(final ArrayList<Story> arraylist) {
        final TranslateAnimation animation = new TranslateAnimation(1500.0f, 0.0f, 0.0f, 0.0f); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
        animation.setDuration(500); // animation duration
        animation.setRepeatCount(0); // animation repeat count if u repeat only once set to 1 if u don't repeat set to 0
        animation.setFillAfter(false);
        final int max = arraylist.size();
        final int[] current = {0};
        final TextView text2 = (TextView) findViewById(R.id.textView2);
        final ImageView test = (ImageView) findViewById(R.id.imageView4);
        NoticeTimerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if(MyApplication.isActivityVisible()){
                            if(current[0]<max) {
                                Log.d("NoticeTimerTask", String.valueOf(current[0]));
                                text2.setText(arraylist.get(current[0]).getTitle());
                                test.setImageBitmap(arraylist.get(current[0]).getImage());
                                test.startAnimation(animation);
                                //flipperImages(arraylist.get(current[0]).getImage());
                                current[0]++;
                            }
                            else {
                                Log.d("NoticeTimerTask", String.valueOf(current[0]) + " timer.cancel();");
                                current[0] = 0; // do this if yu want to loop back from first item
                                text2.setText(arraylist.get(current[0]).getTitle());
                                test.setImageBitmap(arraylist.get(current[0]).getImage());
                                test.startAnimation(animation);
                                current[0] = 1;
                                //timer.cancel(); // do this if you want to stop
                            }
                        }else{}

                    }
                });
            }
        };
        if (timer!=null) {
            timer.schedule(NoticeTimerTask, 0, 4000);
        }
    }


    public void flipperImages(Bitmap Img){
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(Img);
        //v_flipper.addView(imageView);
        //v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);
        //setViews(arraylist);
        ///animation
        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);

    }
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return MyData.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final View view2 = getLayoutInflater().inflate(R.layout.customlayout, null);

            ImageView mImageView = (ImageView) view2.findViewById(R.id.imageView);
            TextView mTextView = (TextView) view2.findViewById(R.id.textView);
            TextView mTextView1 = (TextView) view2.findViewById(R.id.textView1);
            mImageView.setImageBitmap(MyData.get(i).getImage());
            mTextView.setText(MyData.get(i).getTitle());
            mTextView1.setText(MyData.get(i).getContent());
            final int j = i;
            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent a = new Intent(getApplicationContext(), DetailsActivity.class);

                    a.putExtra("index", MyData.get(j).getId());
                    startActivity(a);

                }
            });


            return view2;
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_onlineshop) {

        } else if (id == R.id.nav_signup) {

        }  else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void GetAllData(){
        class GetAllData extends AsyncTask<String,Void,String>{
            ProgressDialog loading;
            TextView text = (TextView) findViewById(R.id.textview);
            TextView text2 = (TextView) findViewById(R.id.textView2);
            ImageView test = (ImageView) findViewById(R.id.imageView4);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Loading","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(MyData.size()>0){


                    setViews(MyData);
                    mListView = (ListView)findViewById(R.id.listView);
                    mListView.setFocusable(false);
                    CustomAdapter customAdapter = new CustomAdapter();
                    mListView.setAdapter(customAdapter);
                    text.setVisibility(View.VISIBLE);
                    text2.setVisibility(View.VISIBLE);
                    test.setVisibility(View.VISIBLE);
                    loading.dismiss();
                }else{

                    loading.dismiss();
                    test.setImageResource(R.drawable.nointernet);
                    test.setVisibility(View.VISIBLE);
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativelayout) ;
                    layout.setBackgroundColor(Color.parseColor("#FDFDFE"));
                    Toast.makeText(MainActivity.this,"No Connection",Toast.LENGTH_LONG).show();
                }



            }

            @Override
            protected String doInBackground(String... params) {
                GetData mydata = new GetData();
                MyData = mydata.doInBackground();
                return null;

            }

        }
        GetAllData gai = new GetAllData();
        gai.execute();
    }
}

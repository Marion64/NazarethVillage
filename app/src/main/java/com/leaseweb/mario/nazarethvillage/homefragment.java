package com.leaseweb.mario.nazarethvillage;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Homefragment extends Fragment {
    private static final String TAG = "homeFragment" ;
    View view;
    private TimerTask NoticeTimerTask;
    private final Handler handler = new Handler();
    Timer timer = new Timer();
    ArrayList<Story> MyData = null;
    ListView mListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.homefragment_layout, container, false);
        Log.d(TAG, "onCreateView: started.");

        TextView text = (TextView) view.findViewById(R.id.textview);
        TextView text2 = (TextView) view.findViewById(R.id.textView2);
        final ImageView test = (ImageView) view.findViewById(R.id.imageView4);
        text.setVisibility(View.INVISIBLE);
        text2.setVisibility(View.INVISIBLE);
        test.setVisibility(View.INVISIBLE);
        GetAllData("select * from story");




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.activityResumed();



    }

    @Override
    public void onPause() {
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
        final TextView text2 = (TextView) view.findViewById(R.id.textView2);
        final ImageView test = (ImageView) view.findViewById(R.id.imageView4);
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
                    Intent a = new Intent(getActivity(), StoryDetailsActivity.class);
                    a.putExtra("index", MyData.get(j).getId());
                    startActivity(a);

                }
            });


            return view2;
        }

    }

    private void GetAllData(final String table){
        class GetAllData extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            TextView text = (TextView) view.findViewById(R.id.textview);
            TextView text2 = (TextView) view.findViewById(R.id.textView2);
            ImageView test = (ImageView) view.findViewById(R.id.imageView4);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Loading","Please Wait...",true,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(MyData.size()>0){


                    setViews(MyData);
                    mListView = (ListView) view.findViewById(R.id.listView);
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
                    RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.relativelayout) ;
                    layout.setBackgroundColor(Color.parseColor("#FDFDFE"));
                    Toast.makeText(getActivity(),"No Connection",Toast.LENGTH_LONG).show();
                }



            }

            @Override
            protected String doInBackground(String... params) {
                GetData mydata = new GetData();
                MyData = mydata.getstory(table);
                return null;

            }

        }
        GetAllData gai = new GetAllData();
        gai.execute();
    }
}

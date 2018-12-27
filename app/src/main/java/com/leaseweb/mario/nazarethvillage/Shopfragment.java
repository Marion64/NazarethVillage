package com.leaseweb.mario.nazarethvillage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Shopfragment  extends Fragment {
    private static final String TAG = "shopFragment" ;
    ArrayList<Shopitem> ShopData = null;
    View view;
    ListView mListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.shopfragment_layout, container, false);
        Log.d(TAG, "onCreateView: started.");
        Spinner mySpinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.spinner1));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {





            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                   GetAllData("select * from shop where category= 'Special Deals'");



                }
                if (i==1){
                    GetAllData("select * from shop where category= 'Olive Wood'");


                }
                if (i==2){
                    GetAllData("select * from shop where category= 'Books'");


                }
                /*mListView = (ListView )view.findViewById(R.id.listView);
                CustomAdapter customAdapter = new CustomAdapter();
                mListView.setAdapter(customAdapter);*/


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });





        return view;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ShopData.size();
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
            mImageView.setImageBitmap(ShopData.get(i).getImage());
            mTextView.setText(ShopData.get(i).getTitle());
            mTextView1.setText(ShopData.get(i).getDescription());
            final int j = i;
            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent a = new Intent(getActivity(), ProductDetailsActivity.class);
                    a.putExtra("index", ShopData.get(j).getId());
                    startActivity(a);

                }
            });


            return view2;
        }

    }

    private void GetAllData(final String table){
        class GetAllData extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Loading","Please Wait...",true,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(ShopData.size()>0){

                    mListView = (ListView) view.findViewById(R.id.shoplistView);
                    mListView.setFocusable(false);
                    CustomAdapter customAdapter = new CustomAdapter();
                    mListView.setAdapter(customAdapter);
                    loading.dismiss();
                }else{

                    loading.dismiss();
//                    RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.relativelayout) ;
                   // layout.setBackgroundColor(Color.parseColor("#FDFDFE"));
                    Toast.makeText(getActivity(),"No Connection",Toast.LENGTH_LONG).show();
                }



            }

            @Override
            protected String doInBackground(String... params) {
                GetData mydata = new GetData();
                ShopData = mydata.getshopitem(table);
                return null;

            }

        }
        GetAllData gai = new GetAllData();
        gai.execute();
    }
}

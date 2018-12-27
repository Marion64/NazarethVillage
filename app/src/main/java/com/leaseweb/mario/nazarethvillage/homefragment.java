package com.leaseweb.mario.nazarethvillage;

import android.support.v4.app.Fragment;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class homefragment extends Fragment {
    private static final String TAG = "homeFragment" ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.homefragment_layout, container, false);
        Log.d(TAG, "onCreateView: started.");






        return view;
    }
}

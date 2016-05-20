package com.darkdesign.constitution.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.darkdesign.constitution.R;
import com.darkdesign.constitution.models.DataObject;
import com.darkdesign.constitution.sql.DatabaseHelper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ryan on 5/19/16.
 */
public class DetailsFragment extends Fragment {

    private int mCurrentItemId;
    private WebView mWebView;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mCurrentItemId = getArguments().getInt("itemId");

        View layout = inflater.inflate(R.layout.view_information, container, false);
        mWebView = (WebView) layout.findViewById(R.id.content_frame);

        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.settings_shared_prefs), Context.MODE_PRIVATE);

        String viewStyle = prefs.getBoolean(getString(R.string.settings_pref_theme_light), true) == true ? "_light" : "_dark";
        String fileExtension = ".html";

        String fileUrl = "file:///" + getActivity().getFilesDir().getPath() + "/www/" + mCurrentItemId + viewStyle + fileExtension;
        Log.d("Loading file", fileUrl);
        Log.d("^ File exists", "" + (new File(fileUrl)).exists());
        mWebView.loadUrl(fileUrl);

        // Enable pinch to zoom without the zoom buttons
        mWebView.getSettings().setBuiltInZoomControls(true);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            mWebView.getSettings().setDisplayZoomControls(false);
        }


        return layout;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

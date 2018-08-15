package com.verbosetech.yoohoo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.verbosetech.yoohoo.R;

/**
 * Created by a_man on 01-01-2018.
 */

public class PrivacyPolicyDialogFragment extends BaseFullDialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_privacy, container);
        WebView web_view = view.findViewById(R.id.web_view);
        web_view.loadUrl("http://softtalk.me/Terms.html");
        WebSettings ws = web_view.getSettings();
//        deleteCache(this);
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        ws.setAppCacheEnabled(false);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }
}

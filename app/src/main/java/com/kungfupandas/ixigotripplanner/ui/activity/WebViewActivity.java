package com.kungfupandas.ixigotripplanner.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.custom.Logger;

/**
 * Created by tushar on 09/04/17.
 */
public class WebViewActivity extends ToolbarActivity {
    public static final String KEY_URL = "url";
    public static final String KEY_TITLE = "title";
    private String title;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initView() {
        setToolbarTitle("_"+title);
        WebView wv1 = (WebView) findViewById(R.id.webView);
        wv1.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(wv1, true);
        }
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        Logger.error("webViewUrl",url);
        wv1.loadUrl(url);
        Toast.makeText(this, "Please wait while the page loads...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_webview;
    }

    private void initData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString(KEY_URL);
            title = getIntent().getExtras().getString(KEY_TITLE);
            Logger.error("webview", "url: " + url);
        } else {
            showMessage("Url not found!");
            finish();
        }
    }
}

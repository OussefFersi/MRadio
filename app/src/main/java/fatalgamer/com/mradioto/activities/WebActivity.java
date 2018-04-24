package fatalgamer.com.mradioto.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import fatalgamer.com.mradioto.R;


public class WebActivity extends Activity {

    private WebView wv1;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView browser = (WebView) findViewById(R.id.webview);
        browser.setWebViewClient(new MyBrowser());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            link = bundle.getString("NewsLink");


        } else {
            link = "empty";
        }
        browser.loadUrl(link);


    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
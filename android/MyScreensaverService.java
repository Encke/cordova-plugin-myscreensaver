
package my.screensaver.plugin;

import android.service.dreams.DreamService;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Intent;

import android.content.SharedPreferences;


public class MyScreensaverService extends DreamService {
    private String screensaverUrl;
    private WebView webView;
    private SharedPreferences sharedPreferences;


    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the WebView here to avoid recreating it on every onStartCommand call
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);

        // Enable universal access from file URLs to support local file access
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);

        // Enable native storage access
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDatabaseEnabled(true);

        sharedPreferences = getSharedPreferences("ScreensaverSettings", MODE_PRIVATE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Retrieve the URL from the Intent and store it in SharedPreferences
        String screensaverUrl = intent.getStringExtra("url");
        if (screensaverUrl != null && !screensaverUrl.isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("screensaverUrl", screensaverUrl);
            editor.apply();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(true);
        setFullscreen(true);

        // Set the content view to the WebView
        setContentView(webView);
        webView.loadUrl(screensaverUrl); // Use the passed URL
    }


    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        // Load the URL from SharedPreferences when the screensaver starts
        String screensaverUrl = sharedPreferences.getString("screensaverUrl", "");
        if (!screensaverUrl.isEmpty()) {
            webView.loadUrl(screensaverUrl);
        }
    }


    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();
        // Stop any ongoing activities in the WebView when the screensaver stops
        webView.stopLoading();
        webView.clearHistory();
        webView.loadUrl("about:blank");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // Destroy the WebView when the screensaver is destroyed
        webView.destroy();
    }
}



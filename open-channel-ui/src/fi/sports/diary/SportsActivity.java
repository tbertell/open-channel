package fi.sports.diary;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

public class SportsActivity extends Activity {
	WebView mWebView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mWebView = (WebView) findViewById(R.id.webview);  
		//        mWebView.setWebViewClient(new NewsClient());  
		mWebView.getSettings().setJavaScriptEnabled(true);  
		mWebView.loadUrl("file:///android_asset/www/index.html");  
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {  
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {  
			mWebView.goBack();  
			return true;  
		}  
		return super.onKeyDown(keyCode, event);  
	}  
}
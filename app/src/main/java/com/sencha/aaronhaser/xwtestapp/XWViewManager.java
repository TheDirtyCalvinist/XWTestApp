package com.sencha.aaronhaser.xwtestapp;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

import java.util.HashMap;

public class XWViewManager {
    private static volatile XWViewManager _instance = null;
    
    private HashMap<String, XWalkView> _webViews = null;
    private static final String TAG = XWViewManager.class.getSimpleName();
    
    public static XWViewManager sharedInstance() {
        if (_instance == null) {
            synchronized (XWViewManager.class) {
                if (_instance == null) {
                    _instance = new XWViewManager();
                }
            }
        }
        
        return _instance;
    }
    
    private XWViewManager() {
        _webViews = new HashMap<String, XWalkView>();
        XWalkPreferences.setValue(XWalkPreferences.ANIMATABLE_XWALK_VIEW, true);
    }

    
    public XWalkView getWebViewForURL(String url, Activity activity) {
        XWalkView webView = _webViews.get(url);
        if (webView == null) {
            try {
                webView = new XWalkView(activity, activity);
                _webViews.put(url, webView);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        
        return webView;
    }


    // must be called on the UI thread
    public void removeAllWebViewsNow() {
          getRemoveWebViewsRunnable().run();
    }

    private Runnable getRemoveWebViewsRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                for (XWalkView webView : _webViews.values()) {
                    ViewGroup parent = (ViewGroup)webView.getParent();
                    if (parent != null) {
                        Log.d(TAG, "Destroying webview " + webView.getTitle() + " " + webView.getOriginalUrl());
                        parent.removeView(webView);
                        webView.clearCache(true);
                        webView.destroyDrawingCache();
                    }
                    webView.onDestroy();
                }

                Log.d(TAG, "Clearing " + _webViews.size() + " webviews");
                _webViews.clear();
            }
        };
    }
}

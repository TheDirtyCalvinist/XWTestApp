package com.sencha.aaronhaser.xwtestapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.xwalk.core.XWalkExtension;
import org.xwalk.core.XWalkView;


public class MyActivity extends Activity {

    private XWalkView xWalkView;
    private FrameLayout frame;
    private XWalkExtension extension;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        frame = (FrameLayout) findViewById(R.id.content);


    }

    public void onPause(){
        xWalkView.onHide();
        frame.removeAllViews();
        super.onPause();
    }

    public void onResume(){
        super.onResume();
        if(xWalkView == null){
            XWViewManager.sharedInstance().removeAllWebViewsNow();
            xWalkView = XWViewManager.sharedInstance().getWebViewForURL("http://google.com", this);
//            XWViewManager.sharedInstance().getWebViewForURL("http://www.reddit.com", this);
//            if(extension == null)
//                extension = new EchoExtension();
            frame.addView(xWalkView);
            xWalkView.load("http://google.com", "");
            Toast.makeText(this, "Xwalk " + xWalkView.getXWalkVersion(), Toast.LENGTH_LONG).show();
        } else {
            frame.addView(xWalkView);
            xWalkView.onShow();
        }

    }

    public void onDestroy(){
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
//            Intent i = new Intent(this, MyActivity.class);
//            startActivity(i);
            xWalkView.reload(XWalkView.RELOAD_IGNORE_CACHE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

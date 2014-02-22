package com.example.fragmenttest1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class RssfeedActivity extends Activity 
	implements MyListFragment.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeed);
	    
	    //manually change orientation due to bug in Android 4.4 emulator
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

	@Override
	public void onRssItemSelected(String link) {
		 DetailFragment fragment = 
				 (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
		        if (fragment != null && fragment.isInLayout()) {
		          fragment.setText(link);
		        } else {
		            Intent intent = new Intent(getApplicationContext(),
		                    DetailActivity.class);
		                intent.putExtra(DetailActivity.EXTRA_URL, link);
		                startActivity(intent);

		       }
		  }
    
}

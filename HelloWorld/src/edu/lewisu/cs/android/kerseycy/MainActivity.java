package edu.lewisu.cs.android.kerseycy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView textView;
	EditText editText;
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView)findViewById(R.id.textView1);
		editText = (EditText)findViewById(R.id.editText1);
		button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(new ButtonListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String name = editText.getText().toString();
			if(!name.equals(""))
				textView.setText(getString(R.string.hello) + " " + name + "!");
			else
				textView.setText(getString(R.string.hello_world));
			
		}
		
	}

}

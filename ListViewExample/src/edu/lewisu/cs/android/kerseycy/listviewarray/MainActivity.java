package edu.lewisu.cs.android.kerseycy.listviewarray;

import com.example.listviewtest.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String[] presidents = getResources().getStringArray(R.array.presidents);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, presidents));

	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String text = ((TextView)v).getText().toString();
		Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT).show();
	}
}

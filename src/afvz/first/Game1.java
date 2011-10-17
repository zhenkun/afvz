package afvz.first;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class Game1 extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.game1);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this));
		gridview.setOnItemClickListener(new OnItemClickListener() { 
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(Game1.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
		
		final Button btn_next = (Button) findViewById(R.id.btn_game1_next);
		btn_next.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            }
        });
		
		final Button btn_back = (Button) findViewById(R.id.btn_game1_back);
		btn_back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            }
        });
	}
}
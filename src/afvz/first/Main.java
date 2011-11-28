package afvz.first;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class Main extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        
		final String msg_help1 = 
			"1. Move tiles by clicking numbers\n" + 
			"2. Order the numbers sequentially in ascending order\n" + 
			"3. The blank tile must either be in the top left or\n" + 
			"    bottom right position\n" + 
			"4. ...\n";
		final String msg_help2 = 
			"1. Move tiles by clicking numbers\n" + 
			"2. Equations can be formed either horizontally from\n" +
			"    left to right or vertically from top to bottom\n" + 
			"3. Drag a finger accross the screen to check an equation\n" + 
			"4. ...\n";

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// place your code here
			}
		});

        final Button btn_help1 = (Button) findViewById(R.id.btn_help1);
        btn_help1.setBackgroundResource(R.drawable.btn_help);
        btn_help1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	dialog.setTitle("Help for Game 1");
            	dialog.setMessage(msg_help1);
            	AlertDialog alert = dialog.create();
            	alert.show();
            }
        });
        
        final Button btn_help2 = (Button) findViewById(R.id.btn_help2);
        btn_help2.setBackgroundResource(R.drawable.btn_help);
        btn_help2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	dialog.setTitle("Help for Game 2");
            	dialog.setMessage(msg_help2);
            	AlertDialog alert = dialog.create();
            	alert.show();
            }
        });
        
        final Button btn_gm1 = (Button) findViewById(R.id.btn_game1);
        btn_gm1.setBackgroundResource(R.drawable.game1);
        btn_gm1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks

            	Intent intent = new Intent(Main.this, Game1.class);
                startActivity(intent);
            }
        });
        
        
        final Button btn_gm2 = (Button) findViewById(R.id.btn_game2);
        btn_gm2.setBackgroundResource(R.drawable.game2);
        btn_gm2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks

                Intent intent = new Intent(Main.this, Game2.class);
                startActivity(intent);
            }
        });
        
    }
}
package afvz.first;

import android.app.Activity;
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
        
        /*
        final Button btn_qt = (Button) findViewById(R.id.btn_quit);
        btn_qt.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                Toast.makeText(Main.this, "Good Bye", Toast.LENGTH_SHORT).show();
                System.exit(0); 	//exit the program normally
            }
        });
          */   
    }
}
package afvz.first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Button btn_gm1 = (Button) findViewById(R.id.btn_game1);
        btn_gm1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                Toast.makeText(Main.this, "Game 1", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Main.this, Game1.class);
                startActivity(intent);
            }
        });
        
        
        final Button btn_gm2 = (Button) findViewById(R.id.btn_game2);
        btn_gm2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                Toast.makeText(Main.this, "Game 2", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Main.this, Game2.class);
                startActivity(intent);
            }
        });
        
    }
}
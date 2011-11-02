package afvz.first;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Game1 extends Activity {

	BoardConfig board = new BoardConfig();

	// array of the buttons to access them directly
	private Button[][] buttons = new Button[5][5];
	
	public Integer boardsize = 5;

	// all button resources
	private Integer[] btn_res_id = {
		R.drawable.btn1, R.drawable.btn2, R.drawable.btn3, R.drawable.btn4, R.drawable.btn5,
		R.drawable.btn6, R.drawable.btn7, R.drawable.btn8, R.drawable.btn9, R.drawable.btn10,
		R.drawable.btn11, R.drawable.btn12, R.drawable.btn13, R.drawable.btn14, R.drawable.btn15,
		R.drawable.btn16, R.drawable.btn17, R.drawable.btn18, R.drawable.btn19, R.drawable.btn20,
		R.drawable.btn21, R.drawable.btn22, R.drawable.btn23, R.drawable.btn24, 
	};
	
	// all buttons in the layout
	private Integer[][] btn_id = {
		{ R.id.gm1_btn1, R.id.gm1_btn2, R.id.gm1_btn3, R.id.gm1_btn4, R.id.gm1_btn5} ,
		{ R.id.gm1_btn6, R.id.gm1_btn7, R.id.gm1_btn8, R.id.gm1_btn9, R.id.gm1_btn10} ,
		{ R.id.gm1_btn11, R.id.gm1_btn12, R.id.gm1_btn13, R.id.gm1_btn14, R.id.gm1_btn15} ,
		{ R.id.gm1_btn16, R.id.gm1_btn17, R.id.gm1_btn18, R.id.gm1_btn19, R.id.gm1_btn20} ,
		{ R.id.gm1_btn21, R.id.gm1_btn22, R.id.gm1_btn23, R.id.gm1_btn24, R.id.gm1_btn25}
	};
	
	private void play_sound()
	{
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sound);
		mediaPlayer.start();
	}
	
	// display the current state of the buttons
	private void displayGrid() {
		// i is row index and j is column index
		// indexing origin is top left corner
		// loop through all buttons
		for (int i = 0; i < boardsize; i++) {
			for (int j = 0; j < boardsize; j++) {
				int val = board.getGrid(i, j);
				if ((val > numType.ZERO) && (val <= numType.TWENTYFOUR))
					buttons[i][j].setBackgroundResource(btn_res_id[val-1]);
				else if (val == numType.BLANKTILE)
					buttons[i][j].setBackgroundColor(Color.BLACK);
			}
		}
		play_sound();
		if(isBoardValid()) 
			Toast.makeText(Game1.this, "You Win", Toast.LENGTH_SHORT).show();
	}

	public Boolean isBoardValid()
	{
		int val;
		Boolean res=true;
		
		// if the first one is the tile
		if (board.getGrid(0, 0) == numType.BLANKTILE) {
			for (int i = 1; i < boardsize*boardsize; i++) {
				val = board.getGrid(i/boardsize, i%boardsize);
				res &= (val == i);
				if (!res) return false;		//short circuit
			}
			return res;
		}
		// if the last one is the tile
		else if (board.getGrid(boardsize-1, boardsize-1) == numType.BLANKTILE) {
			for (int i = 0; i < boardsize*boardsize-1; i++) {
				val = board.getGrid(i/boardsize, i%boardsize);
				res &= (val == i+1);
				if (!res) return false;		//short circuit
			}
			return res;
		}
		else return false;
	}
	
	// initialize the board to the original state, the right bottom is the tile 
	private void initializeBoard() {
		int accu = 0;
		for (int i = 0; i < boardsize; i++) {
			for (int j = 0; j < boardsize; j++) {
				accu += 1;
				board.setGrid(i, j, numType.ZERO + accu);
			}
		}
		board.setGrid(boardsize-1, boardsize-1, numType.BLANKTILE);
	}

	// setup the values for the tiles
	private void setupTiles() {
		initializeBoard(); 		// generate a good board
		board.randomizeTiles(); // swap tiles around
		displayGrid();			// display tiles
	}

	// bind buttons in an array so they are easy to access
	private void bindButtons() {
		for(int i=0; i<5; i++)
			for(int j=0; j<5; j++)
				buttons[i][j] = (Button) this.findViewById(btn_id[i][j]);
	}

	// bind row of buttons click events
	private void bindOnClicks() {
		// bind each row of buttons on click events
		bindRow(0);
		bindRow(1);
		bindRow(2);
		bindRow(3);
		bindRow(4);
	}
	
	// bind a row of buttons on click events
	private void bindRow(final int i) {
		// setup so it passes in its row and 0th position on that row
		buttons[i][0].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				board.doClick(i, 0);
				displayGrid();
			}
		});

		// setup so it passes in its row and 1st position on that row
		buttons[i][1].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				board.doClick(i, 1);
				displayGrid();
			}
		});

		// setup so it passes in its row and 2nd position on that row
		buttons[i][2].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				board.doClick(i, 2);
				displayGrid();
			}
		});

		// setup so it passes in its row and 3rd position on that row
		buttons[i][3].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				board.doClick(i, 3);
				displayGrid();
			}
		});

		// setup so it passes in its row and 4th position on that row
		buttons[i][4].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				board.doClick(i, 4);
				displayGrid();
			}
		});
	}

	private void set_board_size(int size) {
		boardsize = size;
		board.boardSize = size;
		switch (size) {
		case 2:
			for (int i = 0; i < 3; i++) {
				buttons[2][i].setVisibility(View.INVISIBLE);
				buttons[i][2].setVisibility(View.INVISIBLE);
			}
		case 3:
			for (int i = 0; i < 4; i++) {
				buttons[3][i].setVisibility(View.INVISIBLE);
				buttons[i][3].setVisibility(View.INVISIBLE);
			}
		case 4:
			for (int i = 0; i < 5; i++) {
				buttons[4][i].setVisibility(View.INVISIBLE);
				buttons[i][4].setVisibility(View.INVISIBLE);
			}
			break;
		default:
			boardsize = 5;
			board.boardSize = 5;
			break;
		}
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.game1);
		
		bindButtons(); 	// bind the buttons into an array
		bindOnClicks(); // bind their onClick events
		set_board_size(2);
		setupTiles(); 	// setup the grid of tiles
	}
}
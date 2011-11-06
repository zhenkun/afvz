package afvz.first;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class Game1 extends Activity {

	BoardConfig board = new BoardConfig();

	// array of the buttons to access them directly
	private Button[][] buttons = new Button[5][5];
	private RadioButton rb2, rb3, rb4, rb5;
	
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
	
	private void bindRBonClick()
	{
		rb2 = (RadioButton) findViewById(R.id.rBtn2x2);
		rb3 = (RadioButton) findViewById(R.id.rBtn3x3);
		rb4 = (RadioButton) findViewById(R.id.rBtn4x4);
		rb5 = (RadioButton) findViewById(R.id.rBtn5x5);

		rb2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(boardsize != 2) {
					boardsize = 2;
				set_board_size(2);
				setupTiles();
				AI.size = 2;
				}
			}
		});
		
		rb3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(boardsize != 3) {
					boardsize = 3;
				set_board_size(3);
				setupTiles();
				AI.size = 3;
				}
			}
		});
		rb4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(boardsize != 4) {
					boardsize = 4;
				set_board_size(4);
				setupTiles();
				AI.size = 4;
				}
			}
		});
		rb5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(boardsize != 5) {
					boardsize = 5;
				set_board_size(5);
				setupTiles();
				AI.size = 5;
				}
			}
		});
	}
	
	private void play_sound()
	{
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sound);
		mediaPlayer.start();
	}
	
	// when the game is done, freeze the board, let user to start a new one
	private void freeze_board()	{
		for(int i=0; i<5; i++)
			for(int j=0; j<5; j++)
				buttons[i][j].setEnabled(false);
	}
	
	// when the game is done, freeze the board, let user to start a new one
	private void active_board()	{
		for(int i=0; i<5; i++)
			for(int j=0; j<5; j++)
				buttons[i][j].setEnabled(true);
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
		if(isBoardValid()) {
			Toast.makeText(Game1.this, "You Win", Toast.LENGTH_SHORT).show();
			freeze_board();
		}
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
		bindRBonClick();	// bind the radio buttons
		
		// New game button, randomly shuffle the tiles, active the board
		Button btn_new_game = (Button) this.findViewById(R.id.gm1_btn_new);
		btn_new_game.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setupTiles();
				active_board();
			}
		});
		
		// show AI if the AI button clicked
        Button btn_aigame = (Button) this.findViewById(R.id.gm2_btn_AI);
		btn_aigame.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	Intent intent = new Intent(Game1.this, AI.class);
                startActivity(intent);
            }
        });

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
		
		// first reset all of them to visible
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++)
				buttons[i][j].setVisibility(View.VISIBLE);
		
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
		board.shuffleSize = 400;
	 	board.moves = new int[board.shuffleSize];
		bindButtons(); 	// bind the buttons into an array
		bindOnClicks(); // bind their onClick events
		set_board_size(5);
		AI.size = 5;
		setupTiles(); 	// setup the grid of tiles
		
	}
}
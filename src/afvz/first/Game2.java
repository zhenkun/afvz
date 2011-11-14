package afvz.first;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Game2 extends Activity {
	
	
	BoardConfig board = new BoardConfig();
	
	// array of the buttons to access them directly
	private Button[][] buttons;
	private Button newGame;
	private int equationTouched[];
	private int solvedEquations[][];
	private int countTouched;
	private int countSolved;
	private int score;
	private float downX, downY;
	
	private void play_sound()
	{
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sound);
		mediaPlayer.start();
	}
	
	private String showEquation(int i)
	{
		String temp = "";
		temp = temp + Integer.toString(solvedEquations[i][0]);
		
		if(solvedEquations[i][1] == numType.PLUS)
			temp = temp + "+";
		if(solvedEquations[i][1] == numType.MINUS)
			temp = temp + "-";
		if(solvedEquations[i][1] == numType.MULTIPLY)
			temp = temp + "X";
		
		temp = temp + Integer.toString(solvedEquations[i][2]);
		temp = temp + "=";
		temp = temp + Integer.toString(solvedEquations[i][4]);
		
		return temp;
	}
	
	private void showSolved()
	{
    	ListView list;
		String equations[] = new String[countSolved + 1];
		
		equations[0] = "Score " + Integer.toString(score);
		for (int i = 1; i < countSolved ; i++)
			equations[i + 1] = "";
		
		for (int i = 0; i < countSolved; i++)
			equations[i + 1] = showEquation(i);
		
		//final String [] items=new String[]{equations[0], equations[1], equations[2]};
		
        ArrayAdapter<String> ad=new ArrayAdapter<String>(Game2.this, android.R.layout.simple_list_item_1,equations);
        list=(ListView)findViewById(R.id.equationList);
        list.setAdapter(ad);
		
	}
	
	// check to see if a currently selected solution is correct
	private void checkSolution()
	{
		int num1, num2, op, val;
		boolean valid, previous;
		valid = checkValid();
		previous = checkPrevious();
		num1 = -1;
		num2 = -1;
		op = -1;
		val = -1;
			
		if(valid && previous == false)
		{
			if(equationTouched[0] > equationTouched[4])
			{
				if(getGridVal(1) == numType.EQUALSIGN)
				{
					num1 = getGridVal(4);
					num2 = getGridVal(2);
					val = getGridVal(0);
					op = getGridVal(3);
				}
			
				if(getGridVal(3) == numType.EQUALSIGN)
				{
					num1 = getGridVal(2);
					num2 = getGridVal(0);
					op = getGridVal(1);
					val = getGridVal(4);
				}
			}
			else
			{
				if(getGridVal(1) == numType.EQUALSIGN)
				{
					num1 = getGridVal(2);
					num2 = getGridVal(4);
					val = getGridVal(0);
					op = getGridVal(3);
				}
			
				if(getGridVal(3) == numType.EQUALSIGN)
				{
					num1 = getGridVal(0);
					num2 = getGridVal(2);
					op = getGridVal(1);
					val = getGridVal(4);
				}
			}
			
			solvedEquations[countSolved][0] = num1;
			solvedEquations[countSolved][1] = op;
			solvedEquations[countSolved][2] = num2;
			solvedEquations[countSolved][3] = numType.EQUALSIGN;
			solvedEquations[countSolved][4] = val;
			
			countSolved++;
			
			score += countSolved * 2 + (op - numType.PLUS + 1) * 5;
			
			showSolved();
		}
		
	}
	
	private boolean checkValid()
	{
		boolean flag = false;
		int num1, num2, val, op;
		num1 = -1;
		num2 = -1;
		val = -1;
		op = -1;
	
		
		if(equationTouched[0] > equationTouched[4])
		{
			if(getGridVal(1) == numType.EQUALSIGN)
			{
				num1 = getGridVal(4);
				num2 = getGridVal(2);
				val = getGridVal(0);
				op = getGridVal(3);
			}
		
			if(getGridVal(3) == numType.EQUALSIGN)
			{
				num1 = getGridVal(2);
				num2 = getGridVal(0);
				op = getGridVal(1);
				val = getGridVal(4);
			}
		}
		else
		{
			if(getGridVal(1) == numType.EQUALSIGN)
			{
				num1 = getGridVal(2);
				num2 = getGridVal(4);
				val = getGridVal(0);
				op = getGridVal(3);
			}
		
			if(getGridVal(3) == numType.EQUALSIGN)
			{
				num1 = getGridVal(0);
				num2 = getGridVal(2);
				op = getGridVal(1);
				val = getGridVal(4);
			}
		}
		
		switch(op)
		{
			case numType.PLUS:
				if (num1 + num2 == val)
					flag = true;
				break;
			case numType.MINUS:
				if (num1 - num2 == val)
					flag = true;
				break;
			case numType.MULTIPLY:
				if (num1 * num2 == val)
					flag = true;
				break;
		}
		
		if (val > numType.NINE || num1 > numType.NINE || num2 > numType.NINE)
			flag = false;
		
		return flag;
	}
	
	private int getGridVal(int i)
	{
		int val = 0, temp, x, y;
		temp = equationTouched[i];
		y = temp % 5;
		x = temp / 5;
		val = board.getGrid(x, y);
		
		return val;
	}
	
	
	private boolean checkPrevious()
	{
		int num1, num2, op;
		boolean flag = false;
		num1 = -1;
		num2 = -1;
		op = -1;
		
		
		for (int i = 0; i < countSolved; i++)
		{
			if(equationTouched[0] > equationTouched[4])
			{
				if(getGridVal(1) == numType.EQUALSIGN)
				{
					num1 = getGridVal(4);
					num2 = getGridVal(2);
					op = getGridVal(3);
				}
			
				if(getGridVal(3) == numType.EQUALSIGN)
				{
					num1 = getGridVal(2);
					num2 = getGridVal(0);
					op = getGridVal(1);
				}
			}
			else
			{
				if(getGridVal(1) == numType.EQUALSIGN)
				{
					num1 = getGridVal(2);
					num2 = getGridVal(4);
					op = getGridVal(3);
				}
			
				if(getGridVal(3) == numType.EQUALSIGN)
				{
					num1 = getGridVal(0);
					num2 = getGridVal(2);
					op = getGridVal(1);
				}
			}
			
			if(solvedEquations[i][0] == num1 && solvedEquations[i][1] == op && solvedEquations[i][2] == num2)
				flag = true;
			if(solvedEquations[i][2] == num1 && solvedEquations[i][1] == op && solvedEquations[i][0] == num2)
				flag = true;
			
		}
		
		return flag;
	}
	
	// display the current state of the buttons
	private void displayGrid()
	{
		
		// i is row index and j is column index
		// indexing origin is top left corner
		int i, j;
		
		// loop through all buttons
		for (i = 0; i < 5; i++)
		{
			for (j = 0; j < 5; j++)
			{
				// determine value of the button and place correct imagine in its spot
				switch(board.getGrid(i,j))
				{
				case numType.ZERO:
					buttons[i][j].setBackgroundResource(R.drawable.btn0);
					break;
				case numType.ONE:
					buttons[i][j].setBackgroundResource(R.drawable.btn1);
					break;
				case numType.TWO:
					buttons[i][j].setBackgroundResource(R.drawable.btn2);
					break;
				case numType.THREE:
					buttons[i][j].setBackgroundResource(R.drawable.btn3);
					break;
				case numType.FOUR:
					buttons[i][j].setBackgroundResource(R.drawable.btn4);
					break;
				case numType.FIVE:
					buttons[i][j].setBackgroundResource(R.drawable.btn5);
					break;
				case numType.SIX:
					buttons[i][j].setBackgroundResource(R.drawable.btn6);
					break;
				case numType.SEVEN:
					buttons[i][j].setBackgroundResource(R.drawable.btn7);
					break;
				case numType.EIGHT:
					buttons[i][j].setBackgroundResource(R.drawable.btn8);
					break;
				case numType.NINE:
					buttons[i][j].setBackgroundResource(R.drawable.btn9);
					break;
				case numType.PLUS:
					buttons[i][j].setBackgroundResource(R.drawable.btn_plus);
					break;
				case numType.MINUS:
					buttons[i][j].setBackgroundResource(R.drawable.btn_minus);
					break;
				case numType.MULTIPLY:
					buttons[i][j].setBackgroundResource(R.drawable.btn_times);
					break;
				case numType.EQUALSIGN:
					buttons[i][j].setBackgroundResource(R.drawable.btn_equal);
					break;
				case numType.BLANKTILE:
					buttons[i][j].setBackgroundColor(Color.BLACK);
					break;
				}
			}
		}
	}
	
	void displaySelected()
	{
		
		int x, y;
		for(int i = 0; i < countTouched; i++)
		{
			y = equationTouched[i] % 5;
			x = equationTouched[i] / 5;
			
			if (equationTouched[i] != -1)
			{
				
				// determine value of the button and place correct imagine in its spot
				switch(board.getGrid(x,y))
				{ 
				case numType.ZERO:
					buttons[x][y].setBackgroundResource(R.drawable.selected0);
					break;
				case numType.ONE:
					buttons[x][y].setBackgroundResource(R.drawable.selected1);
					break;
				case numType.TWO:
					buttons[x][y].setBackgroundResource(R.drawable.selected2);
					break;
				case numType.THREE:
					buttons[x][y].setBackgroundResource(R.drawable.selected3);
					break;
				case numType.FOUR:
					buttons[x][y].setBackgroundResource(R.drawable.selected4);
					break;
				case numType.FIVE:
					buttons[x][y].setBackgroundResource(R.drawable.selected5);
					break;
				case numType.SIX:
					buttons[x][y].setBackgroundResource(R.drawable.selected6);
					break;
				case numType.SEVEN:
					buttons[x][y].setBackgroundResource(R.drawable.selected7);
					break;
				case numType.EIGHT:
					buttons[x][y].setBackgroundResource(R.drawable.selected8);
					break;
				case numType.NINE:
					buttons[x][y].setBackgroundResource(R.drawable.selected9);
					break;
				case numType.PLUS:
					buttons[x][y].setBackgroundResource(R.drawable.selected_plus);
					break;
				case numType.MINUS:
					buttons[x][y].setBackgroundResource(R.drawable.selected_minus);
					break;
				case numType.MULTIPLY:
					buttons[x][y].setBackgroundResource(R.drawable.selected_multiply);
					break;
				case numType.EQUALSIGN:
					buttons[x][y].setBackgroundResource(R.drawable.selected_equal);
					break;
				default:
					break;
				}
			}
		}
		
	}
	
	// figure out the solution to a row given 2 numbers and an operator
	private int solveRow(int num1, int num2, int op)
	{
		int value = 0;
		
		// addition
		if(op == 0)
			value = num1 + num2;
		
		// subtraction
		if(op == 1)
			value = num1 - num2;
		
		// multiplication
		if(op == 2)
			value = num1 * num2;
		
		return value;
	}
	
	// setup row so that there is a solvable equation in it
	private void setupRow(int row)
	{
		Random rn = new Random();
		int num1, num2, op;
		
		// get two random numbers 0 - 9
		num1 = rn.nextInt(10);
		num2 = rn.nextInt(10);
		
		// get a random operator 0 is addition, 1 is subtraction, 2 is multiplication
		op = rn.nextInt(3);
		
		// keep choosing new numbers while solution is not 0 - 9 for this row
		while(solveRow(num1, num2, op) >= 10 || solveRow(num1, num2, op) < 0)
		{
			num1 = rn.nextInt(10);
			num2 = rn.nextInt(10);
		}
		
		// add these values into the row
		board.setGrid(row, 0, num1);										
		board.setGrid(row, 1, op + numType.PLUS);							
		board.setGrid(row, 2, num2);										
		board.setGrid(row, 3, numType.EQUALSIGN);							
		board.setGrid(row, 4, solveRow(num1, num2, op));				
	}
	
	// setup the values for the tiles
	private void setupTiles()
	{
		Random rn = new Random();
		int j;

		// generate 4 solvable equations for the top 4 rows
		for(int i = 0; i < 4; i++)
			setupRow(i);
		
		// generate random values for the remaining 4 and place the blank tile in the bottom right corner
		
		for (int i = 0; i < 4; i++)
		{
			j = rn.nextInt(14);
			
			switch(j)
			{
				case 10:
					board.setGrid(4, i, numType.PLUS);
					break;
				case 11:
					board.setGrid(4, i, numType.MINUS);
					break;
				case 12:
					board.setGrid(4, i, numType.MULTIPLY);
					break;
				case 13:
					board.setGrid(4, i, numType.EQUALSIGN);
					break;
				default:
					board.setGrid(4, i, j);
						
			}
			
		}
		
		board.setGrid(4, 4, numType.BLANKTILE);
		
		// swap tiles around
		board.randomizeTiles();
		
		// display tiles
		displayGrid();
	}
	
	// bind buttons in an array so they are easy to access
	private void bindButtons()
	{
		newGame = (Button)this.findViewById(R.id.newGame);
		newGame.setBackgroundResource(R.drawable.btn_refresh);
		
		// top row of buttons
		buttons[0][0] = (Button)this.findViewById(R.id.button1);
		buttons[1][0] = (Button)this.findViewById(R.id.button2);
		buttons[2][0] = (Button)this.findViewById(R.id.button3);
		buttons[3][0] = (Button)this.findViewById(R.id.button4);
		buttons[4][0] = (Button)this.findViewById(R.id.button5);
		
		// 2nd row of buttons
		buttons[0][1] = (Button)this.findViewById(R.id.button6);
		buttons[1][1] = (Button)this.findViewById(R.id.button7);
		buttons[2][1] = (Button)this.findViewById(R.id.button8);
		buttons[3][1] = (Button)this.findViewById(R.id.button9);
		buttons[4][1] = (Button)this.findViewById(R.id.button10);
		
		// 3rd row of buttons
		buttons[0][2] = (Button)this.findViewById(R.id.button11);
		buttons[1][2] = (Button)this.findViewById(R.id.button12);
		buttons[2][2] = (Button)this.findViewById(R.id.button13);
		buttons[3][2] = (Button)this.findViewById(R.id.button14);
		buttons[4][2] = (Button)this.findViewById(R.id.button15);
		
		// 4th row of buttons
		buttons[0][3] = (Button)this.findViewById(R.id.button16);
		buttons[1][3] = (Button)this.findViewById(R.id.button17);
		buttons[2][3] = (Button)this.findViewById(R.id.button18);
		buttons[3][3] = (Button)this.findViewById(R.id.button19);
		buttons[4][3] = (Button)this.findViewById(R.id.button20);
		
		// 5th row of buttons
		buttons[0][4] = (Button)this.findViewById(R.id.button21);
		buttons[1][4] = (Button)this.findViewById(R.id.button22);
		buttons[2][4] = (Button)this.findViewById(R.id.button23);
		buttons[3][4] = (Button)this.findViewById(R.id.button24);
		buttons[4][4] = (Button)this.findViewById(R.id.button25);
	}
	
	// bind row of buttons click events
	private void bindOnClicks()
	{
		newGame.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startGame();
			}
		});
		
		// bind each row of buttons on click events
		bindRow(0);
		bindRow(1);
		bindRow(2);
		bindRow(3);
		bindRow(4);
	}
	
	// bind a row of buttons on click events
	private void bindRow(final int i)
	{
		// setup so it passes in its row and 0th position on that row
		buttons[i][0].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(board.getGrid(i,0) != numType.BLANKTILE)
				{
					board.doClick(i, 0);
					displayGrid();
					play_sound();
				}
			}
		});

		// setup so it passes in its row and 1st position on that row
		buttons[i][1].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(board.getGrid(i,1) != numType.BLANKTILE)
				{
					board.doClick(i, 1);
					displayGrid();
					play_sound();
				}
			}
		});

		// setup so it passes in its row and 2nd position on that row
		buttons[i][2].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				if(board.getGrid(i,2) != numType.BLANKTILE)
				{
					board.doClick(i, 2);
					displayGrid();
					play_sound();
				}
			}
		});

		// setup so it passes in its row and 3rd position on that row
		buttons[i][3].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(board.getGrid(i,3) != numType.BLANKTILE)
				{
					board.doClick(i, 3);
					displayGrid();
					play_sound();
				}
			}
		});

		// setup so it passes in its row and 4th position on that row
		buttons[i][4].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(board.getGrid(i,4) != numType.BLANKTILE)
				{
					board.doClick(i, 4);
					displayGrid();
					play_sound();
				}
			}
		});
	}
	
	private void bindTouch()
	{
		LinearLayout mainFrame = (LinearLayout)findViewById(R.id.game2Frame);
		
		mainFrame.setOnTouchListener(new View.OnTouchListener() {
				
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				touchEvent(v, event);
				return true;
			}
		});
		
		for(int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++)
				buttons[i][j].setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) 
					{
						touchEvent(v, event);					
						return true;
					}
				});
	}
		
	private void touchEvent(View v, MotionEvent event)
	{
		float curX, curY;
		boolean previousEquations = false;
		boolean validMove = false;
		curX = event.getRawX();
		curY = event.getRawY();
		int curButton = -1;
		int location[] = new int[2];
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(downX == event.getRawX() && downY == event.getRawY())
			{
				for(int i = 0; i < 5; i++)
					for (int j = 0; j < 5; j++)
					{
						buttons[i][j].getLocationInWindow(location);
						
						if(location[0] <= curX && (location[0] + buttons[i][j].getWidth()) >= curX)
							if(location[1] <= curY && (location[1] + buttons[i][j].getHeight()) >= curY)
								buttons[i][j].performClick();
					}
			}
			
			for(int i = 0; i < 5; i++)
				equationTouched[i] = -1;
			countTouched = 0;
			displayGrid();
		}
		
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			downX = curX;
			downY = curY;
		}
			
		if(event.getAction() == MotionEvent.ACTION_MOVE)  
		{  
			
			for(int i = 0; i < 5; i++)
				for (int j = 0; j < 5; j++)
				{
					buttons[i][j].getLocationInWindow(location);
					
					if(location[0] <= curX && (location[0] + buttons[i][j].getWidth()) >= curX)
						if(location[1] <= curY && (location[1] + buttons[i][j].getHeight()) >= curY)
							curButton = 5 * i + j;
				}
			
			for(int i = 0; i < countTouched; i++)
				if (equationTouched[i] == curButton)
					previousEquations = true;
			
			if(countTouched == 0 || curButton == equationTouched[countTouched -1] - 5 || curButton == equationTouched[countTouched -1] + 5 || curButton == equationTouched[countTouched -1] - 1 || curButton == equationTouched[countTouched -1] + 1)
				validMove = true;
			
			if(curButton != -1 && previousEquations == false && validMove)
			{
	
				equationTouched[countTouched] = curButton;
				
				countTouched++;
					
				if(countTouched > 5)
				{
					equationTouched[0] = equationTouched[1];
					equationTouched[1] = equationTouched[2];
					equationTouched[2] = equationTouched[3];
					equationTouched[3] = equationTouched[4];
					equationTouched[4] = equationTouched[5];
					countTouched = 5;
					displayGrid();
				}
					
				displaySelected();
					
				if (countTouched == 5)
					checkSolution();
			}
		}
	}
	
	private void startGame()
	{
		for (int i = 0; i < 6; i++)
    		equationTouched[i] = -1;
		countTouched = 0;
		countSolved = 0;
		score = 0;
		
		 //setup Score and list of equations
        showSolved();
        
        // bind the buttons into an array
        bindButtons();
        
        // bind their onClick events
        bindOnClicks();
        
        bindTouch();
        
        // setup the grid of tiles
        setupTiles();
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
       	
    	equationTouched = new int[6];
    	solvedEquations = new int[10][5];
    	buttons = new Button[5][5];
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game2);
        
		board.shuffleSize = 400;
	 	board.moves = new int[board.shuffleSize];
        board.boardSize = 5;
        
       startGame();
    }
}
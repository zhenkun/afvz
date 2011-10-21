package afvz.first;

import java.util.Random;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Game2 extends Activity {
	
	
	BoardConfig board = new BoardConfig();
	
	// array of the buttons to access them directly
	private Button[][] buttons;
	
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
				switch(board.getGrid(i,j))//grid[i][j])
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
		buttons = new Button[5][5];
		
		// top row of buttons
		buttons[0][0] = (Button)this.findViewById(R.id.button1);
		buttons[0][1] = (Button)this.findViewById(R.id.button2);
		buttons[0][2] = (Button)this.findViewById(R.id.button3);
		buttons[0][3] = (Button)this.findViewById(R.id.button4);
		buttons[0][4] = (Button)this.findViewById(R.id.button5);
		
		// 2nd row of buttons
		buttons[1][0] = (Button)this.findViewById(R.id.button6);
		buttons[1][1] = (Button)this.findViewById(R.id.button7);
		buttons[1][2] = (Button)this.findViewById(R.id.button8);
		buttons[1][3] = (Button)this.findViewById(R.id.button9);
		buttons[1][4] = (Button)this.findViewById(R.id.button10);
		
		// 3rd row of buttons
		buttons[2][0] = (Button)this.findViewById(R.id.button11);
		buttons[2][1] = (Button)this.findViewById(R.id.button12);
		buttons[2][2] = (Button)this.findViewById(R.id.button13);
		buttons[2][3] = (Button)this.findViewById(R.id.button14);
		buttons[2][4] = (Button)this.findViewById(R.id.button15);
		
		// 4th row of buttons
		buttons[3][0] = (Button)this.findViewById(R.id.button16);
		buttons[3][1] = (Button)this.findViewById(R.id.button17);
		buttons[3][2] = (Button)this.findViewById(R.id.button18);
		buttons[3][3] = (Button)this.findViewById(R.id.button19);
		buttons[3][4] = (Button)this.findViewById(R.id.button20);
		
		// 5th row of buttons
		buttons[4][0] = (Button)this.findViewById(R.id.button21);
		buttons[4][1] = (Button)this.findViewById(R.id.button22);
		buttons[4][2] = (Button)this.findViewById(R.id.button23);
		buttons[4][3] = (Button)this.findViewById(R.id.button24);
		buttons[4][4] = (Button)this.findViewById(R.id.button25);
	}
	
	// bind row of buttons click events
	private void bindOnClicks()
	{
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
				board.doClick(i,0);
				displayGrid();
			}
		});
		
		// setup so it passes in its row and 1st position on that row
		buttons[i][1].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				board.doClick(i,1);
				displayGrid();
			}
		});
		
		// setup so it passes in its row and 2nd position on that row
		buttons[i][2].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				board.doClick(i,2);
				displayGrid();
			}
		});
		
		// setup so it passes in its row and 3rd position on that row
		buttons[i][3].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				board.doClick(i,3);
				displayGrid();
			}
		});
		
		// setup so it passes in its row and 4th position on that row
		buttons[i][4].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				board.doClick(i,4);
				displayGrid();
			}
		});
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game2);
        
        board.boardSize = 5;
        
        // bind the buttons into an array
        bindButtons();
        
        // bind their onClick events
        bindOnClicks();
        
        // setup the grid of tiles
        setupTiles();
    }
}
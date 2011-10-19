package afvz.first;

import java.util.Random;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Game2 extends Activity {
	
	// grid which keeps track of current states
	private int[][] grid = new int[5][5];
	
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
				switch(grid[i][j])
				{
				case 0:
					buttons[i][j].setBackgroundResource(R.drawable.btn0);
					break;
				case 1:
					buttons[i][j].setBackgroundResource(R.drawable.btn1);
					break;
				case 2:
					buttons[i][j].setBackgroundResource(R.drawable.btn2);
					break;
				case 3:
					buttons[i][j].setBackgroundResource(R.drawable.btn3);
					break;
				case 4:
					buttons[i][j].setBackgroundResource(R.drawable.btn4);
					break;
				case 5:
					buttons[i][j].setBackgroundResource(R.drawable.btn5);
					break;
				case 6:
					buttons[i][j].setBackgroundResource(R.drawable.btn6);
					break;
				case 7:
					buttons[i][j].setBackgroundResource(R.drawable.btn7);
					break;
				case 8:
					buttons[i][j].setBackgroundResource(R.drawable.btn8);
					break;
				case 9:
					buttons[i][j].setBackgroundResource(R.drawable.btn9);
					break;
				case 10:
					buttons[i][j].setBackgroundResource(R.drawable.btn_plus);
					break;
				case 11:
					buttons[i][j].setBackgroundResource(R.drawable.btn_minus);
					break;
				case 12:
					buttons[i][j].setBackgroundResource(R.drawable.btn_times);
					break;
				case 13:
					buttons[i][j].setBackgroundResource(R.drawable.btn_equal);
					break;
				case 14:
					buttons[i][j].setBackgroundResource(R.drawable.btn_blank);
					break;
				}
			}
		}
	}
	
	// swap blank with tile above it
	private void swapUp(int x, int y)
	{
		int temp;
		temp = grid[x][y];
		grid[x][y] = grid[x][y-1];
		grid[x][y-1] = temp;
	}
	
	// swap blank tile with the tile below it
	private void swapDown(int x, int y)
	{
		int temp;
		temp = grid[x][y];
		grid[x][y] = grid[x][y+1];
		grid[x][y+1] = temp;
	}
	
	// swap blank tile with teh one to its left
	private void swapLeft(int x, int y)
	{
		int temp;
		temp = grid[x][y];
		grid[x][y] = grid[x-1][y];
		grid[x-1][y] = temp;
	}
	
	// swap blank tile with one to its right
	private void swapRight(int x, int y)
	{
		int temp;
		temp = grid[x][y];
		grid[x][y] = grid[x+1][y];
		grid[x+1][y] = temp;
	}
	
	// randomly select a direction that is possible to swap the blank tile
	private int swapDirection(int x, int y)
	{
		boolean found = false;
		int j = 0;
		Random rn = new Random();
		
		// keep choosing until an acceptable tile is found
		while(!found)
		{
			j = rn.nextInt() % 4;
			
			// check if it can swap up
			if(j == 3)
				if(y != 0)
					found = true;

			
			// check if it can  swap left
			if (j == 2)
				if(x != 0)
					found = true;

			
			// check if it can  swap down
			if (j == 1)
				if(y != 4)
					found = true;
			
			// check if it can  swap right
			if (j ==0)
				if(x != 4)
					found = true;
		}
		
		// return direction to swap
		return j;
	}
	
	// randomly swap tiles to randomize the starting puzzle
	private void randomizeTiles()
	{
		int j, x, y;
		x = 4;
		y = 4;
		
		// swap the tiles 150 times
		for(int i = 0; i < 150; i++)
		{
			// get a random direction to swap
			j = swapDirection(x,y);
			
			// swap according the the direction given
			// also adjust position of the blank tile accordingly
			switch(j)
			{
				case 0:
					swapRight(x,y);
					x += 1;
					break;
				case 1:
					swapDown(x,y);
					y += 1;
					break;
				case 2:
					swapLeft(x,y);
					x -= 1;
					break;
				case 3:
					swapUp(x,y);
					y -= 1;
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
		while(solveRow(num1, num2, op) > 10 || solveRow(num1, num2, op) < 0)
		{
			num1 = rn.nextInt(10);
			num2 = rn.nextInt(10);
		}
		
		// add these values into the row
		grid[row][0] = num1;
		grid[row][1] = op + 10;
		grid[row][2] = num2;
		grid[row][3] = 13;
		grid[row][4] = solveRow(num1, num2, op);
	}
	
	// setup the values for the tiles
	private void setupTiles()
	{
		Random rn = new Random();

		// generate 4 solvable equations for the top 4 rows
		for(int i = 0; i < 4; i++)
			setupRow(i);
		
		// generate random values for the remaining 4 and place the blank tile in the bottom right corner
		grid[4][0] = rn.nextInt(14);
		grid[4][1] = rn.nextInt(14);
		grid[4][2] = rn.nextInt(14);
		grid[4][3] = rn.nextInt(14);
		grid[4][4] = 14;
		
		// swap tiles around
		randomizeTiles();
		
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
	
	// adjust the grid according to where it was clicked
	private void doClick(int x, int y)
	{
		// if the spot clicked is to the left of the blank swap left
		if (x != 0 && grid[x-1][y] == 14)
			swapLeft(x, y);
		else
			// if the spot clicked is to the right of the blank right
			if (x != 4 && grid[x+1][y] == 14)
				swapRight(x,y);
			else
				// if the spot clicked is below of the blank swap down
				if (y != 4 && grid[x][y+1] == 14)
					swapDown(x,y);
				else
					// if the spot clicked is above of the blank swap up
					if(y != 0 && grid[x][y-1] == 14)
						swapUp(x,y);
		
		// redisplay the grid
		displayGrid();
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
				doClick(i,0);
			}
		});
		
		// setup so it passes in its row and 1st position on that row
		buttons[i][1].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doClick(i,1);
			}
		});
		
		// setup so it passes in its row and 2nd position on that row
		buttons[i][2].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doClick(i,2);
			}
		});
		
		// setup so it passes in its row and 3rd position on that row
		buttons[i][3].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doClick(i,3);
			}
		});
		
		// setup so it passes in its row and 4th position on that row
		buttons[i][4].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doClick(i,4);
			}
		});
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game2);
        
        // bind the buttons into an array
        bindButtons();
        
        // bind their onClick events
        bindOnClicks();
        
        // setup the grid of tiles
        setupTiles();
    }
}
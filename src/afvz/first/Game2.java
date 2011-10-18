package afvz.first;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Game2 extends Activity {
	
	private int[][] grid = new int[5][5];
	private Button[][] buttons;
	
	private void displayGrid()
	{
		int i, j;
		String num;
		
		for (i = 0; i < 5; i++)
		{
			for (j = 0; j < 5; j++)
			{
				num = Integer.toString(grid[i][j]);
				buttons[i][j].setBackgroundColor(Color.DKGRAY);
				
				switch(grid[i][j])
				{
				case 10:
					buttons[i][j].setText("+");
					buttons[i][j].setTextColor(Color.RED);
					break;
				case 11:
					buttons[i][j].setText("-");
					buttons[i][j].setTextColor(Color.YELLOW);
					break;
				case 12:
					buttons[i][j].setText("x");
					buttons[i][j].setTextColor(Color.CYAN);
					break;
				case 13:
					buttons[i][j].setText("=");
					buttons[i][j].setTextColor(Color.GREEN);
					break;
				case 14:
					buttons[i][j].setText("N");
					buttons[i][j].setTextColor(Color.BLACK);
					buttons[i][j].setBackgroundColor(Color.BLACK);
					break;
				default:
					buttons[i][j].setText(num);
					buttons[i][j].setTextColor(Color.BLUE);
				}
			}
		}
	}
	
	private void swapUp(int x, int y)
	{
		int temp;
		temp = grid[x][y];
		grid[x][y] = grid[x][y-1];
		grid[x][y-1] = temp;
	}
	
	private void swapDown(int x, int y)
	{
		int temp;
		temp = grid[x][y];
		grid[x][y] = grid[x][y+1];
		grid[x][y+1] = temp;
	}
	
	private void swapLeft(int x, int y)
	{
		int temp;
		temp = grid[x][y];
		grid[x][y] = grid[x-1][y];
		grid[x-1][y] = temp;
	}
	
	private void swapRight(int x, int y)
	{
		int temp;
		temp = grid[x][y];
		grid[x][y] = grid[x+1][y];
		grid[x+1][y] = temp;
	}
	
	private int swapDirection(int x, int y)
	{
		boolean found = false;
		int j = 0;
		Random rn = new Random();
		
		while(!found)
		{
			j = rn.nextInt() % 4;
			
			// swap up
			if(j == 3)
				if(y != 0)
					found = true;

			
			// case for swap left
			if (j == 2)
				if(x != 0)
					found = true;

			
			// case for swap down
			if (j == 1)
				if(y != 4)
					found = true;
			
			// case for swap right
			if (j ==0)
				if(x != 4)
					found = true;
		}
		
		return j;
	}
	
	private void randomizeTiles()
	{
		int j, x, y;
		x = 4;
		y = 4;
		
		for(int i = 0; i < 150; i++)
		{
			j = swapDirection(x,y);
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
	
	private int solveRow(int num1, int num2, int op)
	{
		int value = 0;
		
		if(op == 0)
			value = num1 + num2;
		if(op == 1)
			value = num1 - num2;
		if(op == 2)
			value = num1 * num2;
		
		return value;
	}
	
	private void setupRow(int row)
	{
		Random rn = new Random();
		int num1, num2, op;
		num1 = rn.nextInt(10);
		num2 = rn.nextInt(10);
		op = rn.nextInt(3);
		while(solveRow(num1, num2, op) > 10 || solveRow(num1, num2, op) < 0)
		{
			num1 = rn.nextInt(10);
			num2 = rn.nextInt(10);
		}
		
		grid[row][0] = num1;
		grid[row][1] = op + 10;
		grid[row][2] = num2;
		grid[row][3] = 13;
		grid[row][4] = solveRow(num1, num2, op);
	}
	
	private void setupTiles()
	{
		Random rn = new Random();

		for(int i = 0; i < 4; i++)
			setupRow(i);
		grid[4][0] = rn.nextInt(14);
		grid[4][1] = rn.nextInt(14);
		grid[4][2] = rn.nextInt(14);
		grid[4][3] = rn.nextInt(14);
		grid[4][4] = 14;
		
		randomizeTiles();
		
		displayGrid();
	}
	
	private void bindButtons()
	{
		buttons = new Button[5][5];
		
		buttons[0][0] = (Button)this.findViewById(R.id.button1);
		buttons[0][1] = (Button)this.findViewById(R.id.button2);
		buttons[0][2] = (Button)this.findViewById(R.id.button3);
		buttons[0][3] = (Button)this.findViewById(R.id.button4);
		buttons[0][4] = (Button)this.findViewById(R.id.button5);
		
		buttons[1][0] = (Button)this.findViewById(R.id.button6);
		buttons[1][1] = (Button)this.findViewById(R.id.button7);
		buttons[1][2] = (Button)this.findViewById(R.id.button8);
		buttons[1][3] = (Button)this.findViewById(R.id.button9);
		buttons[1][4] = (Button)this.findViewById(R.id.button10);
		
		buttons[2][0] = (Button)this.findViewById(R.id.button11);
		buttons[2][1] = (Button)this.findViewById(R.id.button12);
		buttons[2][2] = (Button)this.findViewById(R.id.button13);
		buttons[2][3] = (Button)this.findViewById(R.id.button14);
		buttons[2][4] = (Button)this.findViewById(R.id.button15);
		
		buttons[3][0] = (Button)this.findViewById(R.id.button16);
		buttons[3][1] = (Button)this.findViewById(R.id.button17);
		buttons[3][2] = (Button)this.findViewById(R.id.button18);
		buttons[3][3] = (Button)this.findViewById(R.id.button19);
		buttons[3][4] = (Button)this.findViewById(R.id.button20);
		
		buttons[4][0] = (Button)this.findViewById(R.id.button21);
		buttons[4][1] = (Button)this.findViewById(R.id.button22);
		buttons[4][2] = (Button)this.findViewById(R.id.button23);
		buttons[4][3] = (Button)this.findViewById(R.id.button24);
		buttons[4][4] = (Button)this.findViewById(R.id.button25);
	}
	
	private void doClick(int x, int y)
	{
		if (x != 0 && grid[x-1][y] == 14)
			swapLeft(x, y);
		else
			if (x != 4 && grid[x+1][y] == 14)
				swapRight(x,y);
			else
				if (y != 4 && grid[x][y+1] == 14)
					swapDown(x,y);
				else
					if(y != 0 && grid[x][y-1] == 14)
						swapUp(x,y);
		
		displayGrid();
	}
	
	private void bindOnClicks()
	{
		bindRow(0);
		bindRow(1);
		bindRow(2);
		bindRow(3);
		bindRow(4);
	}
	
	private void bindRow(final int i)
	{
		buttons[i][0].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doClick(i,0);
			}
		});
		buttons[i][1].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doClick(i,1);
			}
		});
		buttons[i][2].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doClick(i,2);
			}
		});
		buttons[i][3].setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doClick(i,3);
			}
		});
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
        
        bindButtons();
        bindOnClicks();
        
        setupTiles();
    }
}
package afvz.first;

import java.util.Random;

public class BoardConfig {

	// grid which keeps track of current states
	private int[][] grid = new int[5][5];
	
	// grid which keeps track of current states in AI
	private int[][] grid2 = new int[5][5];
	
	// an array to save the moves for AI
	private int[] moves = new int[400];
	
	// save the position of x, y after the 150th shuffle to backtrack in AI
	int xfinal;
	int yfinal;

	public int boardSize;

	// swap blank with tile above it
	private void swapUp(int x, int y) {
		int temp;
		temp = grid[x][y];
		grid[x][y] = grid[x][y - 1];
		grid[x][y - 1] = temp;
	}

	// swap blank tile with the tile below it
	private void swapDown(int x, int y) {
		int temp;
		temp = grid[x][y];
		grid[x][y] = grid[x][y + 1];
		grid[x][y + 1] = temp;
	}

	// swap blank tile with the one to its left
	private void swapLeft(int x, int y) {
		int temp;
		temp = grid[x][y];
		grid[x][y] = grid[x - 1][y];
		grid[x - 1][y] = temp;
	}

	// swap blank tile with one to its right
	private void swapRight(int x, int y) {
		int temp;
		temp = grid[x][y];
		grid[x][y] = grid[x + 1][y];
		grid[x + 1][y] = temp;
	}
	

	// swap tiles in AI
	private void swapUp2(int x, int y) {
		int temp;
		temp = grid2[x][y];
		grid2[x][y] = grid2[x][y - 1];
		grid2[x][y - 1] = temp;
	}

	// swap tiles in AI
	private void swapDown2(int x, int y) {
		int temp;
		temp = grid2[x][y];
		grid2[x][y] = grid2[x][y + 1];
		grid2[x][y + 1] = temp;
	}

	// swap tiles in AI
	private void swapLeft2(int x, int y) {
		int temp;
		temp = grid2[x][y];
		grid2[x][y] = grid2[x - 1][y];
		grid2[x - 1][y] = temp;
	}

	// swap tiles in AI
	private void swapRight2(int x, int y) {
		int temp;
		temp = grid2[x][y];
		grid2[x][y] = grid2[x + 1][y];
		grid2[x + 1][y] = temp;
	}


	// randomly select a direction that is possible to swap the blank tile
	private int swapDirection(int x, int y) {
		boolean found = false;
		int j = 0;
		Random rn = new Random();

		// keep choosing until an acceptable tile is found
		while (!found) {
			j = rn.nextInt() % 4;

			// check if it can swap up
			if (j == 3)
				if (y != 0)
					found = true;

			// check if it can swap left
			if (j == 2)
				if (x != 0)
					found = true;

			// check if it can swap down
			if (j == 1)
				if (y != boardSize - 1)
					found = true;

			// check if it can swap right
			if (j == 0)
				if (x != boardSize - 1)
					found = true;
		}

		// return direction to swap
		return j;
	}

	// randomly swap tiles to randomize the starting puzzle
	public void randomizeTiles() {
		int j, x, y;
		x = boardSize - 1;
		y = boardSize - 1;

		// swap the tiles 400 times
		for (int i = 0; i < 400; i++) {
			// get a random direction to swap
			j = swapDirection(x, y);

			// save the move for AI
			moves[i] = j;
			
			// swap according the direction given
			// also adjust position of the blank tile accordingly
			switch (j) {
			case 0:
				swapRight(x, y);
				x += 1;
				break;
			case 1:
				swapDown(x, y);
				y += 1;
				break;
			case 2:
				swapLeft(x, y);
				x -= 1;
				break;
			case 3:
				swapUp(x, y);
				y -= 1;
			}
		}
		
		// save the current x, y position
		xfinal = x;
		yfinal = y;
		for(x = 0;x<5;x++)
			for(y = 0; y<5;y++)
				grid2[x][y]=grid[x][y];
	}
	
	// for solving the AI board
	public void solveAI(int i) {
		int j, x, y;
		
		// retrieve the current position of x , y 
		x = xfinal;
		y = yfinal;

		
		j = moves[i];		

			// swap according the the direction given
			// also adjust position of the blank tile accordingly
			switch (j) {
			case 0:
				swapLeft2(x, y);
				x -= 1;				
				break;
			case 1:
				swapUp2(x, y);
				y -=1;
				break;
			case 2:
				swapRight2(x, y);
				x += 1;
				break;
			case 3:
				swapDown2(x, y);
				y += 1;
			}
			
			//save the current x , y
			xfinal = x;
			yfinal = y;
	}


	public void doClick(int x, int y) {
		// if the spot clicked is to the left of the blank swap left
		if (x != 0 && grid[x - 1][y] == numType.BLANKTILE)
			swapLeft(x, y);
		else
		// if the spot clicked is to the right of the blank right
		if (x != boardSize - 1 && grid[x + 1][y] == numType.BLANKTILE)
			swapRight(x, y);
		else
		// if the spot clicked is below of the blank swap down
		if (y != boardSize - 1 && grid[x][y + 1] == numType.BLANKTILE)
			swapDown(x, y);
		else
		// if the spot clicked is above of the blank swap up
		if (y != 0 && grid[x][y - 1] == numType.BLANKTILE)
			swapUp(x, y);

	}

	public void setGrid(int x, int y, int val) {
		if (y <= boardSize - 1 && y >= 0 && x >= 0 && x <= boardSize - 1)
			grid[x][y] = val;
	}
	
	// set grid for AI board
	public void setGrid2(int x, int y, int val) {
		if (y <= boardSize - 1 && y >= 0 && x >= 0 && x <= boardSize - 1)
			grid2[x][y] = val;
	}

	public int getGrid(int x, int y) {
		if (y <= boardSize - 1 && y >= 0 && x >= 0 && x <= boardSize - 1)
			return grid[x][y];
		else
			return -1;
	}
	
	// get grid for AI board
	public int getGrid2(int x, int y) {
		if (y <= boardSize - 1 && y >= 0 && x >= 0 && x <= boardSize - 1)
			return grid2[x][y];
		else
			return -1;
	}

}

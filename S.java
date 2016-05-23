/*
 
 S Tetromino
 
 Authors : Dhrumil Patel
 
 */

public class S extends Tetromino {
	int curr = 0;
	final int[][][] cord = { {

			{ 0, 0 }, { 1, 0 }, { -1, -1 }, { 0, -1 } }, { { 0, 0 }, { 0, -1 }, { -1, 1 }, { -1, 0 } },
			{ { 0, 0 }, { 1, 0 }, { -1, -1 }, { 0, -1 } }, { { 0, 0 }, { 0, -1 }, { -1, 1 }, { -1, 0 }

			}

	};

	public S() {
		// TODO Auto-generated constructor stub
		
		this.curr = 0;

	}

	@Override
	public boolean rotate() {
		// TODO Auto-generated method stub
		curr = curr + 1 % 4;
		return false;
	}

	/**
	 * @param x number of rotations
	 * @return coordinates of tetromino if rotated x times
	 */
	private int[][] get(int x) {
		// TODO Auto-generated method stub
		int[][] temp = { { 0, 0 }, { 1, 0 }, { -1, 0 }, { -2, 0 } };
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				temp[i][j] = this.cord[(this.curr + x) % 4][i][j];
			}
		}
		return temp;

	}

	@Override
	public int[][] getCurrent() {
		// TODO Auto-generated method stub
		return get(0);
	}

	@Override
	public int[][] getNext() {
		// TODO Auto-generated method stub
		return get(1);
	}

	@Override
	public int[][] getDefault() {
		// TODO Auto-generated method stub
		int[][] temp = { { 0, 0 }, { 1, 0 }, { -1, 0 }, { -2, 0 } };
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				temp[i][j] = this.cord[(0) % 4][i][j];
			}
		}
		return temp;
	}

	@Override
	public void setDefault() {
		// TODO Auto-generated method stub
		this.curr = 0;
		
	}
}

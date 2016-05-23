/*
 
 Base class : Tetromino
 
 Authors : Dhrumil Patel
 
 */

public abstract class Tetromino {
	private Tetromino Tetrominoes[] = new Tetromino[7];
	
	Tetromino(){
		
	}
	Tetromino(String s){
		this.Tetrominoes[0] = new Z();
		this.Tetrominoes[1] = new L();
		this.Tetrominoes[2] = new T();
		this.Tetrominoes[3] = new S();
		this.Tetrominoes[4] = new O();
		this.Tetrominoes[5] = new J();
		this.Tetrominoes[6] = new I();
	}
	/**
	 * @return rotates the tetromino, change coordinates to new rotated coordinates, returns true if rotated
	 */
	public abstract boolean rotate();

	/**
	 * @return returns coordinates to tetromino if rotated
	 */
	public abstract int[][] getNext();

	/**
	 * @return current coordinates of tetromino
	 */
	public abstract int[][] getCurrent();
	/**
	 * @return get the default or starting tetromino coordinates
	 */
	public abstract int[][] getDefault();
	/**
	 *  set tetromino at it's starting position (without rotated)
	 */
	public abstract void setDefault();
	
	public Tetromino[] getList(){
		return this.Tetrominoes;
	}

}

1- First Design pattern used in program is "Composite Design Pattern"

Explanation: In Tetromino Class which is super abstract class for other Tetromino pieces has a list of All the Tetromino's as it's field saved in a Array
code:
	private Tetromino Tetrominoes[] = new Tetromino[7];
	Tetromino(String s){
		this.Tetrominoes[0] = new Z();
		this.Tetrominoes[1] = new L();
		this.Tetrominoes[2] = new T();
		this.Tetrominoes[3] = new S();
		this.Tetrominoes[4] = new O();
		this.Tetrominoes[5] = new J();
		this.Tetrominoes[6] = new I();
	}

 
--------------------------------------------------------------------------------------------

2- Second Pattern used in program is "Singleton Design Pattern"

Explanation: Main method is in TetrisGame.java class and main method make object of that class, as constructor of that class is private so the object was made in class but it doesn't allow making of object of that class outside that class, leaving with only one object made in main class
code:
private TetrisGame() {
	...
	...
}
public static void main(String args[]) {
	TetrisGame app = new TetrisGame();
	...
	...
}
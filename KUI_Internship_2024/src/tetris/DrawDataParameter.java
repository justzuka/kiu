package tetris;

public class DrawDataParameter {
	public int[][] fs;
	public int row;
	public int col;
	public boolean drawBackground;
	public int score;

	public int level;

	public boolean gameOver;

	public DrawDataParameter(int[][] fs, int row, int col, boolean drawBackground, int score, int level, boolean gameOver) {
		this.fs = fs;
		this.row = row;
		this.col = col;
		this.drawBackground = drawBackground;
		this.score = score;
		this.level = level;
		this.gameOver = gameOver;
	}
}
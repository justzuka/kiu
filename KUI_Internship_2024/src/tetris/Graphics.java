package tetris;

public interface Graphics {

	void drawBoxAt(int i, int j, int value);

	void drawString(String text, int x, int y);

	void  drawGameOver();

}

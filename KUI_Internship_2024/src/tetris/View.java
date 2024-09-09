package tetris;

public class View {
	
	static final int BOX_SIZE = 30;
	static final int ORIGIN = 50;
	private Graphics graphics;

	public View(Graphics graphics) {
		this.graphics = graphics;
	}

	public void draw(TetrisModel model) {
		drawData(new DrawDataParameter(model.state.field, 0, 0, true, 0, model.state.level, model.state.gameOver));
		drawData(new DrawDataParameter(model.state.figure, model.state.position.y(), model.state.position.x(), false, model.state.score, model.state.level, model.state.gameOver));
	}

	private void drawData(DrawDataParameter parameterObject) {
		if (parameterObject.gameOver) {
			String gameOverText = "Game Over";
			graphics.drawGameOver();
			return;
		}
		
		for (int r = 0; r < parameterObject.fs.length; r++) {
			for (int c = 0; c < parameterObject.fs[r].length; c++) {
				if (!parameterObject.drawBackground && parameterObject.fs[r][c] == 0)
					continue;
				drawBox(r + parameterObject.row,c + parameterObject.col,parameterObject.fs[r][c]);
			}
		}

		graphics.drawString("Score: " + parameterObject.score, 10, 20);

		graphics.drawString("Level: " + parameterObject.level, 100, 20);
	}

	private void drawBox(int row, int col, int value) {
		graphics.drawBoxAt(ORIGIN + col * BOX_SIZE, ORIGIN + row * BOX_SIZE, value);
	}

}

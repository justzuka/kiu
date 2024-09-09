package tetris;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class TetrisModel implements GameEventsListener {

	public static final int DEFAULT_HEIGHT = 20;
	public static final int DEFAULT_WIDTH = 10;
	public static final int DEFAULT_COLORS_NUMBER = 7;

	int maxColors;
	public TetrisState state = new TetrisState();
	final List<ModelListener> listeners = new ArrayList<>();

	public void addListener(ModelListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ModelListener listener) {
		listeners.remove(listener);
	}

	public TetrisModel(int width, int height, int maxColors) {
		this.state.width = width;
		this.state.height = height;
		this.maxColors = maxColors;
		state.field = new int[height][width];
		initFigure();
	}

	private void initFigure() {
		state.figure = FigureFactory.createNextFigure();
		state.position = new Pair(state.width / 2 - 2, 0);
	}

	public Pair size() {
		return new Pair(state.width, state.height);
	}

	@Override
	public void slideDown() {
		if (state.gameOver) return;
		var newPosition = new Pair(state.position.x(), state.position.y() + 1);
		if (isNewFiguresPositionAndRotationValid(newPosition, state.figure)) {
			state.position = newPosition;
			notifyListeners();
		} else {
			pasteInitAndCheckGameOver();
		}
	}

	private void notifyListeners() {
		listeners.forEach(listener -> listener.onChange(this));
	}

	private void gameOver() {
		state.gameOver = true;
		notifyListeners();

	}

	@Override
	public void moveLeft() {
		var newPosition = new Pair(state.position.x() - 1, state.position.y());
		if (isNewFiguresPositionAndRotationValid(newPosition, state.figure)) {
			state.position = newPosition;
			notifyListeners();
		}
	}

	@Override
	public void moveRight() {
		var newPosition = new Pair(state.position.x() + 1, state.position.y());
		if (isNewFiguresPositionAndRotationValid(newPosition, state.figure)) {
			state.position = newPosition;
			notifyListeners();
		}
	}

	@Override
	public void rotate() {
		int[][] f = new int[4][4];
		for (int r = 0; r < state.figure.length; r++) {
			for (int c = 0; c < state.figure[r].length; c++) {
				f[c][3 - r] = state.figure[r][c];
			}
		}
		if(isNewFiguresPositionAndRotationValid(state.position, f)){
			state.figure = f;
			notifyListeners();
		}
	}

	@Override
	public void drop() {
		var newPosition = new Pair(state.position.x(), state.position.y() + 1);
		while (isNewFiguresPositionAndRotationValid(newPosition, state.figure)) {
			state.position = newPosition;
			newPosition = new Pair(state.position.x(), state.position.y() + 1);
		}

		pasteInitAndCheckGameOver();
	}

	@Override
	public void increaseLevel() {
		state.level += 1;
		notifyListeners();
	}

	@Override
	public void decreaseLevel() {
		state.level -= 1;
		notifyListeners();
	}

	@Override
	public void restartGame() {
		state.gameOver = false;
		state.score = 0;
		state.field = new int[state.height][state.width];

		initFigure();
	}

	private void pasteInitAndCheckGameOver() {
		pasteFigure();
		initFigure();
		checkAndRemoveRows();
		notifyListeners();
		if (!isNewFiguresPositionAndRotationValid(state.position, state.figure)) {
			gameOver();
		}
	}

	public boolean isNewFiguresPositionAndRotationValid(Pair newPosition, int[][] figure) {

		boolean[] result = new boolean[1];
		result[0] = true;

		walkThroughAllFigureCells(newPosition, figure,(absPos, relPos) -> {
			if (result[0]) {
				result[0] = checkAbsPos(absPos);
			}
		});

		return result[0];
	}

	private void walkThroughAllFigureCells(Pair newPosition,int[][] figure, BiConsumer<Pair, Pair> payload) {
		for (int row = 0; row < figure.length; row++) {
			for (int col = 0; col < figure[row].length; col++) {
				if (figure[row][col] == 0)
					continue;
				int absCol = newPosition.x() + col;
				int absRow = newPosition.y() + row;
				payload.accept(new Pair(absCol, absRow), new Pair(col, row));
			}
		}
	}

	private boolean checkAbsPos(Pair absPos) {
		var absCol = absPos.x();
		var absRow = absPos.y();
		if (isColumnPositionOutOfBoundaries(absCol))
			return false;
		if (isRowPositionOutOfBoundaries(absRow))
			return false;
		if (state.field[absRow][absCol] != 0)
			return false;
		return true;
	}

	private boolean isRowPositionOutOfBoundaries(int absRow) {
		return absRow < 0 || absRow >= state.height;
	}

	private boolean isColumnPositionOutOfBoundaries(int absCol) {
		return absCol < 0 || absCol >= state.width;
	}

	public void pasteFigure() {
		walkThroughAllFigureCells(state.position, state.figure,(absPos, relPos) -> {
			state.field[absPos.y()][absPos.x()] = state.figure[relPos.y()][relPos.x()];
		});

	}

	private void checkAndRemoveRows(){
		for(int row = 0; row < state.height; row++){
			if(isRowRemovable(row)){
				removeRow(row);

			}
		}
		notifyListeners();
	}

	private boolean isRowRemovable(int row){
		for(int col = 0; col < state.width; col++) {
			if (state.field[row][col] == 0){
				return false;
			}
		}
		return true;
	}

	private void removeRow(int row){
		int startRow = row - 1;

		while (startRow != -1) {
			for(int col = 0; col < state.width; col++) {
				state.field[startRow + 1][col] = state.field[startRow][col];
			}
			startRow -= 1;
		}

		state.score += 10;
		for(int col = 0; col < state.width; col++) {
			state.field[0][col] = 0;
		}
	}

}

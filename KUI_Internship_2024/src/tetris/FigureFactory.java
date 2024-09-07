package tetris;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FigureFactory {

	public static int[][] createNextFigure(){
		Method[] methods = FigureFactory.class.getDeclaredMethods();
		List<Method> figureMethods = new ArrayList<>();

		for (Method method : methods) {
			if (method.getName().length() == 1 && method.getReturnType().equals(int[][].class)) {
				figureMethods.add(method);
			}
		}

		Random random = new Random();
		int randomIndex = random.nextInt(figureMethods.size());

		try {
			// Invoke the selected method and return the result
			return (int[][]) figureMethods.get(randomIndex).invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static int[][] O() {
		return new int[][] {
			{0, 1, 1, 0},
			{0, 1, 1, 0},
			{0, 0, 0, 0},
			{0, 0, 0, 0},
		};
	}

	static int[][] J() {
		return new int[][] {
			{0, 0, 2, 0},
			{0, 0, 2, 0},
			{0, 2, 2, 0},
			{0, 0, 0, 0},
		};
	}

	static int[][] I() {
		return new int[][] {
				{0, 0, 3, 0},
				{0, 0, 3, 0},
				{0, 0, 3, 0},
				{0, 0, 3, 0},
		};
	}
	static int[][] S() {
		return new int[][] {
				{0, 4, 4, 0},
				{4, 4, 0, 0},
				{0, 0, 0, 0},
				{0, 0, 0, 0},
		};
	}

	static int[][] Z() {
		return new int[][] {
				{0, 5, 5, 0},
				{0, 0, 5, 5},
				{0, 0, 0, 0},
				{0, 0, 0, 0},
		};
	}

	static int[][] L() {
		return new int[][] {
				{0, 6, 0, 0},
				{0, 6, 0, 0},
				{0, 6, 6, 0},
				{0, 0, 0, 0},
		};
	}

	static int[][] T() {
		return new int[][] {
				{7, 7, 7, 0},
				{0, 7, 0, 0},
				{0, 0, 0, 0},
				{0, 0, 0, 0},
		};
	}

	static int[][] rotatedJ() {
		return new int[][] {
			{0, 0, 0, 0},
			{0, 2, 0, 0},
			{0, 2, 2, 2},
			{0, 0, 0, 0},
		};
	}

	static int[][] rotatedI() {
		return new int[][] {
				{0, 0, 0, 0},
				{0, 0, 0, 0},
				{3, 3, 3, 3},
				{0, 0, 0, 0},
		};
	}
	
}

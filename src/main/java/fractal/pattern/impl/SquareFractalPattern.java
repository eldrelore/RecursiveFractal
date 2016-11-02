package fractal.pattern.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import fractal.dto.Line;
import fractal.pattern.intf.FractalPattern;

public class SquareFractalPattern implements FractalPattern {
	private static final Random random = new Random();

	private static final String SQUARE_PATTERN_NAME = "Square";

	@Override
	public String getPatternName() {
		return SQUARE_PATTERN_NAME;
	}

	@Override
	public boolean displayPointData() {
		return false;
	};

	@Override
	public List<Line> defineBaseShape(int size) {
		/* define a basic square in lines */
		Line leftLine = new Line(0, 0, 0, size);
		Line topLine = new Line(0, size, size, size);
		Line rightLine = new Line(size, size, size, 0);
		Line bottomLine = new Line(size, 0, 0, 0);

		/* add lines to list */
		List<Line> lines = new ArrayList<Line>();
		lines.add(topLine);
		lines.add(leftLine);
		lines.add(bottomLine);
		lines.add(rightLine);
		return lines;
	}

	@Override
	public Function<Line, List<Line>> getPatternFunction() {
		return (line) -> {
			int lineLength = determineLineLength(line);
			List<Line> lineList = new ArrayList<Line>();
			/*
			 * add 2 lines at the negative inverse slope on either side of this
			 * one (start / end point change)
			 */
			double slope = line.findSlope();
			int firstLineEndX = 0;
			int firstLineEndY = 0;
			int thirdLineStartX = 0;
			int thirdLineStartY = 0;

			/*
			 * Slope for this approach will always be ether 0, or near infinity
			 * (horizontal or vertical lines)
			 * 
			 * uses randomness to determine whether to prodtrude or recess.
			 */
			if (0 == slope) {
				firstLineEndX = line.getX1();
				if (line.getX1() < line.getX2()) {
					int randomInOrOut = getRandomConcaveOrConvex();
					firstLineEndY = line.getY1() + (lineLength * randomInOrOut);
					thirdLineStartY = line.getY2() + (lineLength * randomInOrOut);
				} else {
					int randomInOrOut = getRandomConcaveOrConvex();
					firstLineEndY = line.getY1() - (lineLength * randomInOrOut);
					thirdLineStartY = line.getY2() - (lineLength * randomInOrOut);
				}
				thirdLineStartX = line.getX2();

			} else if (999999.9 == slope) {
				if (line.getY1() < line.getY2()) {
					int randomInOrOut = getRandomConcaveOrConvex();
					firstLineEndX = line.getX1() - (lineLength * randomInOrOut);
					thirdLineStartX = line.getX2() - (lineLength * randomInOrOut);
				} else {
					int randomInOrOut = getRandomConcaveOrConvex();
					firstLineEndX = line.getX1() + (lineLength * randomInOrOut);
					thirdLineStartX = line.getX2() + (lineLength * randomInOrOut);
				}
				firstLineEndY = line.getY1();
				thirdLineStartY = line.getY2();
			}
			/* get the three lines created out of this line */
			Line firstLine = new Line(line.getX1(), line.getY1(), firstLineEndX, firstLineEndY);
			Line secondLine = new Line(firstLineEndX, firstLineEndY, thirdLineStartX, thirdLineStartY);
			Line thirdLine = new Line(thirdLineStartX, thirdLineStartY, line.getX2(), line.getY2());

			lineList.add(firstLine);
			lineList.add(secondLine);
			lineList.add(thirdLine);
			return lineList;
		};
	}

	/* use pseudo-randomness to determine whether to protrude or recess */
	private int getRandomConcaveOrConvex() {
		return random.nextInt(2) > 0 ? 1 : -1;
	}
}
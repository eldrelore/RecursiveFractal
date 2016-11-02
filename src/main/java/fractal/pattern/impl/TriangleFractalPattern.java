package fractal.pattern.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import fractal.dto.Line;
import fractal.pattern.intf.FractalPattern;

public class TriangleFractalPattern implements FractalPattern {
	private static final Random random = new Random();

	private static final String PATTERN_NAME = "Triangle";
	private static final double SIXTY_DEGREES_IN_RADIANS = Math.toRadians(60);

	@Override
	public boolean displayPointData() {
		return true;
	};

	@Override
	public String getPatternName() {
		return PATTERN_NAME;
	}

	@Override
	public List<Line> defineBaseShape(int size) {
		/* define a basic square in lines */
		Long topXD = Math.round(((Double) (Math.cos(SIXTY_DEGREES_IN_RADIANS) * size)));
		int topX = topXD.intValue();
		/* randomize top or bottom point */
		Long topYD = Math.round(((Double) (Math.sin(SIXTY_DEGREES_IN_RADIANS) * size))) * getRandomConcaveOrConvex();
		int topY = topYD.intValue();
		Line leftLine = new Line(0, 0, topX, topY);
		Line rightLine = new Line(topX, topY, 2 * topX, 0);
		Line bottomLine = new Line(2 * topX, 0, 0, 0);

		/* add lines to list */
		List<Line> lines = new ArrayList<Line>();
		lines.add(leftLine);
		lines.add(bottomLine);
		lines.add(rightLine);
		return lines;
	}

	@Override
	public Function<Line, List<Line>> getPatternFunction() {
		return (line) -> {
			int lineLength = determineLineLength(line);
			// int lineLength = determineLineLength(line);
			List<Line> lineList = new ArrayList<Line>();

			/*
			 * add 2 lines at the negative inverse slope on either side of this
			 * one (start / end point change)
			 */
			double slope = line.findSlope();

			/* go off at 60 degree angles from the current slope */
			double slopeInRadians = Math.atan(slope);
			/* get updated slope in radians */
			/* TODO: this section has some bugs */
			/* TODO: back to the precalc textbook, this isn't... quite... right.*/
			double slopePlusOrMinusSixyDegreesInRadians = slopeInRadians + (SIXTY_DEGREES_IN_RADIANS * getRandomConcaveOrConvex());
			double slopePlusSixty = slopeInRadians + (2 * SIXTY_DEGREES_IN_RADIANS);
			Long newSlope = Math.round(Math.tan(slopePlusOrMinusSixyDegreesInRadians));
			int firstLineEndX = 0;
			int firstLineEndY = 0;
			/* handle flat and upright slopes */
			if (0.0 == newSlope || -0.0 == newSlope) {
				firstLineEndY = line.getY1();
				firstLineEndX = line.getX1() + (lineLength);
			} else if (newSlope > 99999 || newSlope < -99999) {
				firstLineEndX = line.getX1();
				firstLineEndY = line.getY1() + (lineLength);
			} else {
				/* handle all other slopes */
				int xLength = ((Double) (Math.cos(slopePlusSixty) * lineLength)).intValue();
				int yLength = ((Double) (Math.sin(slopePlusOrMinusSixyDegreesInRadians) * lineLength)).intValue();
				if (line.getX1() < line.getX2()) {
					firstLineEndX = line.getX1() + xLength / 2;
				} else {
					firstLineEndX = line.getX1() + xLength;
				}
				if (line.getY1() < line.getY2()) {
					firstLineEndY = line.getY1() + yLength;
				} else {
					firstLineEndY = line.getY1() + yLength;
				}
			}
			/* get the two lines created out of this line */
			Line firstLine = new Line(line.getX1(), line.getY1(), firstLineEndX, firstLineEndY);
			Line secondLine = new Line(firstLineEndX, firstLineEndY, line.getX2(), line.getY2());
			lineList.add(firstLine);
			lineList.add(secondLine);
			return lineList;
		};
	}

	/* use pseudo-randomness to determine whether to protrude or recess */
	private int getRandomConcaveOrConvex() {
		return random.nextInt(2) > 0 ? 1 : -1;
	}
}
package fractal.pattern.intf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import fractal.dto.Line;

public abstract interface FractalPattern {

	boolean displayPointData();

	/**
	 * retrieve name of fractal pattern used
	 * 
	 * @return String
	 */
	String getPatternName();

	/**
	 * Define the basic shape, for a square, define 4 sides of identical length,
	 * for a triangle 3 sides of equal length, etc.
	 * 
	 * TODO: will need to do some refactoring to handle circle / arc
	 * 
	 * @return List<Line>
	 */
	List<Line> defineBaseShape(int size);

	/**
	 * Define the pattern that will turn this into a fractal.
	 * 
	 * For example, in a square, turn each side into 3 pieces, for the middle
	 * segment, create a smaller square of that edge size and either insert it
	 * into, or protrude it out from the provided line.
	 * 
	 * The same holds true for a triangle.
	 * 
	 * @return Function<Line, List<Line>>
	 */
	Function<Line, List<Line>> getPatternFunction();

	default int getDefaultDivisor() {
		return 3;
	}

	/**
	 * How to break up the line into segments for use with the function from
	 * getPatternFunction.
	 * 
	 * @param line
	 * @return List<Line>
	 */
	default List<Line> getSubLines(Line line) {
		/*
		 * break original line into thirds, leave first and third thirds the
		 * same
		 */
		List<Line> splitLine = new ArrayList<Line>();
		/*
		 * split line into first, and third segments.
		 * 
		 * Both of which should be left as in the original line. It's the middle
		 * section we're concerned with.
		 * 
		 * May need to change this to arcs (or a superclass of lines and arcs)
		 * for circles?
		 */

		/*
		 * may eventually want to break things up into pieces by numbers other
		 * than 3. Not sure we want to worry about that at the moment.
		 */
		Line firstSplitLine = new Line(line.getX1(), line.getY1(), line.getX1() + ((line.getX2() - line.getX1()) / 3), line.getY1()
				+ ((line.getY2() - line.getY1()) / 3));

		Line thirdSplitLine = new Line(line.getX2() - ((line.getX2() - line.getX1()) / 3), line.getY2()
				- ((line.getY2() - line.getY1()) / 3), line.getX2(), line.getY2());
		/*
		 * cut out the middle, and apply the function to it.
		 */
		Line secondSplitLine = new Line(line.getX1() + ((line.getX2() - line.getX1()) / 3), line.getY1()
				+ ((line.getY2() - line.getY1()) / 3), line.getX2() - ((line.getX2() - line.getX1()) / 3), line.getY2()
				- ((line.getY2() - line.getY1()) / 3));
		splitLine.add(firstSplitLine);
		splitLine.addAll(getPatternFunction().apply(secondSplitLine));
		splitLine.add(thirdSplitLine);
		return splitLine;
	}

	/**
	 * determine whether the current line is viable for further fractals
	 * 
	 * @param line
	 * @param distance
	 * @return boolean
	 */
	default boolean isLineFractable(Line line, int distance) {
		int lineLength = determineLineLength(line);
		boolean createFractal = lineLength >= distance;
		return createFractal;
	}

	/**
	 * determine length of line
	 * 
	 * @param line
	 * @return int
	 */
	default int determineLineLength(Line line) {
		double xSquared = Math.pow((line.getX1() - line.getX2()), 2);
		double ySquared = Math.pow((line.getY1() - line.getY2()), 2);
		Double lineLength = Math.sqrt(xSquared + ySquared);
		return lineLength.intValue();
	}
}

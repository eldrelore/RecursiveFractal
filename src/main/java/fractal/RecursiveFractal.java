package fractal;

import java.util.ArrayList;
import java.util.List;

import fractal.dto.Line;
import fractal.pattern.intf.FractalPattern;

public class RecursiveFractal {
	/**
	 * recursively create the appropriate fractal sub-lines for this line
	 * 
	 * @param lines
	 * @param distance
	 * @param protrude
	 * @param levelsToGo
	 * @param pattern
	 * @return List<Line>
	 */
	public List<Line> createFractalSubLines(List<Line> lines, int distance, int levelsToGo, FractalPattern pattern) {
		List<Line> nextLevelLines = new ArrayList<Line>();
		if (0 < levelsToGo) {
			/*
			 * trace the line that makes up the shape. If there's a section of
			 * the shape that's straight (or curved if circle) for distance 3d,
			 * then based on protrude either add the shape to the base shape, or
			 * subtract it from the base shape
			 */
			for (Line line : lines) {
				/*
				 * If the line is fractable than update break the line down into
				 * fractal lines.
				 * 
				 * otherwise just add this to the list of lines to go to the
				 * next level down.
				 */
				if (pattern.isLineFractable(line, distance)) {
					nextLevelLines.addAll(pattern.getSubLines(line));
				} else {
					/* check and see if this is even called. */
					nextLevelLines.add(line);
				}
			}
			List<Line> resultLines = createFractalSubLines(nextLevelLines, distance / pattern.getDefaultDivisor(), levelsToGo - 1, pattern);
			if (!resultLines.isEmpty()) {
				nextLevelLines = resultLines;
			}
		}
		return nextLevelLines;
	}
}
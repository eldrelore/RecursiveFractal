package fractal;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import fractal.draw.Frame;
import fractal.dto.Line;
import fractal.pattern.impl.IdenticalLineFractalPattern;
import fractal.pattern.impl.SquareFractalPattern;
import fractal.pattern.impl.TriangleFractalPattern;
import fractal.pattern.intf.FractalPattern;

public class TestFractal extends TestCase {

	@Test
	public void testSquareFractal() {
		int frameSize = 1000;
		int shapeSize = 300;
		int fractalDistance = 300;
		FractalPattern squarePattern = new SquareFractalPattern();
		List<Line> lines = squarePattern.defineBaseShape(shapeSize);

		RecursiveFractal basicFractal = new RecursiveFractal();
		List<Line> fractalLines = basicFractal.createFractalSubLines(lines, fractalDistance, 6, squarePattern);
		/* Display drawn lines in a frame */
		new Frame(frameSize, frameSize, shapeSize, shapeSize, fractalLines, squarePattern.getPatternName(),
				squarePattern.displayPointData());
		try {
			while (true) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		/* visual inspection */
	}

	@Test
	public void testTriangleFractal() {
		int frameSize = 1000;
		int shapeSize = 300;
		int fractalDistance = 100;
		FractalPattern trianglePattern = new TriangleFractalPattern();
		List<Line> lines = trianglePattern.defineBaseShape(shapeSize);

		RecursiveFractal basicFractal = new RecursiveFractal();
		List<Line> fractalLines = basicFractal.createFractalSubLines(lines, fractalDistance, 4, trianglePattern);
		/* display drawn lines in a window */
		new Frame(frameSize, frameSize, shapeSize, shapeSize, fractalLines, trianglePattern.getPatternName(),
				trianglePattern.displayPointData());
		try {
			while (true) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/* visual inspection */
	}

	@Test
	public void testBasicDrawable() {
		int frameSize = 500;
		int shapeSize = 500;
		Line line = new Line(0, 0, 500, 500);
		List<Line> lines = new ArrayList<Line>();
		lines.add(line);
		new Frame(frameSize, frameSize, shapeSize, shapeSize, lines, "Basic", false);
		try {
			while (true) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/* visual inspection of drawing */
	}

	@Test
	public void testTrianglePatternFunction() {
		/*
		 * had some odd issues with one particular leg of triangle, this allowed
		 * me to troubleshoot it.
		 */
		Line line = new Line(500, 90, 650, 350);
		int frameSize = 600;
		int shapeSize = 300;
		FractalPattern trianglePattern = new TriangleFractalPattern();
		List<Line> fractalLines = trianglePattern.getSubLines(line);
		/* display drawn lines in a window */
		new Frame(frameSize, frameSize, shapeSize, shapeSize, fractalLines, trianglePattern.getPatternName(),
				trianglePattern.displayPointData());
		try {
			while (true) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/* visual inspection */
	}

	@Test
	public void testBasicLineFractal() {
		int size = 500;
		FractalPattern basicLinePattern = new IdenticalLineFractalPattern();
		List<Line> lines = basicLinePattern.defineBaseShape(size);

		RecursiveFractal basicFractal = new RecursiveFractal();
		List<Line> fractalLines = basicFractal.createFractalSubLines(lines, size, 1, basicLinePattern);

		assertTrue(3 == fractalLines.size());
		Line firstSegment = fractalLines.get(0);
		Line secondSegment = fractalLines.get(1);
		Line thirdSegment = fractalLines.get(2);
		assertTrue(0 == firstSegment.getX1() && 0 == firstSegment.getY1() && 166 == firstSegment.getX2() && 166 == firstSegment.getY2());
		assertTrue(166 == secondSegment.getX1() && 166 == secondSegment.getY1() && 334 == secondSegment.getX2()
				&& 334 == secondSegment.getY2());
		assertTrue(334 == thirdSegment.getX1() && 334 == thirdSegment.getY1() && 500 == thirdSegment.getX2() && 500 == thirdSegment.getY2());
	}
}
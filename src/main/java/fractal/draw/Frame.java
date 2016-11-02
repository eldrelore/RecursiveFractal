package fractal.draw;

import java.util.List;

import javax.swing.JFrame;

import fractal.dto.Line;

public class Frame {

	public Frame(int frameHeight, int frameWidth, int shapeHeight, int shapeWidth, List<Line> lines, String patternName,
			boolean displayPointData) {
		/* JFrame to hold drawings */
		JFrame frame = new JFrame();
		frame.setTitle(patternName + " Fractal");
		frame.setSize(frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Do some geometry to center the graphic. Yes, this is going to be off
		 * by the initial length & width of the surface.
		 */

		/*
		 * TODO:May eventually pass in the size (width and height) of the
		 * fractal, to center it correctly.
		 */
		for (Line line : lines) {
			if (displayPointData) {
				System.out.println("was: " + line.getX1() + " " + line.getY1() + " " + line.getX2() + " " + line.getY2());
			}
			int centerWidthAdjustment = (frameWidth / 2) - (shapeWidth / 2);
			int centerHeightAdjustment = (frameHeight / 2) - (shapeHeight / 2);
			line.setX1(line.getX1() + centerWidthAdjustment);
			line.setX2(line.getX2() + centerWidthAdjustment);
			line.setY1(line.getY1() + centerHeightAdjustment);
			line.setY2(line.getY2() + centerHeightAdjustment);
			if (displayPointData) {
				System.out.println(" is now: " + line.getX1() + " " + line.getY1() + " " + line.getX2() + " " + line.getY2());
			}
		}

		/* Create drawable */
		Drawable drawable = new Drawable();
		drawable.setDrawableLines(lines, displayPointData);

		/* add drawable to the frame as content panel */
		frame.getContentPane().add(drawable);

		/* Make frame visible */
		frame.setVisible(true);
	}
}

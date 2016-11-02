package fractal.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import fractal.dto.Line;

public class Drawable extends JComponent {
	/**
	 * guid
	 */
	private static final long serialVersionUID = -6542954835542457560L;
	private List<Line> lines = new ArrayList<Line>();
	private boolean displayPointData = false;

	public void setDrawableLines(List<Line> lines, boolean displayPointData) {
		this.lines = lines;
		this.displayPointData = displayPointData;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw entire component white
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		for (Line line : lines) {
			/* useful for seeing coordinates at points */
			if (displayPointData) {
				g.drawString(line.getX1() + ", " + line.getY1(), line.getX1(), line.getY1());
			}
			g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
		}
	}
}

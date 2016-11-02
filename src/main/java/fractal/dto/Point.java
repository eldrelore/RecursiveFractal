package fractal.dto;

import java.awt.geom.Point2D;

public class Point extends Point2D {
	private double x = 0.0;
	private double y = 0.0;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
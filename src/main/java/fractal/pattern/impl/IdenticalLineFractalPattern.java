package fractal.pattern.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import fractal.dto.Line;
import fractal.pattern.intf.FractalPattern;

public class IdenticalLineFractalPattern implements FractalPattern {
	private static final String IDENTICAL_LINE = "Identical Line";

	@Override
	public List<Line> defineBaseShape(int size) {
		Line line = new Line(0, 0, size, size);
		List<Line> lines = new ArrayList<Line>();
		lines.add(line);
		return lines;
	}

	@Override
	public Function<Line, List<Line>> getPatternFunction() {
		return (line) -> {
			List<Line> lineList = new ArrayList<Line>();
			lineList.add(line);
			return lineList;
		};
	}

	@Override
	public String getPatternName() {
		return IDENTICAL_LINE;
	}

	@Override
	public boolean displayPointData() {
		return false;
	};
}
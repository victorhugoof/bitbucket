package model;

import javafx.scene.paint.Color;

public class Pixels {
	private Double red;
	private Double green;
	private Double blue;
	private Integer x;
	private Integer y;
	private Integer count;
	
	public Pixels() {
		super();
		this.red = 0D;
		this.green = 0D;
		this.blue = 0D;
		this.x = 0;
		this.y = 0;
		this.count = 0;
	}

	public Pixels(Double red, Double green, Double blue) {
		this();
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.count++;
	}
	
	public Pixels(Double red, Double green, Double blue, Integer x, Integer y) {
		this();
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.x = x;
		this.y = y;
	}
	
	public Color getColor(double opacity) {
		return new Color((this.red / this.count), (this.green / this.count), (this.blue / this.count), opacity);
	}
	
	public void setColor(Color cor) {
		this.count++;
		this.red += cor.getRed();
		this.green += cor.getGreen();
		this.blue += cor.getBlue();
	}

	public Double getRed() {
		return red;
	}

	public Double getGreen() {
		return green;
	}

	public Double getBlue() {
		return blue;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public Integer getCount() {
		return count;
	}

}

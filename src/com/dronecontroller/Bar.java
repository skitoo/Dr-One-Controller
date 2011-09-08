package com.dronecontroller;

import java.awt.Point;

import processing.core.PApplet;

public class Bar
{
	private PApplet parent;
	private DrOneData data;
	private Point position;
	private int height;
	private int width;
	private String orientation;

	public Bar(PApplet parent, DrOneData data, int x, int y, int width, int height, String orientation)
	{
		this.parent = parent;
		this.data = data;
		this.position = new Point(x, y);
		this.width = width;
		this.height = height;
		this.orientation = orientation;
	}
	
	public void draw()
	{
		parent.stroke(255);
		parent.fill(0x024C68);
		parent.rect(position.x, position.y, width, height);
	}
}

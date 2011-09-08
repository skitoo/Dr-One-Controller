package com.dronecontroller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import processing.core.PApplet;

public class Graph
{
	private PApplet parent;
	private Collection<DrOneData> datas;

	private static final int WIDTH = 780;
	private static final int HEIGHT = 200;
	private static final float HALF_HEIGHT = HEIGHT / 2;
	private static final int X = 10;
	private static final int Y = 10;
	
	private float pX, pY;

	public Graph(PApplet parent)
	{
		this.parent = parent;
		this.datas = new ArrayList<DrOneData>();
	}
	
	public void addData(DrOneData data)
	{
		datas.add(data);
	}

	public void draw()
	{
		// draw the rectangle
		parent.stroke(255);
		parent.fill(0);
		parent.rect(X, Y, WIDTH, HEIGHT);

		// draw datas
		Iterator<DrOneData> iterator = datas.iterator();
		while (iterator.hasNext())
		{
			drawData(iterator.next());
		}

		// draw the point line
		parent.stroke(255);
		for (int i = 0; i < WIDTH; i += 3)
		{
			parent.point(X + i, Y + HALF_HEIGHT);
		}
	}

	private void drawData(DrOneData data)
	{
		parent.stroke(data.getColor().getRed(), data.getColor().getGreen(), data.getColor().getBlue());
		float pX = X;
		float pY = HALF_HEIGHT + Y;
		for (int j = X * 2; j < data.values.length - 1; j++)
		{
			parent.line(pX, pY, j + X, HALF_HEIGHT + Y + data.values[j]);
			pX = j + X;
			pY = HALF_HEIGHT + Y + data.values[j];
		}
	}
}

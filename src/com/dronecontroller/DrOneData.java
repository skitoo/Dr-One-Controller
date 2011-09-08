package com.dronecontroller;


import java.awt.Color;

public class DrOneData
{
	private String name;
	private Color color;
	public float[] values;
	public int x, y;
	public boolean degree;
	
	public DrOneData(String name, Color color, int x, int y, boolean degree)
	{
		this.name = name;
		this.color = color;
		this.x = x;
		this.y = y;
		this.degree = degree;
		values = new float[800];
	}
	
	public void next()
	{
		for (int j = 0; j < 800 - 1; j++)
		{
			values[j] = values[j + 1];
		}
	}
	
	public Color getColor()
	{
		return color;
	}

	public String getName()
	{
		return name;
	}
}

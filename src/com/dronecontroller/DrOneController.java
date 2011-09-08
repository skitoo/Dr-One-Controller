package com.dronecontroller;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PFont;
import processing.serial.Serial;

@SuppressWarnings("serial")
public class DrOneController extends PApplet
{
	private Serial serial;
	private HashMap<String, DrOneData> drOneDatas;
	public boolean started;
	private PFont font;
	private Graph graph;

	public void setup()
	{
		size(800, 600);
		frameRate(120);
		smooth();

		font = loadFont("ArialMT-20.vlw");
		textFont(font, 12);

		started = false;

		drOneDatas = new HashMap<String, DrOneData>();

		drOneDatas.put("ACCELEROMTER_X", new DrOneData("ACCELEROMTER_X", new Color(255, 0, 0), 20, 500, false));
		drOneDatas.put("ACCELEROMTER_Y", new DrOneData("ACCELEROMTER_Y", new Color(0, 255, 0), 250, 500, false));
		
		drOneDatas.put("GYRO_Roll", new DrOneData("GYRO_Roll", new Color(0, 0, 255), 20, 550, true));
		drOneDatas.put("GYRO_Pitch", new DrOneData("GYRO_Pitch", new Color(0, 255, 255), 150, 550, true));
		drOneDatas.put("GYRO_Yaw", new DrOneData("GYRO_Yaw", new Color(255, 255, 0), 280, 550, true));
		
		drOneDatas.put("KRoll", new DrOneData("KRoll", new Color(255, 255, 0), 20, 580, false));
		drOneDatas.put("KPitch", new DrOneData("KPitch", new Color(0, 255, 255), 150, 580, false));
		drOneDatas.put("RYaw", new DrOneData("RYaw", new Color(255, 0, 255), 280, 580, false));

		graph = new Graph(this);
		graph.addData(drOneDatas.get("ACCELEROMTER_X"));
		graph.addData(drOneDatas.get("ACCELEROMTER_Y"));

		serial = new Serial(this, Serial.list()[0], 9600);
		serial.bufferUntil(10);
	}

	public void draw()
	{
		background(0);
		// draw datas
		Iterator<DrOneData> iterator = drOneDatas.values().iterator();
		while (iterator.hasNext())
		{
			displayValues(iterator.next());
		}

		graph.draw();
		
		/*
		if (started)
		{
			Iterator<DrOneData> iterator = drOneDatas.values().iterator();
			while (iterator.hasNext())
			{
				displayData(iterator.next());
			}
			iterator = drOneDatas.values().iterator();

			while (iterator.hasNext())
			{
				displayValues(iterator.next());
			}

		}
		
		stroke(255, 255, 255);
		for (int i = 0; i < 800; i += 3)
		{
			point(i, 300);
		}*/
	}

	private void displayData(DrOneData data)
	{
		stroke(data.getColor().getRed(), data.getColor().getGreen(), data
				.getColor().getBlue());
		float pX = 0;
		float pY = 300;
		for (int j = 0; j < 800 - 1; j++)
		{
			line(pX, pY, j, 300 + data.values[j]);
			pX = j;
			pY = 300 + data.values[j];
		}
	}

	private void displayValues(DrOneData data)
	{
		fill(data.getColor().getRed(), data.getColor().getGreen(), data
				.getColor().getBlue());
		text(data.getName() + " : " + data.values[800 - 1], data.x, data.y);
	}

	public void serialEvent(Serial p)
	{
		started = true;
		String data = p.readString();
		String[] datas = data.split(" ");
		for (int i = 0; i < datas.length; i++)
		{
			String[] keyValue = datas[i].split(":");
			String key = keyValue[0];
			DrOneData drOneData = getData(key);
			if (drOneData != null)
			{
				drOneData.next();
				if (drOneData.degree)
				{
					drOneData.values[800 - 1] = Float.parseFloat(keyValue[1]);
				} 
				else
				{
					drOneData.values[800 - 1] = rad2deg(Float.parseFloat(keyValue[1]));
				}
			}
		}
	}

	private DrOneData getData(String key)
	{
		if (drOneDatas.containsKey(key))
		{
			return drOneDatas.get(key);
		} 
		else
		{
			return null;
		}
	}

	float rad2deg(float rad)
	{
		return (float) (rad * 57.2957795);
	}
}

package de.feu.propra15.q8938938.math;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.feu.propra15.q8938938.Helper.SimplePoint;
import de.feu.propra15.q8938938.Main.DrawingPanel;

public class HullCalculator implements IHullCalculator 
{
	DrawingPanel _panel;
	private ConvexHull _curConvexHull;
	
	/**
	 * Instantiates a new DrawingPanel(needed if the program is started in testmode)
	 */
	public HullCalculator()
	{
		_panel = new DrawingPanel(this);
	}
	
	/**
	 * @param panel Set the panel the HullCalculator draws to
	 */
	public void setPanel(DrawingPanel panel)
	{
		_panel = panel;
	}
	
	public void addPoint(int x, int y)
	{
		_panel.addPoints(new SimplePoint(x, y));
		
	}
	
	
	public void addPointsFromArray(int[][] pointArray)
	{
		ArrayList<SimplePoint> store = new ArrayList<SimplePoint>();
		for(int i = 0; i < pointArray.length;i++)
		{
			store.add(new SimplePoint(pointArray[i][0], pointArray[i][1]));
			
		}
		_panel.addPoints(store);
	}

	public void addPointsFromFile(String fileName) throws IOException
	{
		try
		{
			ArrayList<SimplePoint> store = new ArrayList<SimplePoint>();
			BufferedReader in = new BufferedReader(new FileReader(
					fileName)); 
			
			// Create new Regex which searches for Integers
			String regex = "^-?[0-9]+ +-?[0-9]+ ?";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher;
			
			long x;
			long y;
			String zeile = null;
			
			// Search for 2 Ints and add them to the point list
			while ((zeile = in.readLine()) != null)
			{
				matcher = pattern.matcher(zeile);
				

				if (matcher.find())
				{
					String[] splitted = matcher.group().split("\\s");
					x = Long.parseLong(splitted[0]);
					y = Long.parseLong(splitted[1]);
					store.add(new SimplePoint(x, y));
					
				}

				

			}

			
			in.close();
			_panel.addPoints(store);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	
		
	}

	public void clear()
	{
			_panel.clear();	
	}
	

	public int[][] getConvexHull()
	{
		
		_curConvexHull = new ConvexHull(_panel);
		
		
		
		return _curConvexHull.getCHPoints();
	}
	
	public void drawAngleHull()
	{
		AngleHull ah = new AngleHull(_panel,_curConvexHull);
	}

	public String getEmail()
	{

		return "nreusch@web.de";
	}
	
	public String getMatrNr()
	{

		return "8938938";
	}

	public String getName()
	{

		return "Niklas Reusch";
	}
	


}

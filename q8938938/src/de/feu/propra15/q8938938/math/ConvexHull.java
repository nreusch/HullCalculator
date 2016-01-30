package de.feu.propra15.q8938938.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.feu.propra15.q8938938.Helper.SimplePoint;
import de.feu.propra15.q8938938.Main.DrawingPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class ConvexHull.
 *
 * @author Niklas Reusch
 */
public class ConvexHull
{
	
	/** The PointList of the ConvexHull. Is ordered clockwise from turning point L to L.(last L not included) */
	private List<SimplePoint> ch_points = new ArrayList<SimplePoint>();
	
	// KonturPolygon privates
	/** Used to find new points for the KonturPolygon. */
	private SimplePoint P_miny, P_maxy;
	
	/** The turning points. */
	private SimplePoint L,O,R,U;
	
		// The 4 areas of the KonturPolygon
	/** The kp_points_lo. */
	private List<SimplePoint> kp_points_lo = new ArrayList<SimplePoint>();  
	
	/** The kp_points_ro. */
	private List<SimplePoint> kp_points_ro = new ArrayList<SimplePoint>();  
	
	/** The kp_points_ru. */
	private List<SimplePoint> kp_points_ru = new ArrayList<SimplePoint>();  
	
	/** The kp_points_lu. */
	private List<SimplePoint> kp_points_lu = new ArrayList<SimplePoint>();
	
	/**
	 * Calculates a new convex hull.
	 *
	 * @param panel the panel to draw to
	 */
	public ConvexHull(DrawingPanel panel)
	{
		clearAllLists(panel);
		calculateKonturPolygon(panel); // fills kp_points
		getConvexHull();
		drawConvexHull(panel);
	}
	
	/**
	 * Clears all pointlists. Should be called before calculation.
	 *
	 * @param panel the panel
	 */
	private void clearAllLists(DrawingPanel panel)
	{
		ch_points.clear();
		kp_points_lo.clear();
		kp_points_lu.clear();
		kp_points_ru.clear();
		kp_points_ro.clear();
	}
	
	
	
	/**
	 * 
	 * @return Returns the ConvexHull Points in an int-Array clockwise. arr[i][0] is the x-Coordinate arr[i][1] the y-Coordinate.
	 */
	public int[][] getCHPoints()
	{
		int[][] ch_array = new int[ch_points.size()][2]; 
		int i = 0;
		
		
		for (SimplePoint simplePoint : ch_points)
		{

			ch_array[i][0] = (int) (simplePoint.getX());
			ch_array[i][1] = (int) (simplePoint.getY());
			i++;
		}
			
		return ch_array;
	}
	
	/**
	 * 
	 * @return Returns the ConvexHull Points in an int-Array counterclockwise. arr[i][0] is the x-Coordinate arr[i][1] the y-Coordinate.
	 */
	
	public List<SimplePoint> getCHPointsAL()
	{
		
		return ch_points;
	}
	// KonturPolygon

	/**
	 * Calculates the KonturPolygon.
	 *
	 * @param panel the panel to get the Points
	 */
	private void calculateKonturPolygon(DrawingPanel panel)
	{
		
		
		List<SimplePoint> sortedPoints = sortS(panel.getPoints());
		sweepToRight(panel, sortedPoints); // adds L,O,U and other Points
		sweepToLeft(panel, sortedPoints); // adds R and other Points
		
	}

	/**
	 * Sorts a given Points list by ascending x-Coordinates, by equaltiy sorts by y-Coordinate
	 *
	 * @param unsortedPoints the unsorted pointlist
	 * @return the sorted pointlist
	 */
	private List<SimplePoint> sortS(List<SimplePoint> unsortedPoints) {
		Collections.sort(unsortedPoints);
		return unsortedPoints; //now sorted (implementing compareTo)

	}
	
	/**
	 * Sweep to right.
	 * Determines the turning points L,U,O and the left side of the KonturPolygon-Points
	 * @param panel the panel
	 * @param sortedS the sorted pointlist
	 */
	private void sweepToRight(DrawingPanel panel, List<SimplePoint> sortedS) {
		try{
		// Am Start ist Punkt miny=maxy=L
		P_miny = sortedS.get(0); P_maxy = P_miny;
		L = sortedS.get(0); kp_points_lo.add(L); kp_points_lu.add(L); // add L to LO and LU (start and end point)
		
		for (SimplePoint sortedPoint : sortedS) {
			
				if(sortedPoint.getY() > P_maxy.getY())
				{
					
				 
					P_maxy = sortedPoint;
					kp_points_lu.add(P_maxy);
				}
				else if(sortedPoint.getY() <= P_miny.getY() && !sortedPoint.equals(L)) // O ist immer ganz rechts auf einer waagerechten linie,L ist immer ganz links
				{
				
					P_miny = sortedPoint;
					kp_points_lo.add(P_miny);
				}
			
		}
		// at the End: (Top Left is 0,0)
		//U = P_maxy; 
		O = P_miny; 
		}
		catch(Exception exc)
		{}
	}
	
	/**
	 * Sweep to left.
	 * Determines the turning point R and the right side of the KonturPolygon-Points
	 * @param panel the panel
	 * @param sortedS the sorted pointlist
	 */
	private void sweepToLeft(DrawingPanel panel, List<SimplePoint> sortedS) {
		try{
			// Am Start ist Punkt miny=maxy=R

			
			P_miny = sortedS.get(sortedS.size()-1); P_maxy = P_miny;
			R = sortedS.get(sortedS.size()-1);
			if(!R.equals(L))
			{
				kp_points_ro.add(R); // add R to RO and RU
				kp_points_ru.add(R);
			}
			SimplePoint sortedPoint;
			for (int i = sortedS.size()-1;i > -1;i--) {
				sortedPoint = sortedS.get(i);
				
					if(sortedPoint.getY() >= P_maxy.getY() && !sortedPoint.equals(R))  // U ist immer ganz links auf einer waagerechten linie mit R, R ist ganz rechts
					{
	
						
						P_maxy = sortedPoint;
						kp_points_ru.add(P_maxy);
					}
					else if(sortedPoint.getY() < P_miny.getY())
					{
						
						
						
						P_miny = sortedPoint;
						kp_points_ro.add(P_miny);
					}
				
			}
			
			// in the End
			U = P_maxy;
			//O = P_miny sollte gelten
		}
		catch(Exception exc)
		{}
		}
	
	// ConvexHull
	
	/**
	 * Draws the convex hull.
	 *
	 * @param panel the panel to draw too
	 */
	private void drawConvexHull(DrawingPanel panel)
	{

		for (int i = 0; i < ch_points.size(); i++)
		{
			
			try
			{
				panel.addLine(ch_points.get(i), ch_points.get(i+1));
			}
			catch (Exception e)
			{
				panel.addLine(ch_points.get(i), ch_points.get(0));
			}
		}
		
		
		
	}

	/**
	 * Writes the convex-hull-points in the ch_points list.
	 *
	 * @return the convex hull
	 */
	private void getConvexHull()
	{
	
		addPointsToList();
	}


	/**
	 * Writes the convex-hull-points in the ch_points list. Takes care of the right-order and cleans up double points
	 */
	private void addPointsToList()
	{
		
		int i = 0;
		ch_points.addAll(getPartHull(i,kp_points_lo,O));
		
		Collections.reverse(kp_points_ro); 	
		for (SimplePoint simplePoint : getPartHull(i, kp_points_ro, R))
		{
			if(!simplePoint.equals(O))
			{
				ch_points.add(simplePoint);
			}
		}		
		
		for (SimplePoint simplePoint : getPartHull(i, kp_points_ru, U))
		{
			if(!simplePoint.equals(R) && !simplePoint.equals(L)) // Spezialfall U=L, waagerechte Linie
			{
				ch_points.add(simplePoint);
			}
		}	
		
		Collections.reverse(kp_points_lu);

		
		for (SimplePoint simplePoint : getPartHull(i, kp_points_lu, L))
		{
			if(!simplePoint.equals(U) && !simplePoint.equals(L))
			{
				ch_points.add(simplePoint);
			}
		}	
	}

	/**
	 * Gets a part of the hull.
	 *
	 * @param i the upcounting index
	 * @param part the part of the KonturPolygon
	 * @param last the last point(turning point) of this part
	 * @return the part hull list
	 */
	private List<SimplePoint> getPartHull(int i,List<SimplePoint> part, SimplePoint last)
	{
		List<SimplePoint> ret = new ArrayList<SimplePoint>();
		
		//ret.addAll(part);
		
		//DEBUG
		for (SimplePoint simplePoint : part)
		{
			ret.add(new SimplePoint(simplePoint.getX(), simplePoint.getY()));
		}
		
		try
		{
			while(!ret.get(i+1).equals(last))
			{
				if(LRTest(ret.get(i), ret.get(i+1), ret.get(i+2)) >= 0) // Wenn C(i+2) links liegt => B(i+1) rechts
				{
					 // Punkt i+1 liegt rechts von i und i+2
					
					
					int j = i+1; //hereinragend
					j--; // gehe einen Punkt zurück
					
					i = removePoints(i, j, ret);
					
					
				}
				else
				{
					i++;
				}
			}
		}
		catch (Exception e)
		{
			// ret.get(i+1) oo 
		}
			
			
		
		
		return ret;
	}
	
	/**
	 * Removes the points on the right side of the convexline.
	 *
	 * @param i the upcounting index,i=ancestor i+1=found point i+2=successor
	 * @param j the downcounting index 
	 * @param ret the part of the convexhull which will be returned
	 * @return the index the algorithm has to start again
	 */
	private int removePoints(int i, int j, List<SimplePoint> ret)
	{
		// verkleiner j solange bis j=0 oder lr(j-1,j,i+2) < 0
		while(j > -1)
		{ // Suche eine Linie für die der Nachfolger des zu löschenden Punktes rechts liegt
			if(j == 0)
			{ // Keine gefunden => Linie vom Ursprung, Rest löschen
				//Lösche alle Punkte dazwischen aus ret
				
				
				for(int k = j+1;k < i+2;k++)
				{
					ret.remove(j+1); // Takes care off the fact that a previous point was removed => remove(const) will always remove another point (the next one)
				}
				
				// Setze den zähler zum Ursprung zurück
				i = j;
				break;
			}
			else if(LRTest(ret.get(j-1), ret.get(j), ret.get(i+2)) < 0)
			{ // eine solche linie existiert => Linie vom gefunden Punkt bis zum Nachfolger, Rest löschen
				
				
				// alles dazwischen löschen
				for(int k = j+1;k < i+2;k++)
				{
					ret.remove(j+1);  // Takes care off the fact that a previous point was removed => remove(const) will always remove another point (the next one)
				}
				
				// Zähler auf Startpunkt der neuen Linie setzen
				i = j;
				break;
			}
			else
			{
				j--;
			}
		}
		return i;
	}
	
	/**
	 * LR test.
	 *
	 * @param A the first point
	 * @param B the second point
	 * @param C the point which location will be returned
	 * @return the result, r<0 => C is on the right r>0 => C is on the left (note that the "realresult" is being inversed because 0,0 is in the top left)
	 */
	private long LRTest(SimplePoint A,SimplePoint B, SimplePoint C) 
	{
		long ergebnis = (C.getX()-A.getX())*(C.getY()+A.getY()) + (B.getX()-C.getX())*(B.getY()+C.getY()) + (A.getX()-B.getX())*(A.getY()+B.getY());
		return ergebnis*(-1); // reverse the result because 0,0 is top left
	}
}

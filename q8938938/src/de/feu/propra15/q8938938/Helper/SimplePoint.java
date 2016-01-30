package de.feu.propra15.q8938938.Helper;

// TODO: Auto-generated Javadoc
/**
 * The Class SimplePoint.
 * @author Niklas Reusch
 */
public class SimplePoint implements Comparable<SimplePoint>, Cloneable
{

	/** The _x. */
	private long _x;
	


	/** The _y. */
	private long _y;


	/** The vor. */
	private SimplePoint nach,vor;

	/**
	 * Instantiates a new simple point.
	 * 
	 * @param x the x
	 * @param y the y
	 */
	public SimplePoint(long x, long y)
	{

		_x = x;
		_y = y;

	}

	/**
	 * Instantiates a new simple point.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public SimplePoint(double x, double y)
	{
		_x = (long) x;
		_y = (long) y;
		
	}

	/**
	 * Instantiates a new simple point.
	 *
	 * @param pts the pts
	 */
	public SimplePoint(int[] pts)
	{

		_x = pts[0];
		_y = pts[1];
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public long getX()
	{

		return _x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(long x)
	{

		this._x = x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public long getY()
	{

		return _y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(long y)
	{

		this._y = y;
	}


	/* Used to Compare the SimplePoint to others(e.g. in the ConvexHullCalculation). x gets compared first
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SimplePoint p)
	{

		if (this.getX() < p.getX())
		{
			return -1;
		}
		else if (this.getX() == p.getX())
		{
			if (this.getY() < p.getY())
			{
				return -1;
			}
			else if(this.getY() > p.getY())
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return 1;
		}

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{

		return "[" + this.getX() + "," + this.getY() + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{

		if (obj instanceof SimplePoint && (int)((SimplePoint) obj).getX() == (int)this.getX()
				&& (int)((SimplePoint) obj).getY() == (int)this.getY()){
			return true;}
		else{
			return false;}
	}

	/**
	 * Get Nachfolger.
	 *
	 * @return the nach
	 */
	public SimplePoint getNach()
	{

		return nach;
	}

	/**
	 * Sets Nachfolger.
	 *
	 * @param nach the new nach
	 */
	public void setNach(SimplePoint nach)
	{

		this.nach = nach;
	}

	/**
	 * Gets Vorgänger.
	 *
	 * @return the vor
	 */
	public SimplePoint getVor()
	{

		return vor;
	}

	/**
	 * Sets Vorgänger.
	 *
	 * @param vor the new vor
	 */
	public void setVor(SimplePoint vor)
	{

		this.vor = vor;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SimplePoint clone()
	{
		{
			 SimplePoint theClone = null;
			      try {
			        theClone = (SimplePoint) super.clone();
			        theClone.setNach(this.getNach());
			        theClone.setVor(this.getVor());
			      }
			      catch(CloneNotSupportedException e) {
			      }
			      return theClone;
			    }
	}
}

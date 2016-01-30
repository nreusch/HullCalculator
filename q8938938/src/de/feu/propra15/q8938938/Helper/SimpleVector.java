package de.feu.propra15.q8938938.Helper;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleVector.
 *
 * @author Niklas Reusch
 */
public class SimpleVector
{
	
	/** The xlength. */
	private double xlength;
	public double getXlength()
	{
	
		return xlength;
	}

	/** The ylength. */
	private double ylength;

	public double getYlength()
	{
	
		return ylength;
	}
	

	/**
	 * Instantiates a new simple vector.
	 * 0,0 is the top left corner so ylength may be inverted
	 * @param xlength the xlength
	 * @param ylength the ylength
	 */
	public SimpleVector(double xlength, double ylength)
	{
		this.xlength = xlength;
		this.ylength = ylength;
		
	}
	
	/**
	 * Instantiates a new simple vector.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 */
	public SimpleVector(SimplePoint p1, SimplePoint p2)
	{
		this.xlength = p2.getX() - p1.getX();
		this.ylength = p2.getY() - p1.getY();
	}
	
	
	public double getBetrag()
	{
		return Math.sqrt(Math.pow(xlength, 2) + Math.pow(ylength, 2));
	}
	
	/**
	 * Gets the dot product.
	 *
	 * @param v2 the v2
	 * @return the dot product
	 */
	public double getDotProduct(SimpleVector v2)
	{
		return this.getXlength() * v2.getXlength() + this.getYlength() * v2.getYlength();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{

		return "[" + this.getXlength() + "," + this.getYlength() + "]";
	}
}

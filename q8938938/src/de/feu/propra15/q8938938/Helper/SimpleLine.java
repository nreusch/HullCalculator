package de.feu.propra15.q8938938.Helper;

import java.awt.Color;


// TODO: Auto-generated Javadoc
/**
 * The Class SimpleLine.
 *  @author Niklas Reusch
 */
public class SimpleLine
{
	
	private SimplePoint _p1, _p2;
	
	private Color _c;
	
	/**
	 * Instantiates a new simple line.
	 *
	 * @param p1 the Startpoint
	 * @param p2 the Endpoint
	 * @param c the Color
	 */
	public SimpleLine(SimplePoint p1, SimplePoint p2, Color c)
	{
		_p1 = p1;
		_p2 = p2;
		this.set_color(c);
		
	}
	
	/**
	 * Gets the Firstpoint.
	 *
	 * @return the Firstpoint
	 */
	public SimplePoint getP1()
	{

		return _p1;
	}

	/**
	 * Sets the Firstpoint.
	 *
	 * @param p1 the new Firstpoint
	 */
	public void setP1(SimplePoint p1)
	{

		this._p1 = p1;
	}

	/**
	 * Gets the Endpoint.
	 *
	 * @return the Endpoint
	 */
	public SimplePoint getP2()
	{

		return _p2;
	}

	/**
	 * Sets the Endpoint.
	 *
	 * @param p2 the new Endpoint
	 */
	public void setP2(SimplePoint p2)
	{

		this._p2 = p2;
	}

	/**
	 * Gets the Color.
	 *
	 * @return the Color
	 */
	public Color get_color()
	{

		return _c;
	}

	/**
	 * Sets the Color.
	 *
	 * @param _c the new Color
	 */
	public void set_color(Color _c)
	{

		this._c = _c;
	}
}

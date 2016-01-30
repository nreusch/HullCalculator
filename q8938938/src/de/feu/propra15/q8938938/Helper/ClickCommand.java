package de.feu.propra15.q8938938.Helper;

import java.awt.event.MouseEvent;
import java.util.List;

import de.feu.propra15.q8938938.Main.DrawingPanel;


// TODO: Auto-generated Javadoc
/**
 * The Class ClickCommand.
 * Does not verify double undo
 * @author Niklas Reusch
 */
public class ClickCommand extends Command
{

	/** The e. */
	private MouseEvent e;
	
	/** The _points. */
	private List<SimplePoint> _points;
	
	/** The _pnt. */
	private SimplePoint _pnt;
	
	/** The _leftclick. */
	private boolean _leftclick;

	

	/**
	 * Instantiates a new click command.
	 *
	 * @param e the e
	 * @param points the points
	 * @param leftclick the leftclick
	 */
	public ClickCommand(MouseEvent e, List<SimplePoint> points,boolean leftclick)
	{
		this.e = e;
		this._points = points;
		this._leftclick = leftclick;

		
	}

	
	/* (non-Javadoc)
	 * @see de.feu.propra15.q8938938.Helper.Command#doInner()
	 */
	@Override
	protected void doInner()
	{
		
			if (_leftclick)
			{
				_pnt = new SimplePoint(e.getX(), e.getY());			
				_points.add(_pnt);
			}
			else
			{
				try
				{
					int ind = findPointWithIndex(e.getX(), e.getY());
					_pnt = new SimplePoint(_points.get(ind).getX(),_points.get(ind).getY()); // Save a copy of the deleted Point, could do clone aswell
					ErasePoint(ind);
				}
				catch(IndexOutOfBoundsException e)
				{
					// No Point at Click Pos
				}
			}
			
		
		
		
	}


	/* (non-Javadoc)
	 * @see de.feu.propra15.q8938938.Helper.Command#undoInner()
	 */
	@Override
	protected void undoInner()
	{
		
			if (_leftclick)
			{
				if (_pnt != null)
				{			
					_points.remove(_pnt);
				}
			}
			else
			{
				_points.add(_pnt);
			}
		
	}
	
	/**
	 * Find point with index.
	 *
	 * @param mousex the mousex
	 * @param mousey the mousey
	 * @return the int
	 */
	private int findPointWithIndex(int mousex, int mousey)
	{

		int i = 0;
		int pointSideWidth = (DrawingPanel.c_pointSize - 1) / 2;
		for (SimplePoint point : _points)
		{
			if (mousex >= point.getX() - pointSideWidth
					&& mousex <= point.getX() + pointSideWidth)
			{
				if (mousey >= point.getY() - pointSideWidth
						&& mousey <= point.getY() + pointSideWidth)
				{
					return i;
				}
			}
			i++;
		}
		return -1;
	}

	/**
	 * Erases a point.
	 *
	 * @param pointIndex the point index in _points
	 */
	private void ErasePoint(int pointIndex)
	{

		if (pointIndex != -1)
		{
			_points.remove(pointIndex);
			
		}
	}

}

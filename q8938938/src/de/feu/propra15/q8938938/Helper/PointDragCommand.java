package de.feu.propra15.q8938938.Helper;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class PointDragCommand.
 *
 * @author Niklas Reusch
 */
public class PointDragCommand extends Command
{
	
	/** The _oldpos. */
	private SimplePoint _oldpos;
	
	/** The _newpos. */
	private SimplePoint _newpos;
	
	/** The _panelpoints. */
	private List<SimplePoint> _panelpoints;

	/**
	 * Instantiates a new point drag command.
	 *
	 * @param panelpoints the panelpoints
	 * @param oldpos the oldpos
	 * @param newpos the newpos
	 */
	public PointDragCommand(List<SimplePoint> panelpoints,SimplePoint oldpos, SimplePoint newpos)
	{
		this._panelpoints = panelpoints;
		this._oldpos = oldpos;
		this._newpos = newpos;

		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.feu.propra15.q8938938.Helper.Command#doInner()
	 */
	@Override
	protected void doInner()
	{

		if (!_panelpoints.contains(_newpos))
		{
			_panelpoints.remove(_oldpos);
			_panelpoints.add(_newpos);
		}

	}

	/* (non-Javadoc)
	 * @see de.feu.propra15.q8938938.Helper.Command#undoInner()
	 */
	@Override
	protected void undoInner()
	{
			_panelpoints.remove(_newpos);
			_panelpoints.add(_oldpos);
			
	}

}

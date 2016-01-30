package de.feu.propra15.q8938938.Helper;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class AddPointsCommand.
 *
 * @author Niklas Reusch
 */
public class AddPointsCommand extends Command
{
	
	/** The _panelpoints. */
	private List<SimplePoint> _panelpoints;
	
	/** The _pointstoadd. */
	private List<SimplePoint> _pointstoadd;

	/**
	 * Instantiates a new adds the points command.
	 *
	 * @param panelpoints the panelpoints
	 * @param pointstoadd the pointstoadd
	 */
	public AddPointsCommand(List<SimplePoint> panelpoints, List<SimplePoint> pointstoadd)
	{
		this._panelpoints = panelpoints;
		this._pointstoadd = pointstoadd;

	}

	/* (non-Javadoc)
	 * @see de.feu.propra15.q8938938.Helper.Command#doInner()
	 */
	@Override
	protected void doInner()
	{

		_panelpoints.addAll(_pointstoadd);

	}

	/* (non-Javadoc)
	 * @see de.feu.propra15.q8938938.Helper.Command#undoInner()
	 */
	@Override
	protected void undoInner()
	{

		_panelpoints.removeAll(_pointstoadd);
	}

}

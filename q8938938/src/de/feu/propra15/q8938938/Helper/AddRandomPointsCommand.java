package de.feu.propra15.q8938938.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class AddRandomPointsCommand.
 *
 * @author Niklas Reusch
 */
public class AddRandomPointsCommand extends Command
{
	
	/** The _panelpoints. */
	private List<SimplePoint> _panelpoints;
	
	/** The _pntnumber. */
	private int _pntnumber;
	
	
	/** The _pnlwidth. */
	private int _pnlwidth;
	
	/** The _pnlheight. */
	private int _pnlheight;
	
	/** The _alreadygenerated. */
	private boolean _alreadygenerated;
	
	/** The _generated points. */
	List<SimplePoint> _generatedPoints = new ArrayList<SimplePoint>();;

	/**
	 * Instantiates a new adds the random points command.
	 *
	 * @param panelpoints the panelpoints
	 * @param pntnumber the pntnumber
	 * @param pnlwidth the pnlwidth
	 * @param pnlheight the pnlheight
	 */
	public AddRandomPointsCommand(List<SimplePoint> panelpoints,int pntnumber,int pnlwidth, int pnlheight)
	{
		this._panelpoints = panelpoints;
		this._pntnumber = pntnumber;
		this._pnlwidth = pnlwidth;
		this._pnlheight = pnlheight;
		

		
	}

	/* (non-Javadoc)
	 * @see de.feu.propra15.q8938938.Helper.Command#doInner()
	 * Adds a number of random points in the middle of the window
	 */
	@Override
	protected void doInner()
	{

		if (!_alreadygenerated)
		{
			Random rand = new Random();
			
			int partwidth = Math.round(_pnlwidth/3);
			int partheight = Math.round(_pnlheight/3);
			// Points could be duplicated
			for(int i = 0;i < _pntnumber;i++)
			{
				_generatedPoints.add(new SimplePoint(partwidth + rand.nextInt(partwidth), partheight + rand.nextInt(partheight)));
			}
			_alreadygenerated = true;
		}
		
		
		
		_panelpoints.addAll(_generatedPoints);
		
	}

	/* (non-Javadoc)
	 * @see de.feu.propra15.q8938938.Helper.Command#undoInner()
	 */
	@Override
	protected void undoInner()
	{
		if (_generatedPoints != null)
		{
			
			_panelpoints.removeAll(_generatedPoints);
		}
		
	}


}

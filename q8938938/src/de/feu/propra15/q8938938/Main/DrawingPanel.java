package de.feu.propra15.q8938938.Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.feu.propra15.q8938938.Helper.AddPointsCommand;
import de.feu.propra15.q8938938.Helper.AddRandomPointsCommand;
import de.feu.propra15.q8938938.Helper.ClickCommand;
import de.feu.propra15.q8938938.Helper.Command;
import de.feu.propra15.q8938938.Helper.PointDragCommand;
import de.feu.propra15.q8938938.Helper.SimpleArc;
import de.feu.propra15.q8938938.Helper.SimpleLine;
import de.feu.propra15.q8938938.Helper.SimplePoint;
import de.feu.propra15.q8938938.math.HullCalculator;

// TODO: Auto-generated Javadoc
/**
 * 
 * The Class DrawingPanel.
 * Implements the Interaction with the Panel e.g. adding and dragging Points
 * @author Niklas Reusch
 */
@SuppressWarnings("serial")
public class DrawingPanel extends JPanel implements MouseListener,
		MouseMotionListener
{
	
	
	/** The _points. */
	List<SimplePoint> _points = new ArrayList<SimplePoint>(); // the arrays are the same in the panel and the calculator
	
	/** The _lines. */
	List<SimpleLine> _lines = new ArrayList<SimpleLine>();
	
	/** The _arcs. */
	List<SimpleArc> _arcs = new ArrayList<SimpleArc>();
	
	/** The _command stack. */
	Stack<Command> _commandStack = new Stack<Command>();
	
	/** The c_pointSize. Gives the size(Width&Height) a Point will be drawn. The SimplePoint-Coordinates are always the center of the square. */
	public final static int c_pointSize = 6;
	
	/** The _calc. */
	private HullCalculator _calc;
	
	/** The _dragging. Determines if a point is being dragged. */
	private boolean _dragging;
	
	/** The _draggedpoint. */
	private SimplePoint _draggedpoint;

	/** The _drag start pos. */
	private SimplePoint _dragStartPos;

	

	/**
	 * Instantiates a new drawing panel.
	 *
	 * @param calc the HullCalculator
	 */
	public DrawingPanel(HullCalculator calc)
	{

		this._calc = calc;
		addMouseListener(this);
		addMouseMotionListener(this);
		
		// Declare Undo and Redo Actions
		this.getActionMap().put("undo", new AbstractAction()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Go from top of the stack to the button
				for (int i = _commandStack.size()-1; i >= 0; i--)
				{
					if (_commandStack.get(i).undo() == true) // undo successful <=> not already undone
					{
						break;
					}
					
					
				}
				
				drawHulls();
				
			}
		});
		
		this.getActionMap().put("redo", new AbstractAction()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Go from bottom of the stack to the top
				for (int i = 0; i <= _commandStack.size()-1; i++)
				{
					if (_commandStack.get(i).execute() == true) // redo successful <=> not already redone
					{
						break;
					}
					
					
				}
				
				drawHulls();
				
			}
		});
		
		// Declare Hotkeys for Undo and Redo
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK), "undo");
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK), "redo");
	}
	 
	/**
	 * Removes all Points and Lines.
	 */
	public void clear()
	{
		_points = new ArrayList<SimplePoint>();
		_lines = new ArrayList<SimpleLine>();
		_arcs = new ArrayList<SimpleArc>();
		repaint();
	}
	
	/**
	 * Adds points from a list.
	 *
	 * @param points the Point-List
	 */
	public void addPoints(List<SimplePoint> points)
	{
		AddPointsCommand cmd = new AddPointsCommand(_points, points);
		_commandStack.push(cmd);
		cmd.execute();
		drawHulls();
	}
	
	/**
	 * Adds a point.
	 *
	 * @param point the point
	 */
	public void addPoints(SimplePoint point)
	{
		_points.add(point);
		drawHulls();
	}
	
	/**
	 * Adds random points in the middle ninth.
	 *
	 * @param number the number of random points to add
	 */
	public void addRandomPoints(int number)
	{
		AddRandomPointsCommand cmd = new AddRandomPointsCommand(_points, number, this.getWidth(), this.getHeight());
		cmd.execute();
		_commandStack.push(cmd);
		drawHulls();
	}

	/**
	 * @return Returns the Point-List
	 */
	public List<SimplePoint> getPoints()
	{
		return _points;
	}

	/**
	 * Adds a line to _lines.
	 *
	 * @param p1 the Startpoint
	 * @param p2 the Endpoint
	 */
	public void addLine(SimplePoint p1, SimplePoint p2)
	{
	
		// TODO Auto-generated method stub
		_lines.add(new SimpleLine(p1,p2,Color.BLACK));
	
		
	}
	

	/**
	 *  
	 * Paints everything.
	 *
	 * @param g the g
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g)
	{

		super.paintComponent(g);
		
		
		for (SimplePoint pnt : _points)
		{
			// Draw an c_pointSize*c_pointSize Point where the given Point is in the middle
			g.fillRect((int) (pnt.getX() - 1),(int) ( pnt.getY() - 1), c_pointSize, c_pointSize);
		}
		
		for (SimpleLine line : _lines)
		{
			g.setColor(line.get_color());
			g.drawLine((int) (line.getP1()).getX(),(int)( line.getP1().getY()),(int) (  line.getP2().getX()),(int)(  line.getP2().getY()));
		}
		
		Graphics2D g2 = (Graphics2D) g;
		for (SimpleArc arc : _arcs)
		{
			g2.draw(new Arc2D.Double(arc.getZ().getX()-arc.getRadius(),arc.getZ().getY()-arc.getRadius(),2*arc.getRadius(),2*arc.getRadius(),-1*arc.getSigma()*(180/Math.PI),-1*arc.getBeta()*(180/Math.PI),Arc2D.OPEN));
		}
		

	}


	
	/**
	 * Adds a line to _lines.
	 *
	 * @param p1 the Startpoint
	 * @param p2 the Endpoint
	 * @param c the Color
	 */
	public void drawLine(SimplePoint p1, SimplePoint p2,Color c)
	{

		// TODO Auto-generated method stub
		_lines.add(new SimpleLine(p1,p2,c));
		this.getGraphics().setColor(c);
		
		repaint();
	}

	/**
	 *  Updates the Position of the dragged Point and the ConvexHull.
	 *
	 * @param e the e
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		System.out.println("dragging");
		if(_dragging == true)
		{
			
			_draggedpoint.setX(e.getX());
			_draggedpoint.setY(e.getY());
			System.out.println("dragging point");
			drawHulls();
		}

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{
		
	}

	/**
	 *  Adds and removes Points on a Mouseclick, updates the ConvexHull.
	 *
	 * @param e the e
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{

		if (e.getButton() == MouseEvent.BUTTON1)
		{
			ClickCommand cmd = new ClickCommand(e, _points, true);
			cmd.execute();
			_commandStack.push(cmd);
			
			
		}
		else if (e.getButton() == MouseEvent.BUTTON3)
		{
			ClickCommand cmd = new ClickCommand(e, _points, false);
			cmd.execute();
			_commandStack.push(cmd);
			
		}
		
		drawHulls();

	}

	/**
	 * Lets HullCalculator calculate and draw the convex Hull.
	 */
	private void drawHulls()
	{
		_lines.clear();
		_arcs.clear();
		_calc.getConvexHull();
		_calc.drawAngleHull();
		repaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e)
	{

		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e)
	{

		// TODO Auto-generated method stub

	}

	/**
	 * Finds the SimplePoint the user wants to drag and saves it in _draggedpoint.
	 *
	 * @param e the e
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		
		if(_dragging == false)
		{
			try 
			{
				 _draggedpoint = _points.get(findPointWithIndex(e.getX(), e.getY()));
				_dragging = true;
				_dragStartPos = new SimplePoint(_draggedpoint.getX(), _draggedpoint.getY());
				System.out.println("dragging point began");
			}
			catch(IndexOutOfBoundsException exc)
			{
				// No Point there
			}
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
	 * Releases the dragged Point.
	 *
	 * @param e the e
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (_dragging)
		{
			_dragging = false;
			
			PointDragCommand cmd = new PointDragCommand(_points, _dragStartPos, new SimplePoint(_draggedpoint.getX(), _draggedpoint.getY()));
			_commandStack.push(cmd);
			cmd.execute(); // does nothing
			drawHulls();
			
			_dragStartPos = null;
			_draggedpoint = null;
		}
		

	}

	/**
	 * Adds the arc.
	 *
	 * @param simpleArc the simple arc
	 */
	public void addArc(SimpleArc simpleArc)
	{

		_arcs.add(simpleArc);
		
	}
}

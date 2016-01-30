package de.feu.propra15.q8938938.math;

import java.util.List;

import de.feu.propra15.q8938938.Helper.SimpleArc;
import de.feu.propra15.q8938938.Helper.SimplePoint;
import de.feu.propra15.q8938938.Main.DrawingPanel;


// TODO: Auto-generated Javadoc
/**
 * The Class AngleHull.
 *
 * @author Niklas Reusch
 */
public class AngleHull
{
	
	/** The Constant gradtobgn. */
	public static final double gradtobgn = Math.PI/180;
	
	/** The _curangle. */
	private double _curangle;
	
	/** The _pts. */
	private List<SimplePoint> _pts;
	
	/** The _deltas. */
	private double _deltas;
	
	/** The _deltasstr. */
	private double _deltasstr;
	
	/** The l. */
	private SimplePoint L;
	
	/** The r. */
	private SimplePoint R;
	
	/** The _deltae. */
	private double _deltae;
	
	
	/**
	 * Instantiates a new angle hull.
	 *
	 * @param panel the DrawingPanel
	 * @param curCH the current ConvexHull
	 */
	public AngleHull(DrawingPanel panel,ConvexHull curCH)
	{
		if (curCH.getCHPointsAL().size() >= 2)
		{
			_curangle = 30*gradtobgn;
			calculateAndDrawAngleHull(panel,curCH,_curangle);
			
			_curangle = 45*gradtobgn;
			calculateAndDrawAngleHull(panel,curCH,_curangle);
			
			_curangle = 60*gradtobgn;
			calculateAndDrawAngleHull(panel,curCH,_curangle);
			
			_curangle = 75*gradtobgn;
			calculateAndDrawAngleHull(panel,curCH,_curangle);
			
			_curangle = 90*gradtobgn;
			calculateAndDrawAngleHull(panel,curCH,_curangle);
			
			_curangle = 120*gradtobgn;
			calculateAndDrawAngleHull(panel,curCH,_curangle);
			
			_curangle = 150*gradtobgn;
			calculateAndDrawAngleHull(panel,curCH,_curangle);
		}
		
		
	}

	/**
	 * Calculate and draw angle hull.
	 *
	 * @param panel the DrawingPanel
	 * @param curCH the current ConvexHull
	 * @param curangle the current Angle alpha
	 */
	private void calculateAndDrawAngleHull(DrawingPanel panel,
			ConvexHull curCH, double curangle)
	{
		_pts = curCH.getCHPointsAL();

		// Set Vorgänger and Nachfolger for every Point in the List
		for (int i = 0; i < _pts.size(); i++)
		{
			if (i+1 == _pts.size())
			{
				_pts.get(i).setNach(_pts.get(0));
			}
			else
			{
				_pts.get(i).setNach(_pts.get(i+1));
			}
			
		}
		
		for (SimplePoint simplePoint : _pts)
		{
			simplePoint.getNach().setVor(simplePoint);
		}
		
		// Find StartConfig
		
		SimplePoint Ls = _pts.get(0);
		SimplePoint Rs = _pts.get(0);
		_deltas = 0f;
		_deltasstr = 0f;
		
		while (WinkelVergleichsTest(Ls.getVor(), Ls, Rs, Rs.getNach(), curangle))
		{
			Rs = Rs.getNach();
		}
		
		if (Ls.equals(Rs))
		{
			Rs = Rs.getNach();
		}
		else
		{
			// (*1*)
			_deltasstr = 2*(SimpleArc.getEnclosedAngleBetweenThreePoints(Rs, Ls, Ls.getVor()) - curangle);
		}
		
		// Rundlauf
		SimplePoint LSt = Ls.clone();
		SimplePoint RSt = Rs.clone();
		do
		{
			_deltas = _deltasstr;
			L = LSt.clone();
			R = RSt.clone();
			
			if (WinkelVergleichsTest(L, L.getNach(), R, R.getNach(), curangle))
			{
				//2
				_deltae = 2*(SimpleArc.getEnclosedAngleBetweenThreePoints(R.getNach(), R, L) - curangle);
				_deltasstr = 2*SimpleArc.getEnclosedAngleBetweenThreePoints(L, R.getNach(), R);
				RSt = R.getNach(); // R rückt vor
			}
			else
			{
				LSt = L.getNach(); // L rückt vor
				
				if (LSt.equals(RSt))
				{
					//3
					_deltae = 0f;
					_deltasstr = 0f;
					RSt = RSt.getNach();
				}
				else
				{
					//4
					_deltae = 2* SimpleArc.getEnclosedAngleBetweenThreePoints(L.getNach(), L, R);
					_deltasstr = 2 * (SimpleArc.getEnclosedAngleBetweenThreePoints(R, L.getNach(), L) - curangle);
				}
			}
			
			//Z, r, σ + δs, β − δs − δe) ist ein Bogen der Winkelhulle!
			SimpleArc temp = new SimpleArc(L, R, curangle);
			
			// Startwinkelkorrektur
			if (L.getY() <  temp.getZ().getY())
			{
				panel.addArc(new SimpleArc(temp.getZ(), temp.getRadius(), (2*Math.PI - temp.getSigma())+_deltas, temp.getBeta()-_deltas-_deltae));
			}
			else
			{
				panel.addArc(new SimpleArc(temp.getZ(), temp.getRadius(),temp.getSigma()+_deltas, temp.getBeta()-_deltas-_deltae));
			}
			
		}
		while (!RSt.equals(Rs) || !LSt.equals(Ls));
		
		
		
		
		
	}
	
	
	

	/**
	 * Winkelvergleichs test.
	 * Zitat Aufgabenstellung S. 24 : 
	 * "die vier Punkte in der Reihenfolge A, B, C,
	 * 		D gegen den Uhrzeiger als Ecken eines konvexen Polygons vorkommen, wobei
	 * 		B = C erlaubt ist, und wollen wissen, siehe Abbildung 2.6: Schneiden sich
	 * 		die beiden Geraden auf AB in einem Punkt von A aus gesehen hinter oder
	 * 		gleich B (und deshalb auf DC in einem Punkt von D aus gesehen hinter oder
	 * 		gleich C), und wenn ja, ist der Schnittwinkel an dieser Stelle ≥ α"
	 *
	 * @param A the a
	 * @param B the b
	 * @param C the c
	 * @param D the d
	 * @param curangle the curangle
	 * @return true, if successful
	 */
	private boolean WinkelVergleichsTest(SimplePoint A,SimplePoint B,SimplePoint C, SimplePoint D,double curangle)
	{
		
		/*TODO:
		 * Wer Rechenzeit sparen m¨ochte, kann noch das Folgende anwenden: Ein gewisses
Einsparungspotential ist bei dieser Berechnung dadurch gegeben, dass dieser Test
sp¨ater sehr oft mit demselben α aufgerufen wird. Um nun die Aufrufe von arccos in
der Winkelfunktion einzusparen, kann man statt dessen einmal cos α berechnen und
das Argument von arccos in der Winkelfunktion damit vergleichen. Dies funktioniert,
weil die cos-Funktion im Intervall [0, π] monoton ist, allerdings monoton fallend(!),
die Rollen von > und < sind dann also vertauscht.
		 */
		// Det(B-A,D-C,0)
		long res = Determinante(new SimplePoint(B.getX() - A.getX(), B.getY()-A.getY()), new SimplePoint(D.getX() - C.getX(), D.getY()-C.getY()), new SimplePoint(0, 0));
		if (res <= 0)
		{
			return false;
		}
		else
		{
			if (SimpleArc.getEnclosedAngleBetweenFourPoints(A, B, C, D) >= curangle)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	/**
	 * Determinante.
	 *
	 * @param A the a
	 * @param B the b
	 * @param C the c
	 * @return the long
	 */
	private long Determinante(SimplePoint A,SimplePoint B, SimplePoint C) 
	{
		long ergebnis = (C.getX()-A.getX())*(C.getY()+A.getY()) + (B.getX()-C.getX())*(B.getY()+C.getY()) + (A.getX()-B.getX())*(A.getY()+B.getY());
		return ergebnis; 
	}
	
	
}

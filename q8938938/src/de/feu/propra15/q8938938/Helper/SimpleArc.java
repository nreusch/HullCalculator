package de.feu.propra15.q8938938.Helper;

import java.math.BigDecimal;

public class SimpleArc
{

	private SimplePoint _pM;
	private double _d;
	private double _k;
	private SimpleVector _vW;
	private SimplePoint _pZ;
	private double _r;
	private double _sigma;
	private double _beta;

	public double getSigma()
	{
		
		return _sigma;
	}

	public double getBeta()
	{
	
		return _beta;
	}

	public SimplePoint getZ()
	{
	
		return _pZ;
	}

	public double getRadius()
	{
		return _r;
	}

	/**
	 * Instantiates a new simple arc.
	 *
	 * @param pA the p a
	 * @param pB the p b
	 * @param alpha the Angle Alpha in Radians
	 */
	public SimpleArc(SimplePoint pA, SimplePoint pB, double alpha)
	{
		
		SimpleVector AB = new SimpleVector(pA, pB);
		_pM = new SimplePoint(pA.getX() + 0.5f * AB.getXlength(),pA.getY() + 0.5f * AB.getYlength());
		_d = AB.getBetrag();
		_k = -1*_d/(2*Math.tan(alpha));
		double kd = _k/_d;
		_vW = new SimpleVector(kd*(pA.getY()-pB.getY()), kd*(pB.getX()-pA.getX()));
		_pZ = new SimplePoint(_pM.getX()+_vW.getXlength(), _pM.getY()+_vW.getYlength());
		_r = (_d/(2*Math.sin(alpha)));
												//Input(E,O,A-Z)
		_sigma = getEnclosedAngleBetweenTwoVectors(new SimpleVector(1, 0), new SimpleVector(_pZ, pA));
		_beta = 2*(Math.PI - alpha);
		
	}
	
	public SimpleArc(SimplePoint Z, double r, double sigma, double beta)
	{
		this._pZ = Z;
		this._r = r;		
		this._sigma = sigma;
		this._beta = beta;
		
	}

	public BigDecimal getDistanceBetween(SimplePoint pnt1, SimplePoint pnt2)
	{

		System.out.println("p1" + pnt1 + "p2" + pnt2);
		return new BigDecimal(
				Math.sqrt(Math.pow((pnt1.getX() - pnt2.getX()), 2)
						+ Math.pow((pnt1.getY() - pnt2.getY()), 2)));
	}

	// TODO: Fallunterscheidung
	/**
	 * Gets the enclosed angle between two vectors.
	 * (A,B,C) => v1=BA v2= BC
	 * @param v1 the first vector
	 * @param v2 the second vector
	 * @return the enclosed angle between two vectors
	 */
	private static double getEnclosedAngleBetweenTwoVectors(SimpleVector v1,
			SimpleVector v2)
	{

		double zaehler_dotp = v1.getDotProduct(v2);
		double nenner_betrag = v1.getBetrag() * v2.getBetrag();
		return Math.acos(zaehler_dotp / nenner_betrag);
	}
	
	public static double getEnclosedAngleBetweenFourPoints(SimplePoint A, SimplePoint B, SimplePoint C, SimplePoint D)
	{
		// A = A-B B=0 C=D-C
		SimplePoint pA = new SimplePoint(A.getX() - B.getX(), A.getY()-B.getY());
		SimplePoint pB = new SimplePoint(0, 0);
		SimplePoint pC = new SimplePoint(D.getX() - C.getX(), D.getY()-C.getY());
		return getEnclosedAngleBetweenTwoVectors(new SimpleVector(pB,pA),new SimpleVector(pB, pC));
	}
	
	public static double getEnclosedAngleBetweenThreePoints(SimplePoint A, SimplePoint B, SimplePoint C)
	{
		return getEnclosedAngleBetweenTwoVectors(new SimpleVector(B, A), new SimpleVector(B, C));
	}

	public double getDotProduct(SimplePoint A, SimplePoint B)
	{

		return A.getX() * B.getX() + A.getY() * B.getY();
	}



	



}

package de.feu.propra15.q8938938.math;

public abstract interface IHullCalculator {
	  
	  // Method descriptor #6 (Ljava/lang/String;)V
	  public abstract void addPointsFromFile(java.lang.String arg0) throws java.io.IOException;
	  
	  // Method descriptor #11 ([[I)V
	  public abstract void addPointsFromArray(int[][] arg0);
	  
	  // Method descriptor #13 (II)V
	  public abstract void addPoint(int arg0, int arg1);
	  
	  // Method descriptor #15 ()V
	  public abstract void clear();
	  
	  // Method descriptor #17 ()Ljava/lang/String;
	  public abstract java.lang.String getMatrNr();
	  
	  // Method descriptor #17 ()Ljava/lang/String;
	  public abstract java.lang.String getName();
	  
	  // Method descriptor #17 ()Ljava/lang/String;
	  public abstract java.lang.String getEmail();
	  
	  // Method descriptor #21 ()[[I
	  public abstract int[][] getConvexHull();
	}

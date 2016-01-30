// Beispiel fÃ¼r Main.java im Programmierpraktikum

package de.feu.propra15.q8938938.Main;

import javax.swing.SwingUtilities;

import de.feu.propra15.q8938938.math.HullCalculator;

// TODO: Auto-generated Javadoc
/**
 * The Class q8938938Main.
 *
 * @author Niklas Reusch
 */
public class q8938938Main {

	/**
	 * The main method.
	 *
	 * @param args The arguments to the program at the start. "-t" starts the testmode
	 */
	public static void main(String[] args) {
		
		final HullCalculator calculator = new HullCalculator();
		
		// Wenn mindestens ein Argument an das Programm
		// übergeben wurde und das Argument '-t' entspricht,
		if (args.length > 0 && "-t".equals(args[0])) {
			// dann starte den Tester
			
			//Tester tester = new Tester(args, calculator);
			//System.out.println(tester.test());
		}
		else {
			// Andernfalls starte den normalen Programmablauf
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{

					try
					{
						WindowedApplication window = new WindowedApplication(calculator);
						
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			});
		}
	}

}

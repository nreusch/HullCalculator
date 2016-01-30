package de.feu.propra15.q8938938.Main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import de.feu.propra15.q8938938.Helper.SimplePoint;
import de.feu.propra15.q8938938.math.HullCalculator;

// TODO: Auto-generated Javadoc
/**
 * The Class WindowedApplication.
 * 
 * @author Niklas Reusch
 */
public class WindowedApplication
{

	/**
	 * The Constant STARTHEIGHT. 
	 * Sets the Height of the Frame.
	 */
	public static final int STARTHEIGHT = 480;
	
	/**
	 * The Constant STARTWIDTH. 
	 * Sets the Width of the Frame
	 */
	public static final int STARTWIDTH = 640;
	
	/** 
	 * The Boolean alreadysaved.
	 * Is used to determine if the Save-Button should save to a new or old file.
	 */
	public static Boolean alreadysaved = false;
	
	/** The Mainframe. */
	JFrame _frame;
	
	/** The Panel everything gets drawn on. */
	static DrawingPanel _panel;
	
	/** The HullCalculator. */
	private HullCalculator _calculator;
	
	/** The File the Save-Button will save to. */
	private File _file;
	
	/** The Frame for the HelpScreen. */
	private JFrame _helpframe;

	/**
	 * Creates & Launches the graphical Interface
	 *
	 * @param calculator the calculator
	 */
	public WindowedApplication(HullCalculator calculator)
	{

		
		initialize(calculator);
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 *
	 * @param calculator the calculator
	 */
	private void initialize(HullCalculator calculator)
	{
		this._calculator = calculator;
		_panel = new DrawingPanel(_calculator);
		_calculator.setPanel(_panel);
		
		
		_frame = new JFrame();
		_frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) 
			{
				if (!alreadysaved)
				{
					
					int result = JOptionPane.showConfirmDialog(_frame, "Sie haben ihre letzten Änderungen noch nicht gespeichert! Wollen Sie das Programm wirklich beenden?", "Wirklich beenden?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					   if (result==JOptionPane.YES_OPTION) {
						   _frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					   } 
					   else
					   {
						   _frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					   }
				}
			}
		});
		_frame.setBounds(100, 100, STARTWIDTH, STARTHEIGHT);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setTitle("HüllenBerechner Niklas Reusch 8938938");
		
		_helpframe = new JFrame("Hilfe");
		_helpframe.setBounds(100,100,STARTWIDTH,STARTHEIGHT);
		_helpframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		_helpframe.setTitle("Hilfe");
		
		JTextArea ta = new JTextArea("Das Programm berechnet die konvexe Hülle für eine gegebene Punktmenge."
				+ "\nAußerdem berechnet es die Winkelhüllen für α = 30◦, 45◦, 60◦, 75◦, 90◦, 120◦, 150◦."
				+ "\nEs zeigt diese Hüllen als schwarze Linien an."			
				+ "\nSie können Punkte aus Dateien einlesen, Zufällige hinzufügen oder sie manuell hinklicken."
				+ "\nMit einem Rechtsklick wird ein Punkt entfernt, mit gedrücktem Linksklick wird er verschoben."
				+ "\nMit Strg+Z kann eine Aktion rückgängig gemacht werden(undo),"
				+ "\nmit Strg+Y kann eine rückgängig-gemachte Aktion wieder ausgeführt werden(redo)."
				+ "\nErstellt von Niklas Reusch,8938938" );
		ta.setEditable(false);
		
		_helpframe.getContentPane().add(ta);
		
		JMenuBar menuBar = new JMenuBar();
		_frame.setJMenuBar(menuBar);

		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		JMenuItem mntmNeu = new JMenuItem("Neu");
		mntmNeu.addActionListener(NewActionListner());
		mnDatei.add(mntmNeu);

		JMenuItem mntmffnen = new JMenuItem("\u00D6ffnen");
		mntmffnen.addActionListener(OpenActionListner(_panel));
		mnDatei.add(mntmffnen);

		JMenuItem mntmSpeichern = new JMenuItem("Speichern");
		mntmSpeichern.addActionListener(SaveActionListner());
		mnDatei.add(mntmSpeichern);

		JMenuItem mntmSpeichernUnter = new JMenuItem("Speichern unter...");
		mntmSpeichernUnter.addActionListener(SaveAsActionListner());
		mnDatei.add(mntmSpeichernUnter);
		
		JMenu mnEinfgen = new JMenu("Einf\u00FCgen");
		menuBar.add(mnEinfgen);
		
		JMenuItem mntmPunkte = new JMenuItem("10 Punkte");
		mntmPunkte.addActionListener(TenPointsAL());
		mnEinfgen.add(mntmPunkte);
		
		
		JMenuItem mntmPunkte_1 = new JMenuItem("50 Punkte");
		mntmPunkte_1.addActionListener(FiftyPointsAL());
		mnEinfgen.add(mntmPunkte_1);
		
		JMenuItem mntmPunkte_2 = new JMenuItem("100 Punkte");
		mntmPunkte_2.addActionListener(HundredPointsAL());		
		mnEinfgen.add(mntmPunkte_2);
		
		JMenuItem mntmPunkte_3 = new JMenuItem("500 Punkte");
		mntmPunkte_3.addActionListener(FiveHundredPointsAL());
		mnEinfgen.add(mntmPunkte_3);
		
		JMenuItem mntmPunkte_4 = new JMenuItem("1000 Punkte");
		mntmPunkte_4.addActionListener(ThousandPointsAL());
		mnEinfgen.add(mntmPunkte_4);
		
		JMenu mnHilfe = new JMenu("Hilfe");
		mnHilfe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				_helpframe.setVisible(true);
			}
		});
		menuBar.add(mnHilfe);
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.setFocusable(false);
		btnUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_panel.getActionMap().get("undo").actionPerformed(e);
			}
		});
		menuBar.add(btnUndo);
		
		JButton btnRedo = new JButton("Redo");
		btnRedo.setFocusable(false);
		btnRedo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_panel.getActionMap().get("redo").actionPerformed(e);
			}
		});
		menuBar.add(btnRedo);
		mnHilfe.addActionListener(HelpAL());
		
		

		_frame.getContentPane().add(_panel, BorderLayout.CENTER);
		_frame.setVisible(true);
		
		

	}

	

	/**
	 * ActionListner for the HelpButton.
	 * Activates the Help JFrame.
	 * @return the action listener
	 */
	private ActionListener HelpAL()
	{
		return new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_helpframe.setVisible(true);
				
				
			}
		};
	}


	/**
	 * ActionListner for the Thousand-points-Button 
	 *
	 * @return the action listener
	 */
	private ActionListener ThousandPointsAL()
	{

		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ap");
				_panel.addRandomPoints(1000);
			}
		};
	}

	/**
	 * ActionListner for the Five-hundred-points-Button
	 *
	 * @return the action listener
	 */
	private ActionListener FiveHundredPointsAL()
	{

		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ap");
				_panel.addRandomPoints(500);
			}
		};
	}

	/**
	 * ActionListner for the Hundred-points-Button
	 *
	 * @return the action listener
	 */
	private ActionListener HundredPointsAL()
	{

		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ap");
				_panel.addRandomPoints(100);
			}
		};
	}

	/**
	 * ActionListner for the Fifty-points-Button
	 *
	 * @return the action listener
	 */
	private ActionListener FiftyPointsAL()
	{

		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ap");
				_panel.addRandomPoints(50);
			}
		};
	}

	/**
	 * ActionListner for the Ten-points-Button
	 * 
	 * @return the action listener
	 */
	private ActionListener TenPointsAL()
	{

		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ap");
				_panel.addRandomPoints(10);
			}
		};
	}
	
	/**
	 * ActionListner for the New-Button
	 * Deletes all Points 
	 * @return the action listener
	 */
	private ActionListener NewActionListner()
	{

		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				_calculator.clear();		
				WindowedApplication.alreadysaved = false;
			}
		};
	}


	/**
	 * ActionListner for the SaveAs-Button
	 * Opens a FileChooser and saves all Points in this file
	 * @return the action listener
	 */
	private ActionListener SaveAsActionListner()
	{

		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				final JFileChooser fchooser = new JFileChooser();
				fchooser.setCurrentDirectory(new File("../Tester/data"));
				int returnVal = fchooser.showSaveDialog(_frame);

				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					WriteFile(fchooser.getSelectedFile(),_panel.getPoints());

				}

			}
		};
	}

	/**
	 * ActionListner for the Save-Button
	 * Saves all the Points to the last Save-Location(_file) or to a new File
	 * @return the action listener
	 */
	private ActionListener SaveActionListner()
	{

		return new ActionListener()
		{
			

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(!WindowedApplication.alreadysaved) // If the file wasnt saved before, let the user select the destination
				{

					final JFileChooser fchooser = new JFileChooser();
					fchooser.setCurrentDirectory(new File("../Tester/data"));
					int returnVal = fchooser.showSaveDialog(_frame);
	
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						WriteFile(fchooser.getSelectedFile(),_panel.getPoints());				
					}

				}
				else // else save the file again
				{
						WriteFile(_file,_panel.getPoints());					
				}
			}

			
		};
	}
	
	/**
	 * ActionListner for the Open-Button
	 * Creates a FileChooser and reads the Points from the selected File.
	 *
	 * @param panel the DrawingPanel
	 * @return the action listener
	 */
	private ActionListener OpenActionListner(final DrawingPanel panel)
	{

		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				final JFileChooser fchooser = new JFileChooser();
				fchooser.setCurrentDirectory(new File("../Tester/data"));
				fchooser.setSelectedFile(new File("wh_test3"));
				int returnVal = fchooser.showOpenDialog(_frame);

				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					panel.clear();
					panel.addPoints(ReadFile(fchooser.getSelectedFile()));
				}
			}

			
		};
	}
	
	/**
	 * Writes all Panel-Points in a file.
	 *
	 * @param file the file to write to
	 * @param points the Point-List
	 */
	private void WriteFile(File file, List<SimplePoint> points)
	{
		
		try
		{
			File savefile = file;
			
			BufferedWriter out = new BufferedWriter(new FileWriter(savefile.getAbsolutePath()));
			
		
			
			
			
			// Write the Coordinates line for line
			for (SimplePoint p : points)
			{
				out.write(p.getX() + " " + p.getY());
				out.newLine();
				
			}
			
			out.flush();
			out.close();
			
			_file = savefile;
			WindowedApplication.alreadysaved = true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * Reads Points from a File,line by line.
	 *
	 * @param file the File to Read
	 * @return the list of points
	 */
	private List<SimplePoint> ReadFile(File file)
	{
		
		List<SimplePoint> readedPoints = new ArrayList<SimplePoint>();
		
		
		File readFile = file; 

		try
		{
			BufferedReader in = new BufferedReader(new FileReader(
					readFile.getAbsolutePath())); 
			
			// Create new Regex which searches for Integers
			String regex = "^-?[0-9]+ +-?[0-9]+ ?";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher;
			
			int x, y;
			String zeile = null;
			
			// Search for 2 Ints and add them to the point list
			while ((zeile = in.readLine()) != null)
			{
				matcher = pattern.matcher(zeile);
				

				if (matcher.find())
				{
					String[] splitted = matcher.group().split("\\s");
					x = Integer.parseInt(splitted[0]);
					y = Integer.parseInt(splitted[1]);
					readedPoints.add(new SimplePoint(x, y));
					
				}

				

			}
			
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return readedPoints;
	}

}

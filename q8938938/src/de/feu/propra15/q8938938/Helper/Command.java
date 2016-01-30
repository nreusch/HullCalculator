package de.feu.propra15.q8938938.Helper;

// TODO: Auto-generated Javadoc
/**
 * The Command Class.
 * A Command can be executed and undone and executed again...
 * The SubClasses dont have to worry about undo/redo logic, they just have to implement doInner and undoInner
 * @author Niklas Reusch
 */
public abstract class Command
{
	
	/** The alreadyundone. */
	protected boolean alreadyundone;

	/** The alreadydone. */
	protected boolean alreadydone;
	/**
	 * Execute/Redo.
	 *
	 * @return true, if not already done
	 */
	public boolean execute()
	{
		if (alreadydone)
		{
			return false;
		}
		else
		{
			doInner();
			alreadydone = true;
			alreadyundone = false;
			return true;
		}
	}
	
	/**
	 * Do inner.
	 */
	protected abstract void doInner();
 

	/**
	 * Undo.
	 *
	 * @return true, if not already undone
	 */
	public boolean undo()
	{
		if (alreadyundone)
		{
			return false;	
		}
		else
		{
			undoInner();
			alreadydone = false;
			alreadyundone = true;
			return true;
		}
	}

	/**
	 * Undo inner.
	 */
	protected abstract void undoInner();

}

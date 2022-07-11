package service;

/**
 * Command interface is the way to execute concrete commands such as {@link AddTaskCommand AddTaskCommand}
 * @author dcuciuc
 */
public interface Command {

	/**
	 * Method executes command
	 */
	public void execute();
}
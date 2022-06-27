package com.stefanini.taskmanager;

/**
 * Main class wich call UI
 * @author dcuciuc
 *
 */

public class TaskManagerApp {
	public static void main(String[] args) {
			ConsoleTaskManagerUI consoleTaskManagerUI = new ConsoleTaskManagerUI();
			consoleTaskManagerUI.launchUI();
	}
}

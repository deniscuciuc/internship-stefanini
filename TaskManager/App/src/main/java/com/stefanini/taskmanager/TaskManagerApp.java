package com.stefanini.taskmanager;

/**
 * Main class that can call a UI (console or another)
 * @author dcuciuc
 */

public class TaskManagerApp {
	public static void main(String[] args) {
			ConsoleTaskManagerUI consoleTaskManagerUI = new ConsoleTaskManagerUI();
			consoleTaskManagerUI.launchUI();
	}
}

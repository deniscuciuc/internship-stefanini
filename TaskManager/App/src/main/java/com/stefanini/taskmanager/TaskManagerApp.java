package com.stefanini.taskmanager;

import com.stefanini.taskmanager.menu.ConsoleUI;
import com.stefanini.taskmanager.menu.impl.ConsoleUIImpl;

/**
 * Main class that can call a UI (console or another)
 * @author dcuciuc
 */
public class TaskManagerApp {
	public static void main(String[] args) {
		ConsoleUI consoleUI = new ConsoleUIImpl();
		consoleUI.launchUI();
	}
}

package com.stefanini.taskmanager;


import com.stefanini.taskmanager.commands.CommandsExecutor;
import com.stefanini.taskmanager.commands.impl.CommandsExecutorImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class TaskManagerApp {
	private static final Logger logger = LogManager.getLogger(TaskManagerApp.class);

	public static void main(String[] args) {
		logger.info(Thread.currentThread().getName() + " thread started");

		new CommandsExecutorImpl().executeAllCommands();

		logger.info(Thread.currentThread().getName()  + " thread finished");
	}

}
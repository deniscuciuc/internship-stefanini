package com.stefanini.taskmanager.menu;

/**
 * This interface represents the user interface of the console.
 * Its role is to select a command of the user's choice and call the invoker class for further execution and invocation of commands
 * @author dcuciuc
 */
public interface ConsoleUI {

    /**
     * Display console user interface and asks user for an option.
     * Then will execute selected command by calling private methods for each option
     */
    void launchUI();
}

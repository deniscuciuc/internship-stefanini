package com.stefanini.taskmanager.commands;

public class ThreadTask implements Runnable{
    private Command command;

    public ThreadTask(Command command) {
        this.command = command;
    }

    @Override
    public void run() {
        command.execute();
    }
}

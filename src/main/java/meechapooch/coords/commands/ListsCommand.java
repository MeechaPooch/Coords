package meechapooch.coords.commands;

import meechapooch.coords.commands.lists.DeleteCommand;
import meechapooch.coords.commands.lists.InfoCommand;
import meechapooch.coords.commands.lists.MakeCommand;
import meechapooch.coords.commands.lists.ShareCommand;

import java.util.HashMap;
import java.util.LinkedList;

public class ListsCommand extends CommandContainer{

    public ListsCommand() {
        subCommands.put("make", new MakeCommand());
        subCommands.put("share", new ShareCommand());
        subCommands.put("delete", new DeleteCommand());
        subCommands.put("info", new InfoCommand());
    }

    @Override
    public String getNoCommandsMessage() {
        return "Input a valid lists subcommand: [make | delete | share]";
    }

    @Override
    public boolean isConsoleCompatible() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Parent command to make, delete and share lists";
    }
}

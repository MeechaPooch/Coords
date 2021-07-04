package meechapooch.coords.commands;

import meechapooch.coords.Coords;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class HelpCommand implements SubCommand {
    @Override
    public boolean isConsoleCompatible() {
        return true;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {

        if(args.length == 0) return "Input a sub-command to see help about.";

        SubCommand command = Coords.subCommands.get(args[0].toLowerCase());
        if(command == null) return "Command " + args[0] + " does not exist.";

        String message = "";
        message += "" + ChatColor.WHITE + ChatColor.UNDERLINE + ChatColor.BOLD + args[0].toUpperCase() + " COMMAND!";
        message += "" + ChatColor.RESET + ChatColor.GOLD + ChatColor.BOLD + "\nDESCRIPTION: " + ChatColor.RESET + command.getDescription();
        message += "" + ChatColor.RESET + ChatColor.BLUE + ChatColor.BOLD + "\nUSAGE: " + ChatColor.RESET + ChatColor.ITALIC + "/coords " + args[0].toLowerCase() + " " + command.getUsage();
        message += "" + ChatColor.RESET + ChatColor.RED + ChatColor.BOLD + "\nEXAMPLES:" + ChatColor.RESET;
        for(String example : command.getExamples()) {
            message += "\n" + ChatColor.ITALIC + "/coords " + args[0].toLowerCase() + " " + example;
        };
        sender.sendMessage(message);
        return null;
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) return Coords.subCommands.keySet().stream().collect(Collectors.toList());
        return null;
    }
}

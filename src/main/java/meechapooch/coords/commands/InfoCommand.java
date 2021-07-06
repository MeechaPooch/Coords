package meechapooch.coords.commands;

import meechapooch.coords.database.CoordEntry;
import meechapooch.coords.database.CoordsList;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InfoCommand implements SubCommand {
    @Override
    public boolean isConsoleCompatible() {
        return false;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
        StringBuilder message = new StringBuilder();
        message.append("" + ChatColor.RESET + ChatColor.BOLD + ChatColor.UNDERLINE + profile.name.substring(0,1).toUpperCase() + profile.name.substring(1) + "'s Personal List:");
        AtomicInteger i = new AtomicInteger(0);
        profile.personal.forEach((k,coord)->message.append("\n" + (i.getAndIncrement()%2==0 ? ChatColor.AQUA :ChatColor.DARK_AQUA) + i.get() + ". " + ChatColor.RESET + coord.getInfo()));
        return message.toString();
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        return null;
    }

    @Override
    public String getDescription() {
        return "Lists all coordinate entries in your personal list";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public LinkedList<String> getExamples() {
        LinkedList<String> examples = new LinkedList<>();
        examples.add("");
        return examples;
    }
}

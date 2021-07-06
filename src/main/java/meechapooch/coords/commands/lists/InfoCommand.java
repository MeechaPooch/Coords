package meechapooch.coords.commands.lists;

import meechapooch.coords.commands.SubCommand;
import meechapooch.coords.database.CoordsList;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class InfoCommand implements SubCommand {
    @Override
    public boolean isConsoleCompatible() {
        return false;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) {
            CoordsList list = profile.getList(args[0]);
            if(list == null) return "Cannot find list " + args[0] + ".";
            StringBuilder message = new StringBuilder();
            message.append("" + ChatColor.BOLD + ChatColor.UNDERLINE + list.getName() + ChatColor.RESET + ChatColor.BOLD + ":");
            AtomicInteger i = new AtomicInteger(0);
            list.coords.forEach((k,coord)->message.append("\n" + (i.getAndIncrement()%2==0 ? ChatColor.AQUA :ChatColor.DARK_AQUA) + i.get() + ". " + ChatColor.RESET + coord.getInfo()));
            return message.toString();
        } else return "Too many arguments, just input a single list name";
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) return profile.getListNames();
        else return null;
    }

    @Override
    public String getUsage() {
        return "<list name>";
    }

    @Override
    public LinkedList<String> getExamples() {
        LinkedList<String> examples = new LinkedList<>();
        examples.add("structures");
        return examples;
    }
}

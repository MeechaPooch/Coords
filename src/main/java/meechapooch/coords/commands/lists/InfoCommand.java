package meechapooch.coords.commands.lists;

import meechapooch.coords.commands.SubCommand;
import meechapooch.coords.database.CoordsList;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

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
            sender.sendMessage("(" + (list.isPublic ? "private" : "public" ) + ") " + list.getName() + ":");
            list.coords.forEach((k,coord)->sender.sendMessage(coord.getInfo()));
            return null;
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

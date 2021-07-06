package meechapooch.coords.commands;

import meechapooch.coords.database.CoordEntry;
import meechapooch.coords.database.FileSaving;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveCommand implements SubCommand {
    @Override
    public boolean isConsoleCompatible() {
        return false;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) {
            String[] path = args[0].split("/");
            HashMap<String, CoordEntry> list = profile.resolve(path);
            if(list == null) return "List does not exist";
            if(list.containsKey(path[path.length-1].toLowerCase())) {
                list.remove(path[path.length-1].toLowerCase());
                FileSaving.writeDatabase();
                profile.getPlayer().sendMessage("Deleted " + args[0]);
                return null;
            } else {
                return "Selected coordinate does not exist.";
            }
        }
        return "";
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) {
            return profile.getAllPaths();
        }
        return null;
    }

    @Override
    public String getDescription() {
        return "Remove coordinate from personal list or public/shared list";
    }

    @Override
    public String getUsage() {
        return "<entry name/path>";
    }

    @Override
    public LinkedList<String> getExamples() {
        LinkedList<String> examples = new LinkedList<>();
        examples.add("starterhome");
        examples.add("bases/blockers9");
        return examples;
    }
}

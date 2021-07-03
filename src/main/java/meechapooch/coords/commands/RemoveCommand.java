package meechapooch.coords.commands;

import meechapooch.coords.database.CoordEntry;
import meechapooch.coords.database.FileSaving;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
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
            HashMap<String, CoordEntry> list = profile.resolve(args[0]);
            if(list == null) return "List does not exist";
            if(list.containsKey(args[0].toLowerCase())) {
                list.remove(args[0].toLowerCase());
                FileSaving.writeDatabase();
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
}

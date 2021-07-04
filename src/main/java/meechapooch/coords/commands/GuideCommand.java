package meechapooch.coords.commands;

import meechapooch.coords.database.CoordEntry;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public class GuideCommand implements SubCommand {
    @Override
    public boolean isConsoleCompatible() {
        return false;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) {
            CoordEntry coord = profile.getCoordEntry(args[0]);
            if(coord == null) return "Cannot find coord " + args[0];
            profile.startGuide(coord.coord);
            return null;
        }
        return null;
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
        return "Navigates player to given coordinate with on-screen action bar arrow";
    }

    @Override
    public String getUsage() {
        return "[<coordinate name> | <x> <y> <z>]";
    }

    @Override
    public LinkedList<String> getExamples() {
        LinkedList<String> examples = new LinkedList<>();
        examples.add("mybase");
        examples.add("monuments/jungletemple1");
        examples.add("4200 69 80085");
        return examples;
    }
}

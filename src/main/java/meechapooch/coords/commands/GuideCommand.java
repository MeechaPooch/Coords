package meechapooch.coords.commands;

import meechapooch.coords.database.CoordEntry;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

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
}
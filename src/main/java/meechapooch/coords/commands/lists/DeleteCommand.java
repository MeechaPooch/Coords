package meechapooch.coords.commands.lists;

import meechapooch.coords.Coords;
import meechapooch.coords.commands.SubCommand;
import meechapooch.coords.database.CoordsList;
import meechapooch.coords.database.FileSaving;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DeleteCommand implements SubCommand {
    @Override
    public boolean isConsoleCompatible() {
        return false;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
        if (args.length == 1) {
            CoordsList list = profile.getList(args[0]);
            if (list == null) return "List " + args[0] + " not found.";
            if (!profile.lists.containsKey(args[0].toLowerCase())) Coords.publicLists.remove(args[0].toLowerCase());
            Coords.index.remove(list.id);
            Coords.profiles.forEach((nm, prof) -> {
                prof.lists.remove(list.getName().toLowerCase());
            });

            FileSaving.writeDatabase();
            return null;
        } else return "Too many args.";
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        if (args.length == 1) return profile.getListNames();
        else return null;
    }
}

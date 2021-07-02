package meechapooch.coords.commands;

import meechapooch.coords.database.FileSaving;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

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
            if(profile.personal.containsKey(args[0].toLowerCase())) {
                profile.personal.remove(args[0].toLowerCase());
                FileSaving.writeDatabase();
                return null;
            } else {
                return "You dont have a personal entry named " + args[0];
            }
        }
        return "";
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) {
            return profile.personal.values().stream().map((coord)->coord.getName()).collect(Collectors.toList());
        }
        return null;
    }
}

package meechapooch.coords.commands;

import meechapooch.coords.database.CoordEntry;
import meechapooch.coords.database.CoordsList;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;

public class InfoCommand implements SubCommand {
    @Override
    public boolean isConsoleCompatible() {
        return false;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
        sender.sendMessage(profile.name.substring(0,1).toUpperCase() + profile.name.substring(1) + "'s Personal List:");
        profile.personal.forEach((k,coord)->sender.sendMessage(coord.getInfo()));
        return null;
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        return null;
    }
}

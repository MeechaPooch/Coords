package meechapooch.coords.commands;

import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    public boolean isConsoleCompatible();
    public String run(CommandSender sender, PlayerProfile profile, String[] args);
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args);
}

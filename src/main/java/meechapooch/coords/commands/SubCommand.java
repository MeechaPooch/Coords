package meechapooch.coords.commands;

import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public interface SubCommand {
    public boolean isConsoleCompatible();
    public String run(CommandSender sender, PlayerProfile profile, String[] args);
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args);
    public default String getDescription() { return "/bro stop asking me dum dum questions"; }
    public default String getUsage() { return "Uhhmmm idk ask someone else"; }
    public default LinkedList<String> getExamples() { return new LinkedList(); }
}

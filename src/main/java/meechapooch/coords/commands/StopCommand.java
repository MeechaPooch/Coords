package meechapooch.coords.commands;

import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public class StopCommand implements SubCommand {
    @Override
    public boolean isConsoleCompatible() {
        return false;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
        profile.stopGuide();
        return null;
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        return null;
    }

    @Override
    public String getDescription() {
        return "Stops current navigation";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public LinkedList<String> getExamples() {
        LinkedList<String> examples = new LinkedList<>();
        examples.add("");
        return examples;
    }
}

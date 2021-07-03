package meechapooch.coords.commands.lists;

import meechapooch.coords.commands.SubCommand;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MakeCommand implements SubCommand {
    @Override
    public boolean isConsoleCompatible() {
        return false;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) return profile.makeNewList(args[0],false);
        else if (args.length == 2) {
            if(args[1].equals("public")) return profile.makeNewList(args[0],true);
            else if(args[1].equals("private")) return profile.makeNewList(args[0],false);
            else return "wtf does \"" + args[1] + "\" mean";
        } else return "Too many arguments.";
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 2) return Arrays.stream(new String[] {"public", "private"}).collect(Collectors.toList());
        else return null;
    }
}

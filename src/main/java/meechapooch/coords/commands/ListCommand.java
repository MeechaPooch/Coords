package meechapooch.coords.commands;

import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class ListCommand extends CommandContainer {

//    public ListsCommand() {
//        subCommands.put();
//    }

    @Override
    public boolean isConsoleCompatible() {
        return false;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
        return null;
    }

    @Override
    public String getNoCommandsMessage() {
        return "Enter a valid list subcommand: (add | remove)";
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) return profile.getListNames();
        return null;
//        if(args.length == 2) return super.autoComplete(sender,profile, Arrays.copyOfRange(args,1,args.length));
    }
}

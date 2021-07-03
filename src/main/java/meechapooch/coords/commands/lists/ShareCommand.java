package meechapooch.coords.commands.lists;

import meechapooch.coords.Coords;
import meechapooch.coords.commands.SubCommand;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShareCommand implements SubCommand {
    @Override
    public boolean isConsoleCompatible() {
        return false;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) return "Must specify players to share with";
        String listName = args[0];
        String[] playerNames = Arrays.copyOfRange(args,1,args.length);
        return profile.shareList(listName,playerNames);
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) return profile.getListNames();
        else return Coords.profiles.values().stream().map(p->p.name).filter(n->n.equalsIgnoreCase(profile.name)).collect(Collectors.toList());
    }
}

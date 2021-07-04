package meechapooch.coords.commands;

import meechapooch.coords.Coords;
import meechapooch.coords.database.CoordEntry;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SendCommand implements SubCommand {
    @Override
    public boolean isConsoleCompatible() {
        return true;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length > 1) {
            CoordEntry toSend;
            if(args[0].equals("~")) {
                if(! (sender instanceof Player)) return "Only players can use the ~ location argument";
                toSend = new CoordEntry(sender.getName(),((Player)sender).getLocation());
            } else toSend = profile.getCoordEntry(args[0]);
            if(toSend == null) return "Could not find coordinate " + args[0] + ".";
            String err = "";
            for (int i = 1; i < args.length; i++) {
                PlayerProfile recipient = Coords.profiles.get(args[i].toLowerCase());
                if(recipient == null) err += "Could not find player " + args[i] + ".\n";
                else if(!recipient.isOnline()) err += recipient.name + " is not online.\n";
                else {
                    toSend = new CoordEntry(toSend.name + "(shared)",toSend.coord);
                    recipient.personal.put(toSend.name.toLowerCase(),toSend);
                    recipient.getPlayer().sendMessage(sender.getName() + " sent you a coordinate: " + toSend.getName());
                }
            }
            if(err.equals("")) return null;
            else return err;
        } else return "Must input recipients! /coords send <coordname or '~'> <players>";
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        if (args.length == 1) {
            LinkedList<String> ret = new LinkedList<>();
            ret.add("~");
            ret.addAll(profile.getAllPaths());
            return ret;
        } else return Coords.plugin.getServer().getOnlinePlayers().stream().map(p -> p.getName()).collect(Collectors.toList());
    }
}

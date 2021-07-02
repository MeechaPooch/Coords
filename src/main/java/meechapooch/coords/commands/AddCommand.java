package meechapooch.coords.commands;

import meechapooch.coords.database.CoordEntry;
import meechapooch.coords.database.FileSaving;
import meechapooch.coords.database.PlayerProfile;
import meechapooch.coords.utils.Randomz;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class AddCommand implements SubCommand {
    @Override
    public boolean isConsoleCompatible() {
        return false;
    }

    @Override
    public String run(CommandSender sender, PlayerProfile profile, String[] args) {
         Player player = (Player) sender;
         if(args.length == 1) {
             return "Boi, you gotta have them POSITIONAL ARGUMENTS my duuuuuuude";
         }
         if(args.length > 1 && profile.personal.containsKey(args[0].toLowerCase())) {
             sender.sendMessage("Overwriting existing coordinate named '" + profile.personal.get(args[0].toLowerCase()).getName() + "'...");
         }
        if(args.length == 2) {
            if(args[1].equals("~")) {
                String name = args[0];
                Location location = player.getLocation();
                profile.personal.put(name.toLowerCase(),new CoordEntry(name,player.getWorld(),location.getX(),location.getY(),location.getZ()));
                FileSaving.writeDatabase();
                return null;
            } else {
                return args[1] + " is not a valid position argument-- must be ~ or three numbers separated by spaces";
            }
        } else if(args.length == 4) {
            try {
                profile.personal.put(args[0].toLowerCase(),new CoordEntry(args[0],player.getWorld(),Double.parseDouble(args[1]),Double.parseDouble(args[2]),Double.parseDouble(args[3])));
                FileSaving.writeDatabase();
                return null;
            } catch (NumberFormatException e) {
                return "All three position arguments must be numbers";
            }
        } else return "";
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) return null;
        if(args.length == 2) {
            LinkedList<String> ret = new LinkedList<>();
            ret.add("~");
            ret.add("0");
            ret.add(Randomz.intRange(-3000,3000) + "");
            return ret;
        } else if(args.length == 3) {
            LinkedList<String> ret = new LinkedList<>();
            ret.add("0");
            ret.add(Randomz.intRange(0,64) + "");
            return ret;
        } else if(args.length == 4) {
            LinkedList<String> ret = new LinkedList<>();
            ret.add("0");
            ret.add(Randomz.intRange(-3000,3000) + "");
            return ret;
        } else return null;
    }
}

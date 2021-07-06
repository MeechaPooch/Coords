package meechapooch.coords.commands;

import meechapooch.coords.database.CoordEntry;
import meechapooch.coords.database.CoordsList;
import meechapooch.coords.database.FileSaving;
import meechapooch.coords.database.PlayerProfile;
import meechapooch.coords.utils.Randomz;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
         else if (args.length > 1) {
             // Check path existence
             String[] path = args[0].split("/");
             String listName = path[0];
             String coordName = path[path.length-1];
             HashMap<String, CoordEntry> list = profile.resolve(path);
             if (list == null) return "List '" + listName + "' does not exist.";
             if (profile.hasCoord(args[0])) {
                 if (path.length > 1)
                     sender.sendMessage("Overwriting existing personal coordinate named '" + profile.getCoordEntry(path).getName() + "'...");
                 else
                     sender.sendMessage("Overwriting existing coordinate named '" + args[0].toLowerCase() + "' in list " + path[0] + "...");
             }

             if (args.length == 2) {
                 if (args[1].equals("~")) {
                     Location location = player.getLocation();
                     list.put(coordName.toLowerCase(), new CoordEntry(coordName, player.getWorld(), location.getX(), location.getY(), location.getZ()));
                     FileSaving.writeDatabase();
                     profile.getPlayer().sendMessage("Coord " + args[0] + " added");
                     return null;
                 } else if (profile.hasCoord(args[1])) {
                     Location location = profile.getCoordEntry(args[1]).coord;
                     list.put(coordName.toLowerCase(), new CoordEntry(coordName, player.getWorld(), location.getX(), location.getY(), location.getZ()));
                     FileSaving.writeDatabase();
                     profile.getPlayer().sendMessage("Coord " + args[1] + " duplicated to" + args[0]);
                     return null;
                 } else {
                     return args[1] + " is not a valid position argument-- must be ~, a path to/name of an existing coordinate, or three numbers separated by spaces";
                 }
             } else if (args.length == 4) {
                 try {
                     list.put(coordName.toLowerCase(), new CoordEntry(coordName, player.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3])));
                     FileSaving.writeDatabase();
                     profile.getPlayer().sendMessage("Coord " + args[0] + " added");
                     return null;
                 } catch (NumberFormatException e) {
                     return "All three position arguments must be numbers";
                 }
             } else return "";
         } else return "";
    }

    @Override
    public List<String> autoComplete(CommandSender sender, PlayerProfile profile, String[] args) {
        if(args.length == 1) return profile.getListNames().stream().map(listName->listName+"/").collect(Collectors.toList());
        if(args.length == 2) {
            LinkedList<String> ret = new LinkedList<>();
            ret.add("~");
            ret.add("0");
            ret.add(Randomz.intRange(-3000,3000) + "");
            ret.addAll(profile.getListNames().stream().map(listName->listName+"/").collect(Collectors.toList()));
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

    @Override
    public String getDescription() {
        return "Adds a named coordinate entry to a personal or shared/public list";
    }

    @Override
    public String getUsage() {
        return "<coordinate name/path> [ ~ | <x> <y> <z> | <existing coordinate path>]";
    }

    @Override
    public LinkedList<String> getExamples() {
        LinkedList<String> examples = new LinkedList<>();
        examples.add("beautiful-clearing ~");
        examples.add("spawn 0 64 0");
        examples.add("bases/PokeNinjaGuy ~");
        examples.add("bases/ebonboyger friends/dylan");
        return examples;
    }
}

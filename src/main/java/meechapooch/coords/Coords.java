package meechapooch.coords;

import meechapooch.coords.commands.*;
import meechapooch.coords.database.CoordsList;
import meechapooch.coords.database.FileSaving;
import meechapooch.coords.database.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public final class Coords extends JavaPlugin {

    public static Map<String, PlayerProfile> profiles = new HashMap<>();
    public static HashMap<String, CoordsList> publicLists = new HashMap<>();
    public static HashMap<Integer, CoordsList> index = new HashMap<>();
    public static Coords plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getLogger().info("YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEET!");

        FileSaving.setupFileLocation();
        FileSaving.buildDatabase();

        getServer().getPluginManager().registerEvents(
                new Listener() {
                    @EventHandler
                    public void onPlayerJoin(PlayerJoinEvent playerJoin) {
                        setupProfile(playerJoin.getPlayer().getName());
                    }
                }, this);


        for (OfflinePlayer offlinePlayer : getServer().getOfflinePlayers()) {
            String offlineName = offlinePlayer.getName();
            if (offlineName != null) setupProfile(offlineName);
        }

        FileSaving.writeDatabase();
    }

    public static HashMap<String, SubCommand> subCommands = new HashMap<>();

    static {
        subCommands.put("help", new HelpCommand());
        subCommands.put("add", new AddCommand());
        subCommands.put("remove", new RemoveCommand());
        subCommands.put("guide", new GuideCommand());
        subCommands.put("stop", new CancelCommand());
        subCommands.put("lists", new ListsCommand());
        subCommands.put("send", new HelpCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;
        String commandName = args[0].toLowerCase();
        SubCommand subCommand = subCommands.get(commandName);
        if (subCommand == null) {
            sender.sendMessage(commandName + " is not a valid subcommand you stoumpugening dilt!");
            return false;
        } else if (sender instanceof ConsoleCommandSender && !subCommand.isConsoleCompatible()) {
            sender.sendMessage("You cant run that command from the console you merping freak");
            return true;
        } else {
            String error = subCommand.run(sender, profiles.get(sender.getName().toLowerCase()), Arrays.copyOfRange(args, 1, args.length));
            if (error == null) return true;
            else if (error.equals("")) {
                sender.sendMessage(ChatColor.DARK_RED + "Unknown error! Run /coords help <subCommand>");
                return true;
            } else {
                sender.sendMessage(ChatColor.DARK_RED + error);
                return true;
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return subCommands.keySet().stream().filter((String string) -> string.startsWith(args[0])).collect(Collectors.toList());
        }
        String commandName = args[0].toLowerCase();
        SubCommand subCommand = subCommands.get(commandName);
        if (subCommand == null) {
            return null;
        } else if (sender instanceof ConsoleCommandSender && !subCommand.isConsoleCompatible()) {
            return null;
        } else {
            List<String> bois = subCommand.autoComplete(sender, profiles.get(sender.getName().toLowerCase()), Arrays.copyOfRange(args, 1, args.length));
            return bois == null ? new LinkedList<>() : bois.stream().filter((String string) -> string.toLowerCase().startsWith(args[args.length - 1].toLowerCase())).collect(Collectors.toList());
        }
    }

    @Override
    public void onDisable() {
        FileSaving.writeDatabase();
    }

    public static void setupProfile(String name) {
        if (profiles.containsKey(name.toLowerCase())) return;
        profiles.put(name.toLowerCase(), new PlayerProfile(name));
        FileSaving.writeDatabase();
    }


    public static void makeNewList(Player owner, String name, boolean isPublic) {
        CoordsList newList = new CoordsList(name, ++FileSaving.lastId, isPublic);
        if (isPublic) publicLists.put(name.toLowerCase(), newList);
        else profiles.get(owner.getName().toLowerCase()).add(newList);

        index.put(FileSaving.lastId, newList);
        FileSaving.writeDatabase();
    }

    public static String shareList(CoordsList list, List<PlayerProfile> players) {
        String ret = "";
        if (list.isPublic) return list.getName() + " is a public list, everyone already has access.";
        for (PlayerProfile profile : players) {
            if (profile.lists.containsKey(list.getName().toLowerCase()) && profile.lists.get(list.getName().toLowerCase()) != list) {
                ret += profile.name + " Already has a list named '" + list.getName() + "'. Ask them to delete it\n";
                continue;
            }
            profile.add(list);
        }
        FileSaving.writeDatabase();
        if (ret.equals("")) return null;
        else return ret;
    }

    public static String shareList(CoordsList list, String... players) {
        String ret = "";
        LinkedList<PlayerProfile> bois = new LinkedList<>();
        for (String pName : players) {
            PlayerProfile profile = profiles.get(pName);
            if(profile == null) {
                ret += "Cannot recognize player " + pName + ".\n";
                continue;
            } else bois.add(profile);
        }
        String hmmm = shareList(list,bois);
        if(hmmm != null) ret += hmmm;
        if(ret.equals("")) return null;
        else return ret;
    }

}

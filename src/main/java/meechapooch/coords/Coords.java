package meechapooch.coords;

import meechapooch.coords.commands.HelpCommand;
import meechapooch.coords.commands.SubCommand;
import meechapooch.coords.database.CoordsList;
import meechapooch.coords.database.FileSaving;
import meechapooch.coords.database.PlayerProfile;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Console;
import java.nio.file.Files;
import java.util.*;

public final class Coords extends JavaPlugin {

    public static Map<String, PlayerProfile> profiles = new HashMap<>();
    public static HashMap<String, CoordsList> globalLists = new HashMap<>();
    public static HashMap<Integer, CoordsList> index = new HashMap<>();
    public static Coords plugin;

    @Override
    public void onEnable() {
        plugin = this;

//        p.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("Test"));
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
            if(offlineName != null) setupProfile(offlineName);
        }

        FileSaving.writeDatabase();
    }

    static HashMap<String, SubCommand> subCommands = new HashMap<>();
    static {
        subCommands.put("help",new HelpCommand());
        subCommands.put("add",new HelpCommand());
        subCommands.put("remove",new HelpCommand());
        subCommands.put("guide",new HelpCommand());
        subCommands.put("lists",new HelpCommand());
        subCommands.put("list",new HelpCommand());
        subCommands.put("send",new HelpCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = args[0].toLowerCase();
        SubCommand subCommand = subCommands.get(commandName);
        if(subCommand == null) {
            sender.sendMessage(commandName + " is not a valid subcommand you stoumpugening dilt!");
            return false;
        }
        else if(sender instanceof ConsoleCommandSender && !subCommand.isSonsoleCompatible()) {
            sender.sendMessage("You cant run that command from the console you merping freak");
            return false;
        }
        else {
            return subCommand.run(sender, Arrays.copyOfRange(args,1,args.length));
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
        CoordsList newList = new CoordsList(owner.getName(), ++FileSaving.lastId, isPublic);
        profiles.get(owner.getName().toLowerCase()).add(newList);
    }

    public static void shareList(CoordsList list, Player... players) {
        for (Player player : players) {
            PlayerProfile profile = profiles.get(player.getName().toLowerCase());
            if (profile.lists.containsKey(list.getName().toLowerCase())) //TODO Handle share conflict
                profile.add(list);
        }
    }

}

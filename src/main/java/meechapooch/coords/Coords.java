package meechapooch.coords;

import meechapooch.coords.database.CoordsList;
import meechapooch.coords.database.PlayerProfile;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public final class Coords extends JavaPlugin {

    Map<String, PlayerProfile> profiles;
    public static HashMap<String,CoordsList> globalLists;


    @Override
    public void onEnable() {
        // Plugin startup logic
//        p.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("Test"));
        getLogger().info("YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEET!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }





    public void makeNewList(Player owner, String name) {
        CoordsList list = new CoordsList();
        profiles.get(owner).add(list);
    }

    public void shareList(CoordsList list, Player... players) {
        for(Player player : players) {
            PlayerProfile profile = profiles.get(player.getName().toLowerCase());
            if(profile.lists.containsKey(list.getName().toLowerCase())) //TODO Handle share conflict
                profile.add(list);
        }
    }

}

package meechapooch.coords.in_game;

import meechapooch.coords.database.CoordEntry;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Guide extends BukkitRunnable {

    Player player;
    Location to;

    public Guide(Player player, Location to) {
        this.player = player;
        this.to = to;
    }

    @Override
    public void run() {
        if(!player.isOnline()) this.cancel();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("Test"));
    }
}

package meechapooch.coords.ingame;

import meechapooch.coords.Coords;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FlashingMessage extends BukkitRunnable {

    boolean on = true;
    Player player;
    String message;
    ChatColor color;
    double period;
    int reps;
    int repCount = 0;
    long lastTime = 0;

    public FlashingMessage(Player player, String message, ChatColor color, double period, int reps) {
        this.player = player;
        this.message = message;
        this.color = color;
        this.period = period;
        this.reps = reps;
    }

    @Override
    public void run() {
        if (System.currentTimeMillis() > lastTime + period * 1000) {
            if(repCount >= reps) this.cancel();
            lastTime = System.currentTimeMillis();
            if(on) player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.WHITE + "[     " + ChatColor.BOLD + ChatColor.UNDERLINE + color + message + ChatColor.RESET + "     ]"));
            else {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
                repCount++;
            }
            on = !on;
        }
    }

    public void start() {
        lastTime = 0;
        repCount = 0;
        this.runTaskTimer(Coords.plugin, 0, 1);
    }
}

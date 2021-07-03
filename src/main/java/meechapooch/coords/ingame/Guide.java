package meechapooch.coords.ingame;

import meechapooch.coords.utils.Vector2D;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Guide extends BukkitRunnable {

    Player player;
    Location to;
    public static int HALF_WIDTH = 20;
    public static double FOV = 0.65;
    ChatColor color;

    public Guide(Player player, Location to) {
        this.player = player;
        this.to = to;
    }

    @Override
    public void run() {
        if(!player.isOnline()) this.cancel();
        int distance = getDistance();
        int elevation = getRelativeElevation();
        color = (distance > 1000 ? ChatColor.RED : distance>200 ? ChatColor.GOLD : ChatColor.GREEN);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(
                color.toString() + distance + "m "
                + buildBar(getArrowIndex())
                + " " + color.toString() + (elevation > 0 ? "▲" : elevation == 0 ? "■" : "▼")
                + " " + elevation + "m"
        ));
    }

    public int getArrowIndex() {
        double angle = getAngle();
        angle /= 180;
        // angle goes from -1 to 1
        angle = Math.min(Math.max(-FOV/2, angle),FOV/2);
        angle /= FOV/2;
        angle *= HALF_WIDTH;
        int ret = (int)angle;
//        ret = Math.min(Math.max(-HALF_WIDTH + 1, ret),HALF_WIDTH - 1);
        return ret;
    }

    public String buildBar(int index) {
        String ret = ChatColor.WHITE + "[";
        if(index == 0) {
            ret += spaces(HALF_WIDTH - 1);
            ret += color + "(" + ChatColor.WHITE + "^" + color + ")" + ChatColor.WHITE;
            ret += spaces(HALF_WIDTH - 1);
        } else if (index <= -HALF_WIDTH) {
            ret += color + "<-" + ChatColor.WHITE + spaces(HALF_WIDTH - 2) + "^" + spaces(HALF_WIDTH);
        } else if (index >= HALF_WIDTH) {
            ret += spaces(HALF_WIDTH) + "^" + spaces(HALF_WIDTH - 2) + color + "->" + ChatColor.WHITE;
        } else if (index < 0) {
            ret += spaces(HALF_WIDTH + index - 1);
            ret += color + "()" + ChatColor.WHITE;
            ret += spaces(-index - 1);
            ret += "^";
            ret += spaces(HALF_WIDTH);
        } else if (index > 0) {
            ret += spaces(HALF_WIDTH) + "^";
            ret += spaces(index - 1);
            ret += color + "()" + ChatColor.WHITE;
            ret += spaces(HALF_WIDTH - index - 1);
        }
        ret += ChatColor.WHITE + "]";
        return ret;
    }

    private String spaces(int num) {
        String ret = "";
        for (int i = 0; i < num; i++) {
            ret += " ";
        }
        return ret;
    }

    public double getAngle() {
        // Todo nether in overworld
        Vector2D playerPosition = new Vector2D(player.getLocation().getX(),player.getLocation().getZ());
        Vector2D coordinatePosition = new Vector2D(to.getX(),to.getZ());
        double angleRad = coordinatePosition.getSubtracted(playerPosition).getAngle();
        double angleDeg = Math.toDegrees(angleRad);
        double mod = (player.getLocation().getYaw() - angleDeg - 90) % 360;
        if(mod < 0) mod += 360;
        mod-=180;
        mod *= -1;
        return mod;
    }

    public int getDistance() {
        Vector2D playerPosition = new Vector2D(player.getLocation().getX(),player.getLocation().getZ());
        Vector2D coordinatePosition = new Vector2D(to.getX(),to.getZ());
        return (int) playerPosition.distance(coordinatePosition);
    }

    public int getRelativeElevation() {
        return to.getBlockY() - player.getLocation().getBlockY();
    }
}

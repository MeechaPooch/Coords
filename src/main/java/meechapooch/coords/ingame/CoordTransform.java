package meechapooch.coords.ingame;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public enum CoordTransform {

    TO_OVERWORLD(8, "" + ChatColor.RED + ChatColor.ITALIC + " (nether, scaled)"),
    TO_NETHER(1d/8, "" + ChatColor.GREEN + ChatColor.ITALIC + " +(overworld, scaled)"),
    SAME(1,""),
    INCOMPATIBLE(1, "");

    static String NETHER = "world_nether";
    static String OVERWORLD = "world";
    static String END_WORLD = "world_the_end";

    static public CoordTransform getFromNames(String player, String to) {
        if(player.equals(to)) return SAME;
        else if (player.equals(NETHER) && to.equals(OVERWORLD)) return TO_NETHER;
        else if (player.equals(OVERWORLD) && to.equals(NETHER)) return TO_OVERWORLD;
        else return INCOMPATIBLE;
    }

    double sf;
    String message;

    CoordTransform(double sf, String message) {
        this.sf = sf;
        this.message = message;
    }

    public double transform(double coord) {
        return coord * sf;
    }

    public SimpleLocation getTransformedLocation(Location location) {
        return new SimpleLocation(location.getX() * sf, location.getY(), location.getZ() * sf);
    }

    public String getAppend() {
        return message;
    }
}

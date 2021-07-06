package meechapooch.coords.database;

import meechapooch.coords.Coords;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;

public class CoordEntry {
    public String name;
    public Location coord;

    public CoordEntry(String name, World world, double x, double y, double z) {
        this.name = name;
        this.coord = new Location(world, x, y, z);
    }
    public CoordEntry(String name, Location location) {
        this.name = name;
        this.coord = location;
    }

    public String getInfo() {
        String ret = "" + ChatColor.RESET + getName() + " [" + ChatColor.BOLD + coord.getBlockX() + ", " + coord.getBlockY() + ", " + coord.getBlockZ() + ChatColor.RESET + "]";
        if(coord.getWorld().getName().equals("world_nether")) ret += "" + ChatColor.RED + ChatColor.BOLD + " (nether)";
        if(coord.getWorld().getName().equals("world_the_end")) ret += "" + ChatColor.BLUE + ChatColor.BOLD + " (end)";
        return ret;
    }

    public String getName() {
        return name;
    }
}

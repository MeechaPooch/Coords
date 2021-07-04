package meechapooch.coords.database;

import meechapooch.coords.Coords;
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
        String ret = getName() + " - " + coord.getX() + ", " + coord.getY() + ", " + coord.getZ();
        if(coord.getWorld().getName().equals("world_nether")) ret += " (nether)";
        if(coord.getWorld().getName().equals("world_the_end")) ret += " (end)";
        return ret;
    }

    public String getName() {
        return name;
    }
}

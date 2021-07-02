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


    public String getName() {
        return name;
    }
}

package meechapooch.coords.database;

import org.bukkit.Location;
import org.bukkit.World;

public class CoordEntry {
    String name;
    Location coord;

    public CoordEntry(String name, World world, double x, double y, double z) {
        this.name = name;
        this.coord = new Location(world,x,y,z);
    }


    public String getName() {
        return name;
    }
}

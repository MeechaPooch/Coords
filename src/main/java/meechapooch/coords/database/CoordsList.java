package meechapooch.coords.database;

import org.bukkit.Location;

import java.util.LinkedList;
import java.util.List;

public class CoordsList {

    String name;
    LinkedList<CoordEntry> coords;

    public String getName() {
        return name;
    }

    public List<String> getCoordNames() {
        LinkedList<String> ret = new LinkedList<>();
        coords.forEach(coordEntry -> ret.add(coordEntry.getName()));
        return ret;
    }
}

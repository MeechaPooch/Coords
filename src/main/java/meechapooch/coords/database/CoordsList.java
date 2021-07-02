package meechapooch.coords.database;

import org.bukkit.Location;

import java.util.LinkedList;
import java.util.List;

public class CoordsList {

    String name;
    int id;
    LinkedList<CoordEntry> coords = new LinkedList<>();
    boolean isPublic;

    public CoordsList(String name, int id, boolean isPublic) {
        this.name = name;
        this.id = id;
        this.isPublic = isPublic;
    }

    public String getName() {
        return name;
    }

    public List<String> getCoordNames() {
        LinkedList<String> ret = new LinkedList<>();
        coords.forEach(coordEntry -> ret.add(coordEntry.getName()));
        return ret;
    }
}

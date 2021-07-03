package meechapooch.coords.database;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CoordsList {

    String name;
    public int id;
    HashMap<String,CoordEntry> coords = new HashMap<>();
    public boolean isPublic;

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
        coords.forEach((k,coordEntry) -> ret.add(coordEntry.getName()));
        return ret;
    }
}

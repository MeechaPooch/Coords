package meechapooch.coords.database;

import meechapooch.coords.Coords;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;

public class PlayerProfile {
    public String name;
    public LinkedList<CoordEntry> personal = new LinkedList<>();
    public HashMap<String, CoordsList> lists = new HashMap<>();

    public PlayerProfile(String name) {
        this.name = name;
    }


    public void add(CoordsList list) {
        lists.put(list.getName().toLowerCase(),list);
    }


    public List<String> getCoordNames() {
        LinkedList<String> ret = new LinkedList<>();
        personal.forEach(coord->ret.add(coord.getName()));
        lists.forEach((k,v)->ret.addAll(v.getCoordNames()));
        Coords.globalLists.forEach((k,v)->ret.addAll(v.getCoordNames()));
        return ret;
    }

    public List<String> getListNames() {
        LinkedList<String> ret = new LinkedList<>();
//        ret.add(personal.getName());
        lists.forEach((k,v)->ret.add(v.getName()));
        Coords.globalLists.forEach((k,v)->ret.add(v.getName()));
        return ret;
    }
}

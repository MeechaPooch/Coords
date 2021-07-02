package meechapooch.coords.database;

import meechapooch.coords.Coords;
import meechapooch.coords.in_game.Guide;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;

public class PlayerProfile {

    public Guide lastActiveGuide = null;

    public String name;
    public HashMap<String,CoordEntry> personal = new HashMap();
//    public LinkedList<CoordEntry> personal = new LinkedList<>();
    public HashMap<String, CoordsList> lists = new HashMap<>();

    public PlayerProfile(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return Coords.plugin.getServer().getPlayer(name);
    }
    public boolean isGuided() {
        return lastActiveGuide != null && !lastActiveGuide.isCancelled();
    }

    public HashMap<String,CoordEntry> resolve(String path) {
        String[] split = path.split("/");
        if(split.length > 2) return null;
        if(split.length == 1) return personal;
        if(split.length == 2); //Todo resolve shared & public lists
        return null;
    }

    public void add(CoordsList list) {
        lists.put(list.getName().toLowerCase(),list);
    }


    public List<String> getCoordNames() {
        LinkedList<String> ret = new LinkedList<>();
        personal.forEach((name,coord)->ret.add(coord.getName()));
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

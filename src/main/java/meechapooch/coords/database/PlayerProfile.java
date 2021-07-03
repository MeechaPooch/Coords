package meechapooch.coords.database;

import meechapooch.coords.Coords;
import meechapooch.coords.ingame.Guide;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PlayerProfile {

    public Guide lastActiveGuide = null;

    public String name;
    public HashMap<String, CoordEntry> personal = new HashMap();
    //    public LinkedList<CoordEntry> personal = new LinkedList<>();
    public HashMap<String, CoordsList> lists = new HashMap<>();

    public PlayerProfile(String name) {
        this.name = name;
    }

    public void startGuide(Location location) {
        stopGuide();
        Player player = Coords.plugin.getServer().getPlayer(name);
        Guide guide = new Guide(player, location);
        lastActiveGuide = guide;
        guide.runTaskTimer(Coords.plugin, 0, 1);
    }

    public List<String> getAllPaths() {
        LinkedList<String> ret = new LinkedList<>();
        personal.forEach((key,entry)->ret.add(entry.getName()));
        lists.forEach((key,list)->list.coords.forEach((k,entry)->ret.add(list.getName() + "/" + entry.getName())));
        Coords.publicLists.forEach((key,list)->list.coords.forEach((k,entry)->ret.add(list.getName() + "/" + entry.getName())));
        return ret;
    }

    public boolean hasCoord(String path) {
        return getCoordEntry(path) != null;
    }

    public void stopGuide() {
        if (isGuided()) lastActiveGuide.cancel();
    }

    public Player getPlayer() {
        return Coords.plugin.getServer().getPlayer(name);
    }

    public boolean isGuided() {
        return lastActiveGuide != null && !lastActiveGuide.isCancelled();
    }

    public HashMap<String, CoordEntry> resolve(String... path) {
        if (path.length > 2) return null;
        if (path.length == 1) return personal;
        if (path.length == 2) {
            String listName = path[0];
            if(lists.containsKey(listName.toLowerCase())) return lists.get(listName.toLowerCase()).coords;
            else if(Coords.publicLists.containsKey(listName.toLowerCase())) return Coords.publicLists.get(listName.toLowerCase()).coords;
        }
        return null;
    }
    public HashMap<String, CoordEntry> resolve(String path) {
        return resolve(path.split("/"));
    }

    public CoordEntry getCoordEntry(String... path) {
        HashMap<String, CoordEntry> list = resolve(path);
        if (list == null) return null;
        String key = path[path.length - 1].toLowerCase();
        return list.getOrDefault(key, null);
    }

    public CoordEntry getCoordEntry(String path) {
        return getCoordEntry(path.split("/"));
    }

    public void add(CoordsList list) {
        lists.put(list.getName().toLowerCase(), list);
    }

    public String makeNewList(String name, boolean isPublic) {
        if (getList(name) != null) return "A list named '" + name + "' already exists!";
        Coords.makeNewList(this.getPlayer(), name, isPublic);
        return null;
    }

    public String shareList(String name, String... shareTo) {
        CoordsList list = getList(name.toLowerCase());
        if(list == null) return "List " + name + " does not exist.";

        return Coords.shareList(list,shareTo);
    }


    public CoordsList getList(String name) {
        if (lists.containsKey(name.toLowerCase())) return lists.get(name.toLowerCase());
        else return Coords.publicLists.get(name.toLowerCase());
    }

    public List<String> getCoordNames() {
        LinkedList<String> ret = new LinkedList<>();
        personal.forEach((name, coord) -> ret.add(coord.getName()));
        lists.forEach((k, v) -> ret.addAll(v.getCoordNames()));
        Coords.publicLists.forEach((k, v) -> ret.addAll(v.getCoordNames()));
        return ret;
    }

    public List<String> getListNames() {
        LinkedList<String> ret = new LinkedList<>();
//        ret.add(personal.getName());
        lists.forEach((k, v) -> ret.add(v.getName()));
        Coords.publicLists.forEach((k, v) -> ret.add(v.getName()));
        return ret;
    }
}

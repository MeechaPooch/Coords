package meechapooch.coords.database;

import meechapooch.coords.Coords;
import meechapooch.coords.utils.FileUtils;
import meechapooch.coords.utils.JsonThing;
import org.bukkit.WorldType;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class FileSaving {

    public static final String SAVE_DIR = Coords.plugin.getDataFolder() + "";
    public static final String SAVE_PATH = SAVE_DIR + File.separator + "coords.json";
    public static File saveFile = new File(SAVE_PATH);

    public static int lastId = 0;

    public static void writeDatabase() {
        JsonThing container = JsonThing.newMap();

        // Next Id
        container.put("lastId",lastId);

        // Index
        JsonThing index = JsonThing.newList();
        Coords.index.forEach((id,list)->{
            JsonThing listJson = JsonThing.newList();
            listJson.put("id",list.id);
            listJson.put("name",list.getName());
            listJson.put("public",list.isPublic);
            JsonThing coordsList = JsonThing.newList();
            list.coords.forEach((k,coord)->{
                JsonThing coordObject = JsonThing.newMap();
                coordObject.put("name",coord.getName());
                coordObject.put("world",coord.coord.getWorld().getName());
                coordObject.put("x",coord.coord.getX());
                coordObject.put("y",coord.coord.getY());
                coordObject.put("z",coord.coord.getZ());
                coordsList.add(coordObject);
            });
            listJson.put("coords",coordsList);
            index.add(listJson);
        });
        container.put("index",index);

        // Profiles
        JsonThing profiles = JsonThing.newList();
        Coords.profiles.forEach((name,profile)->{
            JsonThing profileObject = JsonThing.newMap();
            profileObject.put("name",profile.name);
            JsonThing listIds = JsonThing.newList();
            profile.lists.forEach((listname,list)->{
                listIds.add(list.id);
            });
            profileObject.put("lists",listIds);
            JsonThing personalList = JsonThing.newList();
            profile.personal.forEach((nm,boi)->{
                JsonThing personalListEntry = JsonThing.newMap();
                personalListEntry.put("name",boi.name);
                personalListEntry.put("world",boi.coord.getWorld().getName());
                personalListEntry.put("x",boi.coord.getX());
                personalListEntry.put("y",boi.coord.getY());
                personalListEntry.put("z",boi.coord.getZ());
                personalList.add(personalListEntry);
            });
            profileObject.put("personalList",personalList);
            profiles.add(profileObject);
        });
        container.put("profiles",profiles);

        try {
            String JsonString = container.toJson();
            FileUtils.writeString(SAVE_PATH,JsonString);
        } catch (IOException e) {
            System.out.println("Database Save IO Error");
        }
    }

    public static void setupFileLocation() {
        File saveDir = new File(SAVE_DIR);
        saveDir.mkdirs();
    }

    public static void buildDatabase() {
        if (saveFile.exists()) {
            Coords.index.clear();
            try {
                JsonThing database = JsonThing.parse(FileUtils.readLineByLineJava8(SAVE_PATH));

                // Set Next Id
                lastId = database.get("lastId").asLong().intValue();

                // Build Index
                JsonThing index = database.get("index");
                index.asListOfThings().forEach(list -> {
                    CoordsList newList = new CoordsList(list.get("name").asString(), list.get("id").asLong().intValue(), list.get("public").asBoolean());
                    list.get("coords").asListOfThings().forEach(coord->{
                        CoordEntry newCoord = new CoordEntry(
                                coord.get("name").asString(),
                                Coords.plugin.getServer().getWorld(coord.get("world").asString()),
                                coord.get("x").asDouble(),
                                coord.get("y").asDouble(),
                                coord.get("z").asDouble()
                        );
                        newList.coords.put(newCoord.getName().toLowerCase(),newCoord);
                    });
                    Coords.index.put(newList.id,newList);
                });

                // Build Profiles
                JsonThing profiles = database.get("profiles");
                profiles.asListOfThings().forEach(profile->{
                    PlayerProfile newProfile = new PlayerProfile(profile.get("name").asString());
                    // Build Personal List
                    profile.get("personalList").asListOfThings().forEach(coord->{
                        CoordEntry newCoord = new CoordEntry(
                                coord.get("name").asString(),
                                Coords.plugin.getServer().getWorld(coord.get("world").asString()),
                                coord.get("x").asDouble(),
                                coord.get("y").asDouble(),
                                coord.get("z").asDouble()
                        );
                        newProfile.personal.put(newCoord.getName().toLowerCase(),newCoord);
                    });
                    // Add Shared List References
                    profile.get("lists").asListOfThings().forEach(listId->{
                        CoordsList listReference = Coords.index.get(listId.asLong().intValue());
                        newProfile.lists.put(listReference.getName().toLowerCase(),listReference);
                    });


                    // Put to Plugin Profile Map
                    Coords.profiles.put(profile.get("name").asString().toLowerCase(),newProfile);
                });

            } catch (IOException e) {
                e.printStackTrace();
                Coords.getPlugin(Coords.class).getLogger().log(Level.SEVERE, "CRITICAL DATABAES FILE READING ERROR.");
            }
        }
    }
}

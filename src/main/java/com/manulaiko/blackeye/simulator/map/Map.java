package com.manulaiko.blackeye.simulator.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.manulaiko.blackeye.simulator.portal.Portal;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.blackeye.simulator.collectable.Collectable;
import com.manulaiko.blackeye.simulator.station.Station;

/**
 * Map class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator
 */
public class Map
{
    /**
     * Map ID
     */
    public int id = 0;

    /**
     * Map name
     */
    public String name = "";

    /**
     * Map size
     */
    public Point limits = new Point(0, 0);

    /**
     * Map portals
     */
    public HashMap<Integer, Portal> portals = new HashMap<>();

    /**
     * NPCS
     */
    public HashMap<Integer, NPC> npcs = new HashMap<>();

    /**
     * Collectables
     */
    public HashMap<Integer, Collectable> collectables = new HashMap<>();

    /**
     * Stations
     */
    public ArrayList<Station> stations = new ArrayList<>();

    /**
     * Whether map is pvp or not
     */
    public boolean isPVP = false;

    /**
     * Whether map is starter map
     */
    public boolean isStarter = false;

    /**
     * Map's owner
     */
    public int factionsID = -1;

    /**
     * NPCs' JSON
     */
    public JSONArray npcsJSON;

    /**
     * Stations' JSON
     */
    public JSONArray stationsJSON;

    /**
     * Collectables' JSON
     */
    public JSONArray collectablesJSON;

    /**
     * Constructor
     *
     * @param id           Map id
     * @param factionsID   Map's owner faction
     * @param isPVP        Whether map is a pvp map or not
     * @param isStarter    Whether map is a starter map or not
     * @param name         Map name
     * @param limits       Map limits
     * @param npcs         NPCs' JSON
     * @param stations     Stations' JSON
     * @param collectables Collectables' JSON
     */
    public Map(
            int id, int factionsID, boolean isPVP, boolean isStarter, String name,
            Point limits, JSONArray npcs, JSONArray stations, JSONArray collectables
    ) {
        this.id               = id;
        this.factionsID       = factionsID;
        this.isPVP            = isPVP;
        this.isStarter        = isStarter;
        this.name             = name;
        this.limits           = limits;
        this.npcsJSON         = npcs;
        this.stationsJSON     = stations;
        this.collectablesJSON = collectables;
    }

    /**
     * Adds a NPC to the array
     *
     * @param npc NPC to add
     */
    public void addNPC(NPC npc)
    {
        int id = -this.npcs.size();

        if(id <= -2147483647) {
            id = 0;
        }

        this.npcs.put(id--, npc);
    }

    /**
     * Adds a portal to the array
     *
     * @param portal Portal to add
     */
    public void addPortal(Portal portal)
    {
        this.portals.put(portal.id, portal);
    }

    /**
     * Adds a collectable to the array
     *
     * @param collectable Collectable to add
     */
    public void addCollectable(Collectable collectable)
    {
        int id = -this.collectables.size();

        if(id <= -2147483647) {
            id = 0;
        }

        this.collectables.put(id--, collectable);
    }

    /**
     * Adds a station to the array
     *
     * @param station Station to add
     */
    public void addStation(Station station)
    {
        this.stations.add(station);
    }
}

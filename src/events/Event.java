package events;

import logic.Planet;
import logic.Player;
import logic.SpaceShip;

import java.util.HashMap;
import java.util.Map;

public class Event {
    public static enum GameEventType {
        UNKNOWN,
        LIST,
        INFO,
        JOIN_GAME,
        CREATE_GAME,
        END_GAME,
        /*
         * ship_id
         * x
         * y
         */
        SHIP_MOVE,
        /*
         * owner_id
         * new_ship_id
         * x
         * y
         */
        SHIP_CREATE,
        /*
         * planet_id
         * new_owner_id
         */
        PLANET_CAPTURE,
        /*
         * attacker_id
         * attacked_id
         * damage
         */
        ATTACK,
        /*
         * ship_id
         */
        SHIP_REMOVE,
        /*
         * planet_id
         */
        GOLD_BUILD,
        /*
         * planet_id
         */
        IRON_BUILD,
        /*
         * planet_id
         */
        ANGAR_BUILD
    }

    private GameEventType type;
    private Map<String, String> props;

    public Event(GameEventType t) {
        type = t;
        props = new HashMap<String, String>();
    }

    public void setProperty(String key, String value) {
        props.put(key, value);
    }

    public String getProperty(String key) {
        if (props.containsKey(key)) {
            return props.get(key);
        }
        return null;
    }

    public GameEventType getType() {
        return type;
    }

    public static Event getMoveEvent(SpaceShip ship, double x, double y) {
        Event event = new Event(GameEventType.SHIP_MOVE);
        event.setProperty("ship_id", Integer.valueOf(ship.getId()).toString());
        event.setProperty("x", Double.valueOf(x).toString());
        event.setProperty("y", Double.valueOf(y).toString());
        return event;
    }

    public static Event getShipCreateEvent(SpaceShip ship, double x, double y) {
        Event event = new Event(GameEventType.SHIP_CREATE);
        event.setProperty("owner_id", Integer.valueOf(ship.getMaster().getId()).toString());
        event.setProperty("new_ship_id", Integer.valueOf(ship.getId()).toString());
        event.setProperty("x", Double.valueOf(x).toString());
        event.setProperty("y", Double.valueOf(y).toString());
        return event;
    }

    public static Event getShipRemoveEvent(SpaceShip ship) {
        Event event = new Event(GameEventType.SHIP_REMOVE);
        event.setProperty("ship_id", Integer.valueOf(ship.getId()).toString());
        return event;
    }

    public static Event getPlanetCaptureEvent(Planet planet, Player player) {
        Event event = new Event(GameEventType.PLANET_CAPTURE);
        event.setProperty("planet_id", Integer.valueOf(planet.getId()).toString());
        event.setProperty("new_owner_id", Integer.valueOf(player.getId()).toString());
        return event;
    }

    public static Event getAttackEvent(SpaceShip attacker, SpaceShip attacked, double damage) {
        Event event = new Event(GameEventType.ATTACK);
        event.setProperty("attacker_id", Integer.valueOf(attacker.getId()).toString());
        event.setProperty("attacked_id", Integer.valueOf(attacked.getId()).toString());
        event.setProperty("damage", Double.valueOf(damage).toString());
        return event;
    }

    public static Event getGoldBuildEvent(Planet planet) {
        Event event = new Event(GameEventType.GOLD_BUILD);
        event.setProperty("planet_id", Integer.valueOf(planet.getId()).toString());
        return event;
    }

    public static Event getIronBuildEvent(Planet planet) {
        Event event = new Event(GameEventType.IRON_BUILD);
        event.setProperty("planet_id", Integer.valueOf(planet.getId()).toString());
        return event;
    }

    public static Event getAngarBuildEvent(Planet planet) {
        Event event = new Event(GameEventType.ANGAR_BUILD);
        event.setProperty("planet_id", Integer.valueOf(planet.getId()).toString());
        return event;
    }

    public static Event fromString(String s) {
        Event event;
        String[] tokens = s.split(" ");
        if (tokens[0].substring(tokens[0].indexOf(":") + 1)
                .equals("SHIP_MOVE")) {
            event = new Event(GameEventType.SHIP_MOVE);
        } else if (tokens[0].substring(tokens[0].indexOf(":") + 1)
                .equals("SHIP_CREATE")) {
            event = new Event(GameEventType.SHIP_CREATE);
        } else if (tokens[0].substring(tokens[0].indexOf(":") + 1)
                .equals("PLANET_CAPTURE")) {
            event = new Event(GameEventType.PLANET_CAPTURE);
        } else if (tokens[0].substring(tokens[0].indexOf(":") + 1)
                .equals("ATTACK")) {
            event = new Event(GameEventType.ATTACK);
        } else if (tokens[0].substring(tokens[0].indexOf(":") + 1)
                .equals("SHIP_REMOVE")) {
            event = new Event(GameEventType.SHIP_REMOVE);
        } else if (tokens[0].substring(tokens[0].indexOf(":") + 1)
                .equals("GOLD_BUILD")) {
            event = new Event(GameEventType.GOLD_BUILD);
        } else if (tokens[0].substring(tokens[0].indexOf(":") + 1)
                .equals("IRON_BUILD")) {
            event = new Event(GameEventType.IRON_BUILD);
        } else if (tokens[0].substring(tokens[0].indexOf(":") + 1)
                .equals("ANGAR_BUILD")) {
            event = new Event(GameEventType.ANGAR_BUILD);
        } else {
            event = new Event(GameEventType.UNKNOWN);
        }

        for (String token : tokens) {
            if (token.isEmpty() || token.startsWith("Type:"))
                continue;
            int index = token.indexOf(":");
            String key = token.substring(0, index);
            String value = token.substring(index + 1);
            event.setProperty(key, value);
        }
        return event;
    }

    @Override
    public String toString() {
        String result = "";
        switch (type) {
            case SHIP_MOVE:
                result += "Type:SHIP_MOVE ";
                break;
            case SHIP_CREATE:
                result += "Type:SHIP_CREATE ";
                break;
            case PLANET_CAPTURE:
                result += "Type:PLANET_CAPTURE ";
                break;
            case ATTACK:
                result += "Type:ATTACK ";
                break;
            case SHIP_REMOVE:
                result += "Type:SHIP_REMOVE ";
                break;
            case GOLD_BUILD:
                result += "Type:GOLD_BUILD ";
                break;
            case IRON_BUILD:
                result += "Type:IRON_BUILD ";
                break;
            case ANGAR_BUILD:
                result += "Type:ANGAR_BUILD ";
                break;
            default:
                result += "Type:UNKNOWN ";
                break;
        }
        for (Map.Entry e : props.entrySet()) {
            result += " " + e.getKey() + ":" + e.getValue() + " ";
        }
        return result;
    }
}

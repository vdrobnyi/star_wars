package events;

import logic.Planet;
import logic.Player;
import logic.SpaceShip;

import java.util.HashMap;
import java.util.Map;

public class Event {
    public static enum GameEventType {
        MESSAGE,
        LIST,
        /*
         * game_name
         */
        INFO,
        /*
         * game_name
         */
        JOIN_GAME,
        /*
         * game_name
         */
        CREATE_GAME,
        /*
         * game_name
         */
        END_GAME,
        START_GAME,
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

    private EventListenerInterface from = null;

    public Event(GameEventType t) {
        type = t;
        props = new HashMap<>();
    }

    public Map<String, String> getProps() {
        return props;
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

    public EventListenerInterface getFrom() {
        return from;
    }

    public void setFrom(EventListenerInterface from) {
        this.from = from;
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
        Event event = new Event(GameEventType.LIST);
        String[] tokens = s.split(" ");
        switch (tokens[0].substring(tokens[0].indexOf(":") + 1)) {
            case "SHIP_MOVE":
                event.type = GameEventType.SHIP_MOVE;
                break;
            case "SHIP_CREATE":
                event.type = GameEventType.SHIP_CREATE;
                break;
            case "PLANET_CAPTURE":
                event.type = GameEventType.PLANET_CAPTURE;
                break;
            case "ATTACK":
                event.type = GameEventType.ATTACK;
                break;
            case "SHIP_REMOVE":
                event.type = GameEventType.SHIP_REMOVE;
                break;
            case "GOLD_BUILD":
                event.type = GameEventType.GOLD_BUILD;
                break;
            case "IRON_BUILD":
                event.type = GameEventType.IRON_BUILD;
                break;
            case "ANGAR_BUILD":
                event.type = GameEventType.ANGAR_BUILD;
                break;
            case "LIST":
                event.type = GameEventType.LIST;
                break;
            case "INFO":
                event.type = GameEventType.INFO;
                break;
            case "JOIN_GAME":
                event.type = GameEventType.JOIN_GAME;
                break;
            case "START_GAME":
                event.type = GameEventType.START_GAME;
                break;
            case "CREATE_GAME":
                event.type = GameEventType.CREATE_GAME;
                break;
            case "END_GAME":
                event.type = GameEventType.END_GAME;
                break;
            case "MESSAGE":
                event.type = GameEventType.MESSAGE;
                break;
            default:
                System.err.println("Unknown event: " + s);
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

    public Double getDouble(String prop) {
        Double res = Double.valueOf(props.get(prop));
        return res;
    }

    public Integer getInteger(String prop) {
        Integer res = Integer.valueOf(props.get(prop));
        return res;
    }

    @Override
    public String toString() {
        String result = "";
        switch (type) {
            case MESSAGE:
                result += "Type:MESSAGE ";
                break;
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
            case LIST:
                result += "Type:LIST ";
                break;
            case INFO:
                result += "Type:INFO ";
                break;
            case CREATE_GAME:
                result += "Type:CREATE_GAME ";
                break;
            case END_GAME:
                result += "Type:END_GAME ";
                break;
            case START_GAME:
                result += "Type:START_GAME ";
                break;
            case JOIN_GAME:
                result += "Type:JOIN_GAME ";
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

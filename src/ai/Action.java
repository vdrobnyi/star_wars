package ai;

import logic.SpaceShip;
import logic.UniverseObject;

public class Action {
    enum actions {
        MOVE_TO_EMPTY_PLANET,
        MOVE_TO_ENEMY_PLANET,
        MOVE_TO_ENEMY_SHIP,
        STAY;

    };

    actions action;
    UniverseObject target;
    SpaceShip ship;

    public Action(actions a) {
        action = a;
        target = null;
        ship = null;
    }

    public Action(actions a, UniverseObject t) {
        action = a;
        target = t;
        ship = null;
    }


    public Action(actions a, UniverseObject t, SpaceShip s) {
        action = a;
        target = t;
        ship = s;
    }
}

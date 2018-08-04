package com.liftmania;

public enum LiftControllerStates {
    CLOSED_IDLE, // Closed with no requests (or summons)
    CLOSED_wREQ, // Closed with request(s) (or summon(s))
    OPEN_IDLE, // Open with no requests (or summons)
    OPEN_wREQ // Open with request(s) (or summon(s))
}

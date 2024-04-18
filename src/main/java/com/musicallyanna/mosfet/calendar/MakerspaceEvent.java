package com.musicallyanna.mosfet.calendar;

/**
 * Class representing an event to take place in the makerspace.
 * @author Anna Bontempo
 */
public abstract class MakerspaceEvent {

    /**
     * The {@code MakerspaceRoom} the the event is being held in.
     */
    MakerspaceRoom room;

    /**
     * Formats {@code this.room} into a printable {@code String} format.
     * @return a {@code String} in capitalized, spaced out format representing the value of {@code this.room}
     */
    public String getRoomString() {
        return MakerspaceRoom.toFormattedString(this.room);
    }
}

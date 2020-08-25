/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.room;

/**
 *
 * @author Ngoc Tuan
 */
public class Room {

    private int idRoom;
    private String roomName;

    public Room() {
    }

    public Room(int idRoom, String roomName) {
        this.idRoom = idRoom;
        this.roomName = roomName;
    }

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public String getRoomName() {
        return roomName;
    }

    @Override
    public String toString() {
        return this.roomName;
    }

}

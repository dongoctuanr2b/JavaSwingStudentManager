/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.room;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sm.connection.BConnection;
import sm.interfaceSM.StudentManager;

/**
 *
 * @author Ngoc Tuan
 */
public class RoomDAO implements StudentManager<Room> {

    private Connection conn;

    public RoomDAO() {
        conn = BConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection succeed!");
        } else {
            System.out.println("Connection failed!!!");
        }
    }

    @Override
    public List<Room> getAll() {
        List<Room> rooms = new ArrayList<>();

        try {
            String sql = "{CALL readAllRoom}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idRoom = rs.getInt("idRoom");
                String roomName = rs.getString("roomName");

                Room room = new Room(idRoom, roomName);
                rooms.add(room);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rooms;
    }

    @Override
    public boolean save(Room room) {
        try {
            String sql = "{CALL insertRoom(?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, room.getRoomName());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Room room) {
        try {
            String sql = "{CALL updateRoom(?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, room.getIdRoom());
            cs.setString(2, room.getRoomName());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(String s) {
        try {
            String sql = "DELETE FROM tblRoom WHERE idRoom = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Room> search(String s) {
        List<Room> rooms = new ArrayList<>();

        try {
            String sql = "{CALL searchRoom(?)}";

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, s);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idRoom = rs.getInt("idRoom");
                String roomName = rs.getString("roomName");

                Room room = new Room(idRoom, roomName);
                rooms.add(room);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rooms;
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.user;

/**
 *
 * @author admin
 */
public class User {

    private int idUser;
    private String username;
    private String password;
    private String permissionName;
    private int idPermission;

    public User() {
    }

    public User(int idUser, String username, String password, String permissionName) {
        this.idUser = idUser;
        this.permissionName = permissionName;
        this.username = username;
        this.password = password;
    }

    public User(int idPermission, int idUser) {
        this.idPermission = idPermission;
        this.idUser = idUser;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int idUser, String username, String password) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public int getIdPermission() {
        return idPermission;
    }

    @Override
    public String toString() {
        return this.username;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.user;

/**
 *
 * @author Ngoc Tuan
 */
public class Permission {

    private int idPermission;
    private int idPermissionDetail;
    private String permissionName;
    private String actionName;
    private byte checkAction;
    private String checkActionString;

    public Permission() {
    }

    public Permission(int idPermission, String permissionName, String actionName, byte checkAction) {
        this.idPermission = idPermission;
        this.permissionName = permissionName;
        this.actionName = actionName;
        this.checkAction = checkAction;
    }

    public Permission(int idPermissionDetail, int idPermission, String permissionName, String actionName, String checkActionString) {
        this.idPermissionDetail = idPermissionDetail;
        this.idPermission = idPermission;
        this.permissionName = permissionName;
        this.actionName = actionName;
        this.checkActionString = checkActionString;
    }

    public Permission(String permissionName) {
        this.permissionName = permissionName;
    }

    public Permission(int idPermissionDetail, int idPermission, String actionName, byte checkAction) {
        this.idPermissionDetail = idPermissionDetail;
        this.idPermission = idPermission;
        this.actionName = actionName;
        this.checkAction = checkAction;
    }

    public Permission(int idPermission, String actionName, byte checkAction) {
        this.idPermission = idPermission;
        this.actionName = actionName;
        this.checkAction = checkAction;
    }

    public Permission(int idPermission, String permissionName) {
        this.idPermission = idPermission;
        this.permissionName = permissionName;
    }

    public int getIdPermissionDetail() {
        return idPermissionDetail;
    }

    public int getIdPermission() {
        return idPermission;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getActionName() {
        return actionName;
    }

    public byte getCheckAction() {
        return checkAction;
    }

    public String getCheckActionString() {
        return checkActionString;
    }

    @Override
    public String toString() {
        return this.permissionName;
    }

}

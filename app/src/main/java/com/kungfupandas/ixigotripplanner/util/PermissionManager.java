package com.kungfupandas.ixigotripplanner.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v13.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aish on 8/4/17.
 */

public class PermissionManager {
    public static final int ACCOUNTS_REQUEST = 0x01;
    public static final int FINE_LOCATION_REQUEST = 0x02;
    public static final int WRITE_EXTERNAL_STORAGE = 0x04;

    static HashMap<String, PermissionRequest> permissions;
    static PermissionManager manager = new PermissionManager();
    ArrayList<String> permissionsToRequest = new ArrayList<>();

    private PermissionManager() {
    }

    static {
        permissions = new HashMap<>();
        permissions.put(android.Manifest.permission.GET_ACCOUNTS, new PermissionRequest(/*R.string.default_error_message, */ACCOUNTS_REQUEST));
        permissions.put(android.Manifest.permission.ACCESS_FINE_LOCATION, new PermissionRequest(/*R.string.default_error_message, */FINE_LOCATION_REQUEST));
        permissions.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionRequest(/*R.string.default_error_message, */WRITE_EXTERNAL_STORAGE));
    }

    public static PermissionManager begin() {
        return manager.start();
    }

    private PermissionManager start() {
        permissionsToRequest.clear();
        return this;
    }

    public boolean request(Activity activity) {
        if (needPersionCheck()) {
            for (int i = 0; i < permissionsToRequest.size(); i++) {
                if (!isPermissionRequestRequired(activity, permissionsToRequest.get(i))) {
                    permissionsToRequest.remove(i);
                    i--;
                }
            }
            if (permissionsToRequest.size() > 0) {
                requestPermission(activity);
            }
        }
        return false;
    }

    private void requestPermission(final Activity activity) {
        ArrayList<String> nonSnackBarRequests = new ArrayList<>();
        for (int i = 0; i < permissionsToRequest.size(); i++) {
            boolean needToShowSnackBar = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionsToRequest.get(i));
            if (!needToShowSnackBar) {
                nonSnackBarRequests.add(permissionsToRequest.get(i));
                permissionsToRequest.remove(i);
                i--;
            }
        }

        if (permissionsToRequest.size() > 0) {
            int code = 0;
            for (String permission : permissionsToRequest) {
                code |= permissions.get(permission).requestCode;
            }
            ActivityCompat.requestPermissions(activity,
                    permissionsToRequest.toArray(new String[0]), code);
        }

        if (nonSnackBarRequests.size() > 0) {
            int code = 0;
            for (String permission : nonSnackBarRequests) {
                code |= permissions.get(permission).requestCode;
            }
            ActivityCompat.requestPermissions(activity,
                    nonSnackBarRequests.toArray(new String[0]), code);
        }

    }

    public PermissionManager addRequest(String permission) {
        if (permissions.containsKey(permission)) {
            permissionsToRequest.add(permission);
        }
        return this;
    }

    public PermissionManager addRequest(int code) {
        switch (code) {
            case ACCOUNTS_REQUEST:
                permissionsToRequest.add(Manifest.permission.GET_ACCOUNTS);
                break;
            case FINE_LOCATION_REQUEST:
                permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
                break;
            case WRITE_EXTERNAL_STORAGE:
                permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        return this;
    }

    public boolean needPersionCheck() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isPermissionRequestRequired(Context context, String permission) {
        return permissions.containsKey(permission) && ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
    }

    static class PermissionRequest {
        /*int messageResourseId;*/
        int requestCode;

        public PermissionRequest(/*int messageResourseId, */int requestCode) {
            /*this.messageResourseId = messageResourseId;*/
            this.requestCode = requestCode;
        }
    }
}
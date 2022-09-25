package com.app.maptranslation.composable

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
fun checkPermissions(
    permissionsState: MultiplePermissionsState,
    afterPermissionGranted: () -> Unit
) {
    if (permissionsState.allPermissionsGranted) { // 퍼미션 허용
        afterPermissionGranted.invoke()
    } else {
        val allPermissionsRevoked =
            permissionsState.permissions.size ==
                    permissionsState.revokedPermissions.size

        if (!allPermissionsRevoked) {
            // If not all the permissions are revoked, it's because the user accepted the COARSE
            // location permission, but not the FINE one.
        } else if (permissionsState.shouldShowRationale) {
            // Both location permissions have been denied
        } else {
            // First time the user sees this feature or the user doesn't want to be asked again
        }

        permissionsState.launchMultiplePermissionRequest()
    }
}
package com.sychen.basic.util

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sychen.basic.MyApplication.Companion._context

object PermissionUtil {

    /**
     * 获取权限
     */
    fun runtimePermission(permission: String) {
        Dexter.withContext(_context)
            .withPermission(permission)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    Toast.makeText(_context, "申请$permission" + "权限成功", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).check()
    }
}
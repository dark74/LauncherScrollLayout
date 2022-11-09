package com.dk.asandroidlauncher.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.dk.asandroidlauncher.IFlymeLauncherConfigManager

/**
 * @Description :
 * @Author : ke.dong
 * @Date : 2022/11/3 20:41
 * @Email : ke.dong@xjsd.com
 */
class ARLauncherDataService : Service() {
    
    private val stub: IFlymeLauncherConfigManager.Stub = object : IFlymeLauncherConfigManager.Stub() {
        @Throws(RemoteException::class)
        override fun getWorkSpaceConfig(): String {
            Log.e("ARLauncherDataService", "获取launcher数据")
            return json
        }
    }
    
    override fun onBind(intent: Intent): IBinder? {
        return stub
    }
    
    companion object {
        val json =
            """
        {
          "numRows": 6,
          "numColumns": 4,
          "cellLayoutCounts": 5,
          "workspaceCellLayoutWidth": 1408,
          "workspaceCellLayoutHeight": 2111
        }
        """.trimIndent()
    }
}
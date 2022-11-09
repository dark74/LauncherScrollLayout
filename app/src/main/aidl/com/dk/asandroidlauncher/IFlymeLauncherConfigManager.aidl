// IFlymeLauncherConfigManager.aidl
package com.dk.asandroidlauncher;
/**
* Flyme Launcher为AR桌面提供数据
*/
interface IFlymeLauncherConfigManager {
    /**
    * 获取Flyme桌面当前CellLayout中配置数据
    * @return 返回json数据字符串
    */
    String getWorkSpaceConfig();
}
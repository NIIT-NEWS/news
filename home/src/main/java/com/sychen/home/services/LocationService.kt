package com.sychen.home.services

import android.content.ContentValues
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.location.AMapLocationQualityReport
import com.sychen.basic.Utils.Show

class LocationService : LifecycleService() {
    private lateinit var locationClient: AMapLocationClient
    private lateinit var locationOption: AMapLocationClientOption
    val locationData = MutableLiveData("")

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(ContentValues.TAG, "onStartCommand: MyService")
        return super.onStartCommand(intent, flags, startId)
    }

    inner class MyBinder : Binder() {
        val service = this@LocationService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        initLocation()
        startLocation()
        return MyBinder()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(ContentValues.TAG, "onDestroy: MyService")
    }

    /**
     * 初始化定位
     * @since 2.8.0
     * @author csy
     */
    private fun initLocation() {
        //初始化client
        locationClient = AMapLocationClient(applicationContext)
        locationOption = getDefaultOption()
        //设置定位参数
        locationClient.setLocationOption(locationOption)
        // 设置定位监听
        locationClient.setLocationListener(locationListener)
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author 陈思远
     */
    private fun getDefaultOption(): AMapLocationClientOption {
        val mOption = AMapLocationClientOption()
        mOption.locationMode =
            AMapLocationClientOption.AMapLocationMode.Hight_Accuracy //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.isGpsFirst = false //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.httpTimeOut = 30000 //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.interval = 10000 //可选，设置定位间隔。默认为2秒
        mOption.isNeedAddress = true //可选，设置是否返回逆地理地址信息。默认是true
        mOption.isOnceLocation = false //可选，设置是否单次定位。默认是false
        mOption.isOnceLocationLatest =
            false //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP) //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.isSensorEnable = true //可选，设置是否使用传感器。默认是false
        mOption.isWifiScan =
            true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.isLocationCacheEnable = true //可选，设置是否使用缓存定位，默认为true
        mOption.geoLanguage =
            AMapLocationClientOption.GeoLanguage.DEFAULT //可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption
    }

    /**
     * 定位监听
     */
    private var locationListener =
        AMapLocationListener { location ->
            var result = "null"
            if (null != location) {
                val sb = StringBuffer()
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.errorCode == 0) {
                    sb.append("\n" + "经度" + location.longitude + "\n")
                    sb.append("纬度" + location.latitude + "\n")
                    locationData.postValue("${location.longitude}"+","+"${location.latitude}")
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.errorCode + "\n")
                    sb.append("错误信息:" + location.errorInfo + "\n")
                    sb.append("错误描述:" + location.locationDetail + "\n")
                }
                sb.append("* WIFI开关：")
                    .append(if (location.locationQualityReport.isWifiAble) "开启" else "关闭")
                    .append("\n")
                sb.append("* GPS星数：").append(location.locationQualityReport.gpsSatellites)
                    .append("\n")
                sb.append("* GPS状态：")
                    .append(getGPSStatusString(location.locationQualityReport.gpsStatus))
                    .append("\n")
                sb.append("* 网络类型：" + location.locationQualityReport.networkType).append("\n")
                sb.append("* 网络耗时：" + location.locationQualityReport.netUseTime).append("\n")
                //解析定位结果，
                result = sb.toString()
            }
        }

    /**
     * 获取GPS状态的字符串
     * @param statusCode GPS状态码
     * @return
     */
    private fun getGPSStatusString(statusCode: Int): String {
        var str = ""
        when (statusCode) {
            AMapLocationQualityReport.GPS_STATUS_OK -> str = "GPS状态正常"
            AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER -> str =
                "手机中没有GPS Provider，无法进行GPS定位"
            AMapLocationQualityReport.GPS_STATUS_OFF -> str = "GPS关闭，建议开启GPS，提高定位质量"
            AMapLocationQualityReport.GPS_STATUS_MODE_SAVING -> str =
                "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量"
            AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION -> str = "没有GPS定位权限，建议开启gps定位权限"
        }
        return str
    }

    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author 陈思远
     */
    private fun startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption)
        // 启动定位
        locationClient.startLocation()
    }
}
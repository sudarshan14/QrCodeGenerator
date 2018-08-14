package lti.com.qrcodegenerator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var qrCodeWrite: QRCodeWriter = QRCodeWriter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        barCode.setImageBitmap(getBitmap())
    }


    fun getBitmap(): Bitmap {

        val myList: ArrayList<String> = getMacAddress()

        val SSID: String =myList.get(0)//"MOB_TCL"
        val KEY: String = "1234567890"
        val BSSID = myList.get(1)//"94:65:2d:ca:fe:6e"//"a0:63:91:48:18:4d"//myList.get(1)// getMacAddress().get(0)//"A0639148184D"
        var TYPE:String = "1"//"WPA2-PSK"
        if (myList.size == 3) {
         //    TYPE = "WPA2-PSK"//WPA2-PSK-CCMP//myList.get(2)//"WEP"
        }


        val bitMatrix: BitMatrix = qrCodeWrite.encode("$SSID\r\n$KEY\r\n$BSSID\r\n$TYPE", BarcodeFormat.QR_CODE, 512, 512)
        val bitMap: Bitmap = Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.RGB_565)
        for (x in 0 until bitMatrix.width) {
            for (y in 0 until bitMatrix.height) {
                bitMap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bitMap


    }

    fun getMacAddress(): ArrayList<String> {

//        val myList = arrayListOf<Kolory>()
        val myList: ArrayList<String> = ArrayList()


        val wifiManager: WifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val networkList: List<ScanResult> = wifiManager.scanResults
        if (wifiManager == null) {
            return myList
        } else {


            val wifiInfo: WifiInfo = wifiManager.connectionInfo

            var ssid: String = wifiInfo.ssid

            ssid = ssid.substring(1,ssid.length-1)
            myList.add(ssid)

            val BSSID = wifiInfo.bssid
            myList.add(BSSID)


            for (network in networkList) {
                   val ss:String = ssid;
                if (ssid.equals(network.SSID)) {
                    val capabilities = network.capabilities
                    myList.add(capabilities)
                }
            }


        }




        return myList
    }
}


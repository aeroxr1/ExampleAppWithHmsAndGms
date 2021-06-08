package aeroxr1.core.qrCodePoc

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import aeroxr1.core.databinding.ActivityQrCodeBinding
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions

class QrCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrCodeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.scanButton.setOnClickListener {
            loadScanKitBtnClick()
        }
    }


    private fun loadScanKitBtnClick() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
            CAMERA_REQ_CODE
        )
    }

    private fun showResult(hmsScan: HmsScan?) {
        binding.scanResult.text = hmsScan?.getOriginalValue()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissions == null || grantResults == null) {
            return
        }
        if (grantResults.size < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return
        }

        //Default View Mode
        if (requestCode == CAMERA_REQ_CODE) {
            ScanUtil.startScan(this, REQUEST_CODE_SCAN_ONE, HmsScanAnalyzerOptions.Creator().create())
        }
    }

    /**
     * Event for receiving the activity result.
     *
     * @param requestCode Request code.
     * @param resultCode Result code.
     * @param data        Result.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            return
        }
        //Default View
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            val obj: HmsScan ?= data.getParcelableExtra(ScanUtil.RESULT)
            showResult(obj)
        }
    }

    companion object {
        const val CAMERA_REQ_CODE = 111
        const val REQUEST_CODE_SCAN_ONE = 0X01
        const val RESULT = "SCAN_RESULT"
    }

}
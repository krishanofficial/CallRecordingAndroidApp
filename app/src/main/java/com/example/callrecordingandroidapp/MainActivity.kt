package com.example.callrecordingandroidapp


import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
   lateinit var edittext: EditText
    private val READ_PERMISSIONS_CODE=1
    private lateinit var button: Button
    lateinit var information:String
    lateinit var phoneTypeString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        edittext = findViewById(R.id.editText)
//
//        // Attach set on click listener to the button for initiating intent
//        button.setOnClickListener(View.OnClickListener {
//            // getting phone number from edit text and changing it to String
//            val phone_number = edittext.text.toString()
//
//            // Getting instance of Intent with action as ACTION_CALL
//            val phone_intent = Intent(Intent.ACTION_CALL)
//
//            // Set data of Intent through Uri by parsing phone number
//            phone_intent.data = Uri.parse("tel:$phone_number")
//
//            // start Intent
//            startActivity(phone_intent)
//        })

        button.setOnClickListener {
            checkPermission()
        }
    }
    private fun checkPermission() {
        val checkPermission=ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_STATE)

        if (checkPermission==PackageManager.PERMISSION_GRANTED){
            telephoneManagerDetails()
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_PHONE_STATE),READ_PERMISSIONS_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            READ_PERMISSIONS_CODE->{
                if (grantResults.size >=0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    telephoneManagerDetails()
                }else
                {
                    Toast.makeText(applicationContext, "You don't have permission", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    @SuppressLint("ServiceCast", "MissingPermission")
    private fun telephoneManagerDetails(){
        val telephonyManager=getSystemService(Context.TELECOM_SERVICE) as TelephonyManager
        val typeOfPhone=telephonyManager.phoneType

        when(typeOfPhone){
            TelephonyManager.PHONE_TYPE_GSM->{
                phoneTypeString="GSM"
            }
            TelephonyManager.PHONE_TYPE_CDMA->{
                phoneTypeString="CDMA"
            }
            TelephonyManager.PHONE_TYPE_NONE->{
                phoneTypeString="NONE"
            }
        }
        val roaming=telephonyManager.isNetworkRoaming
        val phoneType=phoneTypeString
        val voiceMailNo=telephonyManager.voiceMailNumber
        val iMEINumber=telephonyManager.deviceId
        val simCountryISO=telephonyManager.simCountryIso
        val networkCountryISO=telephonyManager.networkCountryIso
        val deviceSoftwareVersion=telephonyManager.deviceSoftwareVersion
        val subscriberId=telephonyManager.subscriberId
        val simSerialNumber=telephonyManager.simSerialNumber

        information="Phone Details:\n\n" +
                "Phone Network Type : $phoneType\n"+
                "IMEI number:$iMEINumber\n"+"SIM Counter ISO:$simCountryISO\n"+
                "Network Country ISO :$networkCountryISO\n"+
                "Voice Mail Number: $voiceMailNo\n"+
                "Roaming:$roaming\n"+
                "Subscriber Id: $subscriberId\n"+
                "SIM Serial Number: $simSerialNumber\n"+
                "Device Software Version: $deviceSoftwareVersion\n"

    }

}
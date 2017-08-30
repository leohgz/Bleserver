package com.tech.teravision.bleserver;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ActividadServer extends Activity {
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt bluetoothGattClient;
    private BluetoothLeScanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_server);

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        Log.d("","");

        if (mBluetoothAdapter.isEnabled() && getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
            //scanner = mBluetoothAdapter.getBluetoothLeScanner();
            //scanner.startScan(scanCallback);
            //mBluetoothAdapter.startLeScan(callback);
            LeCallBack callBacka = new LeCallBack(mBluetoothAdapter,getApplicationContext());
            mBluetoothAdapter.startLeScan(callBacka);

           /* mBluetoothAdapter.getBluetoothLeScanner().startScan(new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result){
                    super.onScanResult(callbackType, result);
                    result.getDevice().connectGatt(getApplicationContext(), false,callbackNew);
                    Log.d("","");
                }

                @Override
                public void onBatchScanResults(List<ScanResult> results) {
                    super.onBatchScanResults(results);
                    Log.d("","");
                }

                @Override
                public void onScanFailed(int errorCode) {
                    super.onScanFailed(errorCode);
                    Log.d("","");
                }
            });*/
        }else{
            ((TextView)findViewById(R.id.textview)).setText("NO BLE SUPPORT");
            Toast.makeText(this, "NO BLE SUPPORT!", Toast.LENGTH_SHORT).show();
        }
    }


    BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            bluetoothGattClient= device.connectGatt(getApplicationContext(), true, new BluetoothGattCallback() {
                @Override
                public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                    super.onConnectionStateChange(gatt, status, newState);
                    if (newState ==BluetoothGatt.STATE_CONNECTED){
                        gatt.discoverServices();
                        Log.d("","");
                    }
                }
                @Override
                public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                    super.onServicesDiscovered(gatt, status);
                    if (status == BluetoothGatt.GATT_SUCCESS){
                        Log.d("","");

                    }
                    if (status==BluetoothGatt.STATE_CONNECTED){
                        Log.d("","");
                        for (BluetoothGattService bluetoothGattService:gatt.getServices()){
                            for (BluetoothGattCharacteristic bluetoothGattCharacteristic:bluetoothGattService.getCharacteristics()) {
                                for (BluetoothGattDescriptor bluetoothGattDescriptor: bluetoothGattCharacteristic.getDescriptors()) {
                                    Log.e("num ",""+bluetoothGattDescriptor.getPermissions());
                                }
                                if (bluetoothGattCharacteristic.getProperties()==BluetoothGattCharacteristic.PROPERTY_WRITE){
                                    //  Log.e("PROPERTY_WRITE::: ","BluetoothGattCharacteristic.PROPERTY_WRITE");
                                }
                            }
                        }
                    }
                    gatt.disconnect();
                }
                @Override
                public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                    super.onCharacteristicWrite(gatt, characteristic, status);
                }

                @Override
                public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                    super.onDescriptorWrite(gatt, descriptor, status);
                }

                @Override
                public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
                    super.onReliableWriteCompleted(gatt, status);
                }
            });
        }
    };


    BluetoothGattCallback callbackNew = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);

            if (newState ==BluetoothGatt.STATE_CONNECTED){
                Log.d("","");
                gatt.discoverServices();
            }
            if (newState ==BluetoothGatt.STATE_DISCONNECTED){
                //boolean canceled = mBluetoothAdapter.cancelDiscovery();
                Log.d("cancelDiscovery ","");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            List<BluetoothGattService> listServices = gatt.getServices();
            for (BluetoothGattService bluetoothGattService: listServices) {
                if (bluetoothGattService.describeContents()==BluetoothGattService.SERVICE_TYPE_PRIMARY){
                    Log.e("BluetoothGattService:: ","SERVICE_TYPE_PRIMARY");
                }
                    List<BluetoothGattCharacteristic> list =bluetoothGattService.getCharacteristics();
                    for (BluetoothGattCharacteristic item:list) {
                        Log.e("BluetoothGattCharac ",""+item.describeContents());
                    }
            }
            Log.d("onServicesDiscovered ","onServicesDiscovered");
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.d("onCharacteristicRead ","onCharacteristicRead");
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.d("onCharacteristicWrite ","onCharacteristicWrite");
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.d("onDescriptorRead ","onDescriptorRead");
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.d("onDescriptorWrite ","onDescriptorWrite");
        }
    };


    ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            Log.d("","");
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            Log.d("","");
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("","");
        }
    };
}
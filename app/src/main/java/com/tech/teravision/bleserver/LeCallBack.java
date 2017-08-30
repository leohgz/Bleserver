package com.tech.teravision.bleserver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import android.util.Log;

/**
 * Created by Leonardo on 30/8/2017.
 */

public class LeCallBack  implements BluetoothAdapter.LeScanCallback {
    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;

    public LeCallBack(BluetoothAdapter bluetoothAdapter, Context ctx){
        mBluetoothAdapter = bluetoothAdapter;
        mContext = ctx;
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if (device.getType() == BluetoothDevice.DEVICE_TYPE_LE){
            /*este dispositivo es un BLE!!!*/
            BluetoothGatt bluetoothGatt = device.connectGatt(mContext,true,bluetoothGattCallback);
            Log.d("DEVICE_TYPE_LE","DEVICE_TYPE_LE");

            bluetoothGatt.connect();

        }
    }

    BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);

            if (status==gatt.GATT_SUCCESS){
                Log.d("","");

            }
            if (newState==gatt.STATE_CONNECTED){
                Log.d("","");

            }
            if (newState==gatt.STATE_DISCONNECTED){
                Log.d("","");

            }
            Log.d("","");
        }
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.d("","");
        }
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.d("","");
        }
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.d("","");
        }
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.d("","");
        }
        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.d("","");
        }
        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.d("","");
        }
        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
            Log.d("","");
        }
        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            Log.d("","");
        }
        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            Log.d("","");
        }
    };
}

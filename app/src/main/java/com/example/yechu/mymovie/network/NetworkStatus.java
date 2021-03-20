package com.example.yechu.mymovie.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkStatus extends ConnectivityManager.NetworkCallback {

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE  = 2;
    public static final int TYPE_NOT_CONNECTED = 3;

    public static int getNetworkStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder wifiBuilder = new NetworkRequest.Builder();

        //콜백 호출 조건
        wifiBuilder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        wifiBuilder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);

        ConnectivityManager.NetworkCallback wifiCallBack = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
            }
        };
        connectivityManager.registerNetworkCallback(wifiBuilder.build(), wifiCallBack);

        return TYPE_NOT_CONNECTED;
    }

}

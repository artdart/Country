package com.dataart.country.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.dataart.country.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by aakimov on 25/06/15.
 */
public abstract class BaseCommand {
    private final static String NAMESPACE = BaseCommand.class.getName();
    protected final static String KEY_EXCEPTION = NAMESPACE
            .concat(".KEY_EXCEPTION");

    public static final int ERROR_RESULT = -1;
    public static final int OK_RESULT = 1;

    protected enum RequestType {
        GET, POST, PUT, DELETE
    }

    protected void execute(Context context, ResultReceiver resultReceiver) {
        HttpURLConnection httpURLConnection = null;
        Bundle bundle = new Bundle();
        if (hasNetworkConnection(context)) {
            try {
                URL url = new URL(getTargetUrl(context));
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod(getRequestType().toString());
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                if (isSuccessStatusCode(httpURLConnection.getResponseCode())) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    int resultCode = handleResponse(sb.toString(), bundle, context);
                    send(resultReceiver, resultCode, bundle);
                } else {
                    bundle.putString(KEY_EXCEPTION, String.valueOf(httpURLConnection.getResponseCode()));
                    send(resultReceiver, ERROR_RESULT, bundle);
                }
            } catch (IOException e) {
                bundle.putString(KEY_EXCEPTION, e.getLocalizedMessage());
                send(resultReceiver, ERROR_RESULT, bundle);
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

        } else {
            bundle.putString(KEY_EXCEPTION, context.getString(R.string.no_connection));
            send(resultReceiver, ERROR_RESULT, bundle);
        }
    }

    protected boolean isSuccessStatusCode(int statusCode) {
        return statusCode == 200 || statusCode == 201;
    }

    protected void send(ResultReceiver resultReceiver, final int resultCode, Bundle resultData) {
        if (resultReceiver != null) {
            resultReceiver.send(resultCode, resultData);
        }
    }

    private boolean hasNetworkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null;
    }

    public static String getErrorString(Bundle bundle) {
        return bundle.getString(KEY_EXCEPTION);
    }

    protected abstract String getTargetUrl(Context context);

    protected abstract RequestType getRequestType();

    protected abstract int handleResponse(String content, Bundle bundle, Context context);
}

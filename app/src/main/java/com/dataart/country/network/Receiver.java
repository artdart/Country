package com.dataart.country.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by aakimov on 25/06/15.
 */
public abstract class Receiver extends ResultReceiver {

    public Receiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case BaseCommand.OK_RESULT:
                handleResult(resultData);
                break;
            case BaseCommand.ERROR_RESULT:
                handleError(BaseCommand.getErrorString(resultData));
            default:
        }
    }

    protected abstract void handleResult(Bundle bundle);

    protected abstract void handleError(String error);
}

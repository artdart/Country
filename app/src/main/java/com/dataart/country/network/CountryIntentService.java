package com.dataart.country.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;


public class CountryIntentService extends IntentService {
    private static final String COUNTRY_RECEIVER = "COUNTRY_RECEIVER";

    public CountryIntentService() {
        super("CountryIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            new RandomCountryCommand().execute(this, (ResultReceiver) intent.getParcelableExtra(COUNTRY_RECEIVER));
        }
    }

    public static void start(Context context, Receiver countryReceiver) {
        Intent intent = new Intent(context, CountryIntentService.class);
        intent.putExtra(COUNTRY_RECEIVER, countryReceiver);
        context.startService(intent);
    }
}

package com.dataart.country.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.dataart.country.Country;
import com.dataart.country.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

/**
 * Created by aakimov on 25/06/15.
 */
public class RandomCountryCommand extends BaseCommand {
    private final static String KEY_COUNTRY = RandomCountryCommand.class.getName()
            .concat(".KEY_COUNTRY");

    @Override
    protected String getTargetUrl(Context context) {
        return context.getString(R.string.country_url);
    }

    @Override
    protected RequestType getRequestType() {
        return RequestType.GET;
    }

    @Override
    protected int handleResponse(String content, Bundle bundle, Context context) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray("worldpopulation");
            Random random = new Random();
            int i = random.nextInt(jsonArray.length());
            JSONObject countryJson = (JSONObject) jsonArray.get(i);
            Bitmap bitmap = BitmapFactory.decodeStream(new URL(countryJson.getString("flag")).openStream());
            Country country = new Country(countryJson.getString("country"),
                    countryJson.getString("population"),
                    bitmap);
            bundle.putParcelable(KEY_COUNTRY, country);
        } catch (JSONException e) {
            bundle.putString(KEY_EXCEPTION, e.getLocalizedMessage());
            return ERROR_RESULT;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return OK_RESULT;
    }

    public static Country getCountry(Bundle bundle) {
        return bundle.getParcelable(KEY_COUNTRY);
    }
}

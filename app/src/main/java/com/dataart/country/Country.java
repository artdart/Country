package com.dataart.country;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aakimov on 25/06/15.
 */
public class Country implements Parcelable {
    public final String name;
    public final String population;
    public final Bitmap flag;

    public Country(String name, String population, Bitmap flag) {
        this.name = name;
        this.population = population;
        this.flag = flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(population);
        parcel.writeParcelable(flag, 0);
    }

    public static Creator<Country> CREATOR = new Creator<Country>() {

        @Override
        public Country createFromParcel(Parcel source) {
            return new Country(
                    source.readString(),
                    source.readString(),
                    (Bitmap) source.readParcelable(Bitmap.class.getClassLoader())
            );
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

}

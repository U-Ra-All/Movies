package com.android.uraall.movies.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    SharedPreferences preferences;

    public Preferences(Activity activity) {
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public void setSearchParameters(String searchParameters) {
        preferences.edit().putString("search", searchParameters).apply();
    }

    public String getSearchParameters() {
        return preferences.getString("search", "Superman");
    }

}

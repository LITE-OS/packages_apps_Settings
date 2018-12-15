/*
 * Copyright (C) 2018 LiteOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lite.settings.fragments;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v14.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceChangeListener;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.provider.Settings;
import android.util.Log;
import android.content.ContentResolver;
import android.content.Context;
import android.content.FontInfo;
import android.content.IFontService;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.support.v7.preference.ListPreference;
import java.util.ArrayList;
import java.util.List;
import libcore.util.Objects;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.lite.LiteUtils;
import com.lite.settings.fragments.FontDialogPreference;

public class LiteDisplay extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    private static final String KEY_FONT_PICKER_FRAGMENT_PREF = "custom_font";
    private static final String SUBS_PACKAGE = "projekt.substratum";

    private FontDialogPreference mFontPreference;
    private IFontService mFontService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.display_settings_lite);

        final PreferenceScreen prefScreen = getPreferenceScreen();

        mFontPreference =  (FontDialogPreference) findPreference(KEY_FONT_PICKER_FRAGMENT_PREF);
        mFontService = IFontService.Stub.asInterface(
                ServiceManager.getService("fontservice"));
        if (!LiteUtils.isPackageInstalled(getActivity(), SUBS_PACKAGE)) {
            mFontPreference.setSummary(getCurrentFontInfo().fontName.replace("_", " "));
        } else {
            mFontPreference.setSummary(getActivity().getString(
                    R.string.disable_fonts_installed_title));
        }
    }

    private FontInfo getCurrentFontInfo() {
        try {
            return mFontService.getFontInfo();
        } catch (RemoteException e) {
            return FontInfo.getDefaultFontInfo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

//  @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        return true;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.LITE;
    }
}

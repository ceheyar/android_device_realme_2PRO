/*
 * Copyright (C) 2015 The CyanogenMod Project
 *               2017-2020 The LineageOS Project
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

package com.realmeparts.settings;

import android.content.Intent;
import android.os.Bundle;
import androidx.preference.PreferenceFragment;
import androidx.preference.Preference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;
import androidx.preference.TwoStatePreference;

import com.realmeparts.settings.doze.DozeSettingsActivity;
import com.realmeparts.settings.kcal.KCalSettingsActivity;
import com.realmeparts.settings.vibration.CallVibratorStrengthPreference;
import com.realmeparts.settings.vibration.NotificationVibratorStrengthPreference;
import com.realmeparts.settings.vibration.VibratorStrengthPreference;

public class RealmeParts extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    public static final String PREF_USB_FAST_CHARGE_SWITCH = "fastcharge";
    public static final String USB_FAST_CHARGE_PATH = "/sys/kernel/fast_charge/force_fast_charge";

    public static final String PREF_OTG_SWITCH = "otg";
    public static final String USB_OTG_SWITCH_PATH = "/sys/class/power_supply/usb/otg_switch";

    public static final String PREF_VMAX_OVERRIDE_SWITCH = "vmax_override";
    public static final String VMAX_OVERRIDE_PATH = "/sys/class/leds/vibrator/vmax_override";

    private static TwoStatePreference mUSBFastChgModeSwitch;
    private static TwoStatePreference mOTGModeSwitch;
    private static TwoStatePreference mVmaxOverrideModeSwitch;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.realmeparts, rootKey);
        Preference mDozePref = findPreference("doze");
        mDozePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(), DozeSettingsActivity.class);
                startActivity(intent);
                return true;
            }
        });

        Preference mKCal = findPreference("device_kcal");
        mKCal.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity().getApplicationContext(), KCalSettingsActivity.class);
                startActivity(intent);
                return true;
            }
        });

        mVmaxOverrideModeSwitch = (TwoStatePreference) findPreference(PREF_VMAX_OVERRIDE_SWITCH);
        mVmaxOverrideModeSwitch.setEnabled(VibratorOverrideModeSwitch.isSupported());
        mVmaxOverrideModeSwitch.setChecked(VibratorOverrideModeSwitch.isCurrentlyEnabled(this.getContext()));
        mVmaxOverrideModeSwitch.setOnPreferenceChangeListener(new VibratorOverrideModeSwitch(getContext()));

        VibratorStrengthPreference mVibratorStrength = findPreference("vib_strength");
        mVibratorStrength.setEnabled(VibratorStrengthPreference.isSupported());

        CallVibratorStrengthPreference mCallVibratorStrength = findPreference("call_vib_strength");
        mCallVibratorStrength.setEnabled(CallVibratorStrengthPreference.isSupported());

        NotificationVibratorStrengthPreference mNotificationVibratorStrength = findPreference("notif_vib_strength");
        mNotificationVibratorStrength.setEnabled(NotificationVibratorStrengthPreference.isSupported());

        mUSBFastChgModeSwitch = (TwoStatePreference) findPreference(PREF_USB_FAST_CHARGE_SWITCH);
        mUSBFastChgModeSwitch.setEnabled(USBFastChgModeSwitch.isSupported());
        mUSBFastChgModeSwitch.setChecked(USBFastChgModeSwitch.isCurrentlyEnabled(this.getContext()));
        mUSBFastChgModeSwitch.setOnPreferenceChangeListener(new USBFastChgModeSwitch(getContext()));

        mOTGModeSwitch = (TwoStatePreference) findPreference(PREF_OTG_SWITCH);
        mOTGModeSwitch.setEnabled(OTGModeSwitch.isSupported());
        mOTGModeSwitch.setChecked(OTGModeSwitch.isCurrentlyEnabled(this.getContext()));
        mOTGModeSwitch.setOnPreferenceChangeListener(new OTGModeSwitch(getContext()));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }
}

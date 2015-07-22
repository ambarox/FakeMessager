package com.fakeMessager.controller;

/**
 * Created by lasitha on 7/21/15.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.ITelephony;
import com.fakeMessager.service.ContactNMessageDao;

import java.lang.reflect.Method;

/**
 * The type Call barring.
 */
public class CallBarring extends BroadcastReceiver {
    private String number;

    /** {@inheritDoc} */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals("android.intent.action.PHONE_STATE"))
            return;
        else {
            number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (ContactNMessageDao.numbers.contains(number)) {
                disconnectPhoneItelephony(context);
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, ContactNMessageDao.numberNMessageMap.get(number), null, null);
                return;
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void disconnectPhoneItelephony(Context context) {
        ITelephony telephonyService;
        TelephonyManager telephony = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class c = Class.forName(telephony.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(telephony);
            telephonyService.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

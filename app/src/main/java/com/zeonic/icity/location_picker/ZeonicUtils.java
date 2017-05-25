package com.zeonic.icity.location_picker;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;

import com.zeonic.icity.location_picker.entity.ZeonicEncrypt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ninja on 5/10/17.
 */
public class ZeonicUtils {
    public static boolean isEmpty(Collection collection) {
        if(collection == null || collection.isEmpty())
            return true;
        return false;
    }

    public static String getDeviceToken() {
        return Settings.Secure.getString(BootstrapApplication.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getVersionName() {
        PackageInfo pInfo = null;
        try {
            Context ctx = BootstrapApplication.getInstance();
            pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "unknown";
        }
    }

    public static String getIdentification() {
        return getIdentification(ZeonicEncrypt.PhoneStatus.Running);
    }

    public static String getIdentification(ZeonicEncrypt.PhoneStatus status) {
        String phoneNumber = null;
//        phoneNumber = "15800800838";
        ZeonicEncrypt.Numberflag numberflag =
                phoneNumber == null ? ZeonicEncrypt.Numberflag.NoPhone : ZeonicEncrypt.Numberflag.HasPhone;
        return ZeonicEncrypt.getInstance().encrypt(
                ZeonicEncrypt.EncryptOrder.Sequence, getDeviceToken()
                , numberflag, status
                , phoneNumber);
    }

    // not work now
    @Deprecated
    public String getPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager) BootstrapApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        return tMgr.getLine1Number();
    }

    /**
     * left trim
     * @param s
     * @return
     */
    public static String ltrim(String s){
        final String ltrim = s.replaceAll("^\\s+","");
        return ltrim;
    }

    /**
     * right trim
     * @param s
     * @return
     */
    public static String rtrim(String s){
        final String rtrim = s.replaceAll("\\s+$","");
        return rtrim;
    }

    public static boolean isEmpty(CharSequence filePath) {
        if(filePath == null || filePath.length() == 0)
            return  true;
        else
            return false;
    }

//    public static String imageUrlBuild2x3x(String originImageUrl) {
//        if(originImageUrl == null) return  null;
//        int index = originImageUrl.lastIndexOf(".");
//        if (index >= 0) {
//            int densityDpi = BootstrapApplication.getInstance().getResources().getDisplayMetrics().densityDpi;
//            float density = BootstrapApplication.getInstance().getResources().getDisplayMetrics().density;
////            Timber.e("the device's density is %s ", String.valueOf(density));
////            Timber.e("the device's densityDpi is %d ", densityDpi);
//            String densityStr;
//            if(density >= 3){
//                Timber.d("the device's use @3x");
//                densityStr = "@3x";
//            }else {
//                densityStr = "@2x";
//                Timber.d("the device's use @2x");
//            }
//            String imageUrl = originImageUrl.substring(0, index) + densityStr + originImageUrl.substring(index);
//            return imageUrl;
//        }
//        return null;
//    }
//
//    /**
//     * 4567
//     * @param originImageUrl
//     * @return
//     */
//    public static String imageUrlBuild456(String originImageUrl) {
//        if(originImageUrl == null) return  null;
//        int densityDpi = BootstrapApplication.getInstance().getResources().getDisplayMetrics().densityDpi;
////        Timber.e("densityDpi " + densityDpi);
//        String postFix;
//        switch (densityDpi){
//            case DisplayMetrics.DENSITY_LOW:
////                Toast.makeText(context, "LDPI", Toast.LENGTH_SHORT).show();
////                break;
//            case DisplayMetrics.DENSITY_MEDIUM:
//                postFix = "@4";
////                Toast.makeText(context, "MDPI", Toast.LENGTH_SHORT).show();
//                break;
//            case DisplayMetrics.DENSITY_HIGH:
//                postFix = "@5";
////                Toast.makeText(context, "HDPI", Toast.LENGTH_SHORT).show();
//                break;
//            case DisplayMetrics.DENSITY_XHIGH:
//                postFix = "@6";
////                Toast.makeText(context, "XHDPI", Toast.LENGTH_SHORT).show();
//                break;
//            case DisplayMetrics.DENSITY_560:
//            case DisplayMetrics.DENSITY_XXHIGH:
//                postFix = "@7";
////                Toast.makeText(context, "XHDPI", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                postFix = "@6";
//                break;
//        }
////        Timber.e("postFix " + postFix);
//        int index = originImageUrl.lastIndexOf(".");
//        if (index >= 0) {
//            String imageUrl = originImageUrl.substring(0, index) + postFix + originImageUrl.substring(index);
//            return imageUrl;
//        }
//        return null;
//    }

    public static boolean isEmpty(Map map) {
        if(map == null || map.isEmpty())
            return true;
        else
            return false;
    }

    /**
     * key:value;key2:value2;
     * @param mapPair
     * @return
     */
    public static String zhaoshijieString(@Nullable HashMap<String, String> mapPair) {
        if(ZeonicUtils.isEmpty(mapPair)) return "";
        StringBuffer sb = new StringBuffer();
        Iterator<Map.Entry<String, String>> iterator = mapPair.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            sb.append(entry.getKey());
            sb.append(":");
            sb.append(entry.getValue());
            sb.append(";");
        }
        // delete last ;
        if(sb.length() > 0){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
}
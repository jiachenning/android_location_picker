package com.zeonic.icity.location_picker.entity;

import android.support.annotation.NonNull;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import timber.log.Timber;

/**
 * Created by ninja on 5/10/17.
 */

public class ZeonicEncrypt {
    private static final boolean DEBUG = true;
    public static ZeonicEncrypt instance = new ZeonicEncrypt();
    public static final String EMPTY_PHONE_NUMBER="00000000000";

    public static ZeonicEncrypt getInstance(){
        return instance;
    }

    public static void main(String[] args) {
//        ZeonicEncrypt encrypt = new ZeonicEncrypt();
//        String androdId = "e5004ac7e2f08c9";
//        String phoneno = "15800800838";
//        Calendar date = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
//        String encrypted = encrypt.encrypt(EncryptOrder.Sequence, androdId, Numberflag.HasPhone, PhoneStatus.Running, phoneno, date);
//        System.out.println("encrypted:");
//        System.out.println(encrypted);
        SecureRandom random = new SecureRandom();
        String s = new BigInteger(130, random).toString(32);
        System.out.println(s);
        System.out.println(s.length());
        s= UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(s);
        System.out.println(s.length());
    }

    //Character.toString(char)
    public char[] dic = new char[62];

    public ZeonicEncrypt() {
        //init
        int index = 0;
        for (int i = 0; i < 10; i++) {
            dic[index++] = Character.forDigit(i, 10);
        }
        for (int i = (int) 'A'; i <= (int) 'Z'; i++) {
            dic[index++] = (char) i;
        }
        for (int i = (int) 'a'; i <= (int) 'z'; i++) {
            dic[index++] = (char) i;
        }
    }

    public enum EncryptOrder {
        Sequence,
        Reverse;
    }

    /**
     * 0 is no phone
     * 1 is has phone
     */
    public enum Numberflag {
        NoPhone,
        HasPhone
    }

    public enum PhoneStatus {
        Offline,
        Running,
        Background;
    }

    public String encrypt(EncryptOrder sequence, String deviceId, Numberflag numberflag, PhoneStatus status, String phoneNumber) {
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        return encrypt(sequence, deviceId, numberflag, status, phoneNumber, now);
    }

    /**
     * if numberflag = 0, phoneNumber = 0;
     * if phone number is not valid, it is accepted. and will set numberflag to 0
     * @param order
     * @param deviceId    length = 64-bit = 16 hex-string length
     * @param numberflag
     * @param status
     * @param phoneNumber
     * @param date
     * @return
     */
    public String encrypt(EncryptOrder order, String deviceId, Numberflag numberflag, PhoneStatus status, String phoneNumber, Calendar date) {
        if (DEBUG) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now = dateFormat.format(date.getTime());
            String debugMessage = String.format("encrypt: order %d, deviceId %s, numberflag %d, status %d, phoneNumber %s, date %s "
                    , order.ordinal()
                    , deviceId
                    , numberflag.ordinal()
                    , status.ordinal()
                    , phoneNumber
                    , now);
//            System.out.println(debugMessage);
            Timber.d(debugMessage);
        }
        // try to format
        if(numberflag != Numberflag.NoPhone){
            Timber.i(String.format("phone number %s is not valid", phoneNumber));
            phoneNumber=EMPTY_PHONE_NUMBER;
            numberflag = Numberflag.NoPhone;
        }
        //
        if(phoneNumber == null)
            phoneNumber = ZeonicEncrypt.EMPTY_PHONE_NUMBER;
        if (!validEncryptInput(order, deviceId, numberflag, status, phoneNumber, date))
            throw new RuntimeException("invalid param in " + getClass().getSimpleName() + ":encrypt()");
        StringBuffer sb = new StringBuffer();
        sb.append(deviceId);
        sb.append(deviceId);
        sb.append(deviceId);
        sb.append(deviceId);
        deviceId = sb.toString();

        //pre-process origin value
        int encryptChar = getRandom() % 6 + 1;
        int order_ = order.ordinal() + (getRandom() % 31);
        int encryptChar_ = encryptChar + getRandom() % 10 * 6;
        int numberflag_ = numberflag.ordinal() + (getRandom() % 31) * 2;
        int status_ = status.ordinal() + (getRandom() % 20) * 3;

        //get time in server location
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddHHmmssddHHmmss");
        String formattedTime = dateFormat.format(date.getTime());
        int[] time_bits = new int[formattedTime.length()];
        for (int i = 0; i < formattedTime.length(); i++) {
            time_bits[i] = Character.digit(formattedTime.charAt(i), 10) + getRandom() % 6 * 10;
        }
        //bind
        //split deviceId to 16 group, mix salt(15) to 16 groups
        //build salt
        int[] salt = new int[15];
        salt[0] = order_;
        salt[1] = encryptChar_;
        salt[2] = numberflag_;
        salt[3] = status_;
        for (int i = 0; i < 11; i++) {
            if (numberflag == Numberflag.NoPhone) {
                salt[4 + i] = 0;
            } else {
                salt[4 + i] = Character.digit(phoneNumber.charAt(i), 10) * encryptChar;
            }
        }
        if (order == EncryptOrder.Reverse) {
            salt = revers(salt);
        }

        char[] encryptedSalt = encryptSalt(salt);
        //insert date to deviceId.there are 16 groups,each has 4 elements
        List<Character> encrypeted = new ArrayList<>(95);
        for (int i = 0; i < 16; i++) {
            List<Character> slice = new ArrayList<>(5);

            int positionSum = getCharPosition(dic, deviceId.charAt(i + 0)) + getCharPosition(dic, deviceId.charAt(i + 1))
                    + getCharPosition(dic, deviceId.charAt(i + 2)) + getCharPosition(dic, deviceId.charAt(i + 3));
            int position = (positionSum + time_bits[i]) % 3;
            slice.add(deviceId.charAt(i + 0));
            slice.add(deviceId.charAt(i + 1));
            slice.add(deviceId.charAt(i + 2));
            slice.add(deviceId.charAt(i + 3));
            slice.add(position+1, Character.forDigit(time_bits[i], 10));

            // assemble slices
            encrypeted.addAll(slice);
            if(i<15) encrypeted.add(encryptedSalt[i]);
        }
        sb = new StringBuffer();
        for(char c : encrypeted){
            sb.append(c);
        }
        return sb.toString();
    }

    private boolean validEncryptInput(EncryptOrder order, String deviceId, Numberflag numberflag, PhoneStatus status, String phoneNumber, Calendar date) {
        if(order == null || numberflag == null || status == null || phoneNumber == null || date == null)
            return false;
        else
            return true;
    }

    private char[] encryptSalt(@NonNull int[] salt) {
        char[] encrypted = new char[salt.length];
        for (int i = 0; i < salt.length; i++) {
            encrypted[i] = dic[salt[i]];
        }
        return encrypted;
    }

    /**
     * @param dic
     * @param c
     * @return return -1 if not found
     */
    private int getCharPosition(char[] dic, char c) {
        for (int i = 0; i < dic.length; i++) {
            if (dic[i] == c) return i;
        }
        return -1;
    }

    private int[] revers(int[] salt) {
        int[] revers = new int[salt.length];
        for (int i = 0; i < salt.length; i--) {
            revers[i] = salt[salt.length-1-i];
        }
        return revers;
    }

    /**
     * get a random int, from 0~100
     *
     * @return
     */
    public int getRandom() {
        return (int) Math.random() * 100;
    }
}

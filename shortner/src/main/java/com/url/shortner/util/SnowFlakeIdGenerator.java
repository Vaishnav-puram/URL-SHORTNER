package com.url.shortner.util;


import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.Instant;
import java.util.Enumeration;

public class SnowFlakeIdGenerator {
    private static final long CUSTOME_EPOCH=1540272380000L;
    private static final long NODEIDBITS=10L;
    private static final long COUNTERBITS=12L;

    private static final long MAX_COUNTER = (1L << COUNTERBITS) - 1; // Maximum counter value: 2^12 - 1 = 4095

    // or you can define like this as well
    // private static final long MAX_COUNTER = 4095 (2^12-1)

    private static long lastTimestamp = -1L; // Track the last timestamp
    private static long counter = 0; // Counter for the current millisecond

    public static long timestamp(){
        return Instant.now().toEpochMilli()-CUSTOME_EPOCH;
    }

    public static long getHardwareAddress() throws SocketException {
        Enumeration<NetworkInterface> interfaces=NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()){
            NetworkInterface networkInterface=interfaces.nextElement();
            byte[] mac=networkInterface.getHardwareAddress();
            if(mac!=null){
                long machineId=0;
                for (int i=0;i<mac.length;i++){
                    machineId=(machineId<<8)|(mac[i] & 0xFF);
                }
                return machineId;
            }
        }
        throw new IllegalStateException("No valid MAC address found.");
    }

    public static long uniqueIdGenerator() throws SocketException {
        long currentTimestamp=timestamp();
        if(currentTimestamp<lastTimestamp){
            throw new IllegalStateException("Clock moving backwords ! , unable to generate id !");
        }
        if(currentTimestamp==lastTimestamp){
            counter=(counter+1) & MAX_COUNTER; // Increment counter, wrap around if max is reached , so it makes to 0
            if(counter==0){
                // Counter exhausted, wait for the next millisecond
                while (currentTimestamp == lastTimestamp) {
                    currentTimestamp = timestamp();
                }
            }
        }else{
            // reset counter to 0 for each new millisecond
            counter=0;
        }
        lastTimestamp=currentTimestamp;

        long id=0;
        // for filling 41 bits timestamp bits
        id=timestamp()<<(NODEIDBITS+COUNTERBITS);
        // for filling 10 bit machine ID bits
        id|=(getHardwareAddress()<<(COUNTERBITS));
        // for filling 12 bit counter bits
        id|=counter;
        return id;
    }

}

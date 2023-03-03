// IMyAidlInterface.aidl
package com.example.aidlserver;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    int getColor();
    int increment();
    String getMessage();
}
package com.anna.sent.soft.expandablelayout;

public class TestProguardInLibraryModule {
    private static final String TAG = "test_lib";

    private final String testField1 = "testField1", testField2 = "testField2";

    public void testMethod1(StringBuilder sb) {
        sb.append(String.format("%s\t%s: %s\n", TAG, "testMethod1", testField1));
    }

    public void testMethod2(StringBuilder sb) {
        sb.append(String.format("%s\t%s: %s\n", TAG, "testMethod2", testField2));
    }
}

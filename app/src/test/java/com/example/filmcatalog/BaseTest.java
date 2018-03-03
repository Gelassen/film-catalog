package com.example.filmcatalog;

import android.security.NetworkSecurityPolicy;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@RunWith(RobolectricTestRunner.class)
@Config(
        shadows = BaseTest.MyNetworkSecurityPolicy.class,
        application = TestApp.class,
        sdk = 23
)
public class BaseTest {


    @Implements(NetworkSecurityPolicy.class)
    public static class MyNetworkSecurityPolicy {

        @Implementation
        public static NetworkSecurityPolicy getInstance() {
            try {
                Class<?> shadow = MyNetworkSecurityPolicy.class.forName("android.security.NetworkSecurityPolicy");
                return (NetworkSecurityPolicy) shadow.newInstance();
            } catch (Exception e) {
                throw new AssertionError();
            }
        }

        @Implementation
        public boolean isCleartextTrafficPermitted() {
            return true;
        }
    }
}

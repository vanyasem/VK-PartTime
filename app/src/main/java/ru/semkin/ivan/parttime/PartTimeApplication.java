package ru.semkin.ivan.parttime;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.vk.sdk.VKSdk;

/**
 * Created by Ivan Semkin on 5/6/18
 */
public class PartTimeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //initializeLogging();
        //initializeLeakCanary();

        VKSdk.initialize(getApplicationContext());
    }

    /*private void initializeLogging() {
        if (BuildConfig.TIMBER_ENABLE) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    if (priority == Log.ERROR) {
                        super.log(priority, tag, message, t);
                    }
                }
            });
        }
    }

    private void initializeLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            return;
        }
        LeakCanary.install(this);
    }*/
}

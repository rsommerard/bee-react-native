package com.reactplayground;

import android.app.Application;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import io.apisense.sdk.APISENSE;
import io.apisense.sdk.adapter.SimpleAPSCallback;
import io.apisense.sdk.core.store.Crop;

public class ApisenseModule extends ReactContextBaseJavaModule {
    private static final String sdkKey = "";
    private static final String accessKey = "";

    private static final String cropIdentifier = "";
    private APISENSE.Sdk sdk;

    public ApisenseModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "Apisense";
    }

    @ReactMethod
    public void foo() {
        APISENSE apisense = new APISENSE((Application) getReactApplicationContext().getApplicationContext());
        apisense.useSdkKey(sdkKey);
        apisense.useAccessKey(accessKey);

        sdk = apisense.getSdk();

        if (sdk.getSessionManager().isConnected()) {
            installOrUpdate();
        } else {
            sdk.getSessionManager().applicationLogin(new SimpleAPSCallback<Void>() {
                @Override
                public void onDone(Void aVoid) {
                    installOrUpdate();
                }
            });
        }
    }

    private void installOrUpdate() {
        sdk.getCropManager().installOrUpdate(cropIdentifier,  new SimpleAPSCallback<Crop>() {
            @Override
            public void onDone(Crop crop) {
                // Crop Installed, ready to be started.
                sdk.getCropManager().start(crop, new SimpleAPSCallback<Crop>() {
                    @Override
                    public void onDone(Crop crop) {
                        // Crop finally started.
                        Toast.makeText(getReactApplicationContext(), "Crop Started", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}

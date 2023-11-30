package my.screensaver.plugin;

import android.annotation.TargetApi;
import android.content.Intent;
import android.service.dreams.DreamService;
import android.webkit.WebView;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;

public class MyScreensaverPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("setScreensaver".equals(action)) {
            String url = args.getString(0);
            setScreensaver(url, callbackContext);
            return true;
        }
        return false;
    }
    

    public void setScreensaver(String url, CallbackContext callbackContext) {

        try {

            Intent intent = new Intent(cordova.getActivity().getApplicationContext(), MyScreensaverService.class);

            intent.putExtra("url", url);

            cordova.getActivity().startService(intent);

            callbackContext.success( url );
        } catch (Exception e) {
            // Send an error result to the callback
            callbackContext.error(e.getMessage());
        }

    }

}


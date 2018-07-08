package opr.capacidad;

import android.app.Application;

import net.gotev.uploadservice.UploadService;

// https://github.com/gotev/android-upload-service/wiki/Setup
public class Initializer extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
    }
}

package ptpn12.amanat.asem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

import ptpn12.amanat.asem.R;
import ptpn12.amanat.asem.api.AsetInterface;
import ptpn12.amanat.asem.api.model.AsetModel2;
import ptpn12.amanat.asem.api.model.CheckVersionModel;
import ptpn12.amanat.asem.api.model.VersionModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String LOGIN_CREDENTIALS = "LOGIN_CREDENTIALS";
    SharedPreferences sharedPreferences;
    AsetInterface asetInterface;
    String versionName;
    Integer versionCode;

//    private static final int MY_REQUEST_CODE = 999;
////    Task<AppUpdateInfo> appUpdateInfoTask;
//    AppUpdateManager appUpdateManager;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

        versionName = BuildConfig.VERSION_NAME;
        versionCode = BuildConfig.VERSION_CODE;

        AsetInterface asetInterface;
        asetInterface = AsemApp.getApiClient().create(AsetInterface.class);
        Call<CheckVersionModel> call = asetInterface.checkVersion(versionCode);


        call.enqueue(new Callback<CheckVersionModel>() {
            @Override
            public void onResponse(Call<CheckVersionModel> call, Response<CheckVersionModel> response) {
                if (!response.isSuccessful() && response.body() == null) {
                    Toast.makeText(getApplicationContext(), "Error " + String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    return;
                } else {
                    android.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashScreen.this);
                    if (!response.body().getData()) {

                        // set title dialog
                        alertDialogBuilder.setTitle("Update Versi Terbaru");

                        // set pesan dari dialog
                        alertDialogBuilder
                                .setMessage("Update versi aplikasi anda yang terbaru")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=ptpn12.amanat.asem"));
                                        startActivity(browserIntent);
                                        finish();

                                    }
                                });
                        alertDialogBuilder.show();
//                        new Handler().postDelayed(this::checkLogin, 2000);
                        return;
                    } else {
                        new Handler().postDelayed(this::checkLogin, 2000);
//                        Toast.makeText(getApplicationContext(),"masuk",Toast.LENGTH_LONG).show();
                    }
                }
                new Handler().postDelayed(this::checkLogin, 2000);

//                CheckForAppUpdate();
            }

//            //check versi dalam apps
//
//            private void CheckForAppUpdate(){
//                appUpdateManager = AppUpdateManagerFactory.create(this);
//
//                // Returns an intent object that you use to check for an update.
////                Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
//
//                // Checks that the platform will allow the specified type of update.
//                appUpdateManager
//                        .getAppUpdateInfo()
//                        .addOnSuccessListener(appUpdateInfo -> {
//                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                            // This example applies an immediate update. To apply a flexible update
//                            // instead, pass in AppUpdateType.FLEXIBLE
//                            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
//                        // Request the update.
//                        try {
//                            appUpdateManager.startUpdateFlowForResult(
//                                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
//                                    appUpdateInfo,
//                                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
//                                    AppUpdateType.FLEXIBLE,
//                                    // The current activity making the update request.
//                                    this,
//                                    // Include a request code to later monitor this update request.
//                                    MY_REQUEST_CODE);
//                        } catch (IntentSender.SendIntentException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                });
//
//                // Before starting an update, register a listener for updates.
//                appUpdateManager.registerListener(listener);
//            }
//
//            @Override
//            public void onActivityResult(int requestCode, int resultCode, Intent data) {
//                super.onActivityResult(requestCode, resultCode, data);
//                if (requestCode == MY_REQUEST_CODE) {
//                    if (resultCode != RESULT_OK) {
//                        Log.w("SplashScreen", "Update flow failed! Result code: " + resultCode);
//                        // If the update is cancelled or fails,
//                        // you can request to start the update again.
//                    }
//                }
//            }
//
//    // Checks that the update is not stalled during 'onResume()'.
//// However, you should execute this check at all app entry points.
//    private void popupSnackbarForCompleteUpdate() {
//        Snackbar snackbar =
//                Snackbar.make(
//                        findViewById(android.R.id.content),
//                        "An update has just been downloaded.",
//                        Snackbar.LENGTH_INDEFINITE);
//        snackbar.setAction("INSTALL", view -> appUpdateManager.completeUpdate());
//        snackbar.setActionTextColor(
//                getResources().getColor(android.R.color.holo_green_light));
//        snackbar.show();
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        // When status updates are no longer needed, unregister the listener.
//        appUpdateManager.unregisterListener(listener);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        appUpdateManager
//                .getAppUpdateInfo()
//                .addOnSuccessListener(appUpdateInfo -> {
//
//                    // If the update is downloaded but not installed,
//                    // notify the user to complete the update.
//                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
//                        popupSnackbarForCompleteUpdate();
//                    }
//                });
//    }
//
//    InstallStateUpdatedListener listener = state -> {
//        if (state.installStatus() == InstallStatus.DOWNLOADED) {
//            // After the update is downloaded, show a notification
//            // and request user confirmation to restart the app.
//            popupSnackbarForCompleteUpdate();
//        }
//
//    };


            private void checkLogin() {
                String user_id = sharedPreferences.getString("user_id", "-");
                Log.d("asetapix", user_id);

                if (user_id.equals("-")) {
//            Toast.makeText(getApplicationContext(),"Anda sudah login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                } else {
//            Toast.makeText(getApplicationContext(),"Harap login terlebih daulu",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreen.this, Home.class);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<CheckVersionModel> call, Throwable t) {

            }
        });

    }
}
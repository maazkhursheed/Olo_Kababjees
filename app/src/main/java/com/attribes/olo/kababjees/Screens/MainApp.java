package com.attribes.olo.kababjees.Screens;

import android.app.Application;
import com.attribes.olo.kababjees.R;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

/**
 * Created by Maaz on 7/11/2016.
 */

@ReportsCrashes(
        formUri = "https://mk123.cloudant.com/acra-kbj/_design/acra-storage/_update/report",
        reportType = HttpSender.Type.JSON,
        httpMethod = HttpSender.Method.POST,
        formUriBasicAuthLogin = "yonothawshoutheningenera",
        formUriBasicAuthPassword = "a001c4ee7209de6fea824d156a6199f607812de6",
        formKey = "", // This is required for backward compatibility but not used
        customReportContent = {
                ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION,
                ReportField.PACKAGE_NAME,
                ReportField.REPORT_ID,
                ReportField.BUILD,
                ReportField.STACK_TRACE
        },
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.toast_crash
)

public class MainApp extends Application {

        @Override
        public void onCreate() {
                super.onCreate();
                // The following line triggers the initialization of ACRA
                ACRA.init(this);
        }
}

package site.meowcat;

import android.content.pm.ActivityInfo;
import com.jme3.app.AndroidHarness;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class MainActivity extends AndroidHarness {

    public MainActivity() {
        // Set the application class to run
        appClass = "site.meowcat.OrbitPi";
        // Try to quit the application on "Back" button tap
        exitDialogTitle = "Exit?";
        exitDialogMessage = "Press Yes to quit.";
        // Enable splash screen at start (optional)
        splashPicID = 0; // Set to R.drawable.splash_image if you have one
    }

    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

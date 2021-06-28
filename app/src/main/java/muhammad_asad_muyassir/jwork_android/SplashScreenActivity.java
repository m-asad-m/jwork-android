package muhammad_asad_muyassir.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

/**
 * Class SplashScreenActivity adalah activity yang
 * menampilkan splash screen
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class SplashScreenActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread1 = new Thread() {
            public void run() {
                try {
                    sleep(2500);

                    if (id != 0){
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        intent.putExtra("extraJobseekerId", id);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread2 = new Thread() {
            public void run() {
                sharedPrefManager = new SharedPrefManager(SplashScreenActivity.this);
                id = sharedPrefManager.getSPId();
            }
        };

        thread1.start();
        thread2.start();
    }
}
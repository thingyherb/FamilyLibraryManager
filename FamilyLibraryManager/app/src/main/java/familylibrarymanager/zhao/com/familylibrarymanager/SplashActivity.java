package familylibrarymanager.zhao.com.familylibrarymanager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * 欢迎页
 */
public class SplashActivity extends AppCompatActivity {
    private TextView mTitleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "Avenir LT 95 Black.ttf");
        mTitleTv.setTypeface(typeface);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }, 1500);
    }
}

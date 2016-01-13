package jp.yoshi_misa_kae.tatami_sample.view;

import android.content.Intent;
import android.view.View;

import jp.yoshi_misa_kae.tatami.annotations.view.ActivityInfo;
import jp.yoshi_misa_kae.tatami.annotations.view.Click;
import jp.yoshi_misa_kae.tatami.view.TatamiActivity;
import jp.yoshi_misa_kae.tatami_sample.R;

@ActivityInfo(layoutId = R.layout.activity_main)
public class MainActivity extends TatamiActivity {

    @Click(R.id.button1)
    private void onClickButton1(View view) {
        startActivity(new Intent(getApplicationContext(), Test1Activity.class));
    }

    @Click(R.id.button2)
    private void onClickButton2(View view) {
        startActivity(new Intent(getApplicationContext(), Test2Activity.class));
    }

    @Click(R.id.button3)
    private void onClickButton3(View view) {
        startActivity(new Intent(getApplicationContext(), Test3Activity.class));
    }
}

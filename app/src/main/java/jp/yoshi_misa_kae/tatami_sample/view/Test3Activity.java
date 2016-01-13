package jp.yoshi_misa_kae.tatami_sample.view;

import android.os.Bundle;
import android.view.View;

import jp.yoshi_misa_kae.tatami.annotations.view.ActivityInfo;
import jp.yoshi_misa_kae.tatami.annotations.view.Click;
import jp.yoshi_misa_kae.tatami.view.TatamiActivity;
import jp.yoshi_misa_kae.tatami_sample.R;

/**
 * Created by ymizusawa on 2016/01/13.
 */
@ActivityInfo(layoutId = R.layout.activity_test3)
public class Test3Activity extends TatamiActivity {

    private int id = 0;

    @Click(R.id.test3Button)
    private void test3Button(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        addFragmentAnimation(Test3Fragment.class, R.id.contents, bundle, "Test3Fragment" + id, true);
        id++;
    }

}

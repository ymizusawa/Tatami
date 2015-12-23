package jp.yoshi_misa_kae.tatami_sample.view;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import jp.yoshi_misa_kae.tatami.annotations.view.ActivityInfo;
import jp.yoshi_misa_kae.tatami.view.TatamiActivity;
import jp.yoshi_misa_kae.tatami.view.widget.TatamiViewPager;
import jp.yoshi_misa_kae.tatami_sample.R;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/23.
 */
@ActivityInfo(layoutId = R.layout.activity_test2)
public class Test2Activity extends TatamiActivity implements
        TatamiViewPager.TatamiViewPagerItemListener {

    private TatamiViewPager viewPager;

    @Override
    public void callOnCreate() {
        super.callOnCreate();

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        viewPager.init(this, getSupportFragmentManager(), list);
    }

    @Override
    public Fragment getViewPagerItem(Object obj, int position) {
        return Test2Fragment.newInstance((Integer) obj);
    }

}

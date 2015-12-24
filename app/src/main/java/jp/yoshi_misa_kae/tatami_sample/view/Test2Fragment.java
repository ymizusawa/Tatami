package jp.yoshi_misa_kae.tatami_sample.view;

import android.widget.TextView;

import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraInt;
import jp.yoshi_misa_kae.tatami.annotations.view.FragmentInfo;
import jp.yoshi_misa_kae.tatami.view.TatamiFragment;
import jp.yoshi_misa_kae.tatami_sample.R;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/23.
 */
@FragmentInfo(layoutId= R.layout.fragment_test2)
public class Test2Fragment extends TatamiFragment {

//    public static Test2Fragment newInstance(Integer value) {
//        Test2Fragment fragment = new Test2Fragment();
//
//        Bundle bundle = new Bundle();
//        bundle.putInt(VALUE, value);
//        fragment.setArguments(bundle);
//
//        return fragment;
//    }

    private TextView textView;

    @ExtraInt
    private int value;

    @Override
    public void callOnActivityCreated() {
        super.callOnActivityCreated();

        textView.setText("" + value);
    }
}

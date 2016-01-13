package jp.yoshi_misa_kae.tatami_sample.view;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraInt;
import jp.yoshi_misa_kae.tatami.annotations.view.FragmentInfo;
import jp.yoshi_misa_kae.tatami.view.TatamiFragment;
import jp.yoshi_misa_kae.tatami_sample.R;

/**
 * Created by ymizusawa on 2016/01/13.
 */
@FragmentInfo(layoutId= R.layout.fragment_test3)
public class Test3Fragment extends TatamiFragment {

    private LinearLayout linearLayout;
    private TextView textView4;

    @ExtraInt
    private int id;

    @Override
    public void callOnActivityCreated(Bundle savedInstanceState) {
        super.callOnActivityCreated(savedInstanceState);

        int backgroundColor = 0;
        switch(id % 3){
            case 0:{
                backgroundColor = Color.RED;
                break;
            }
            case 1:{
                backgroundColor = Color.GREEN;
                break;
            }
            case 2:{
                backgroundColor = Color.BLUE;
                break;
            }
        }
        linearLayout.setBackgroundColor(backgroundColor);
        textView4.setText("" + id);
    }
}

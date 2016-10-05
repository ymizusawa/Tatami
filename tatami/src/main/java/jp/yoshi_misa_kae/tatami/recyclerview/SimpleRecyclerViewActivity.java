package jp.yoshi_misa_kae.tatami.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;

import jp.yoshi_misa_kae.tatami.TatamiBindViewBindView;
import jp.yoshi_misa_kae.tatami.R;

public abstract class SimpleRecyclerViewActivity extends TatamiRecyclerViewActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TatamiBindViewBindView tatamiBindView = new TatamiBindViewBindView();
        tatamiBindView.bindView(this, R.layout.activity_common_list);
        super.onCreate(savedInstanceState);
    }

}

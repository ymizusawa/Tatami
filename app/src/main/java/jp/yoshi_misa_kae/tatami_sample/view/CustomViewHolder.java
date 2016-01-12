package jp.yoshi_misa_kae.tatami_sample.view;

import android.view.View;
import android.widget.TextView;

import jp.yoshi_misa_kae.tatami.view.widget.TatamiRecyclerView;
import jp.yoshi_misa_kae.tatami_sample.R;

/**
 * Created by ymizusawa on 2015/12/18.
 */
public class CustomViewHolder
        extends TatamiRecyclerView.TatamiViewHolder
{

    public final TextView textView2;
    public final TextView textView3;

    public CustomViewHolder(View itemView) {
        super(itemView);

        textView2 = (TextView) itemView.findViewById(R.id.textView2);
        textView3 = (TextView) itemView.findViewById(R.id.textView3);
    }

}

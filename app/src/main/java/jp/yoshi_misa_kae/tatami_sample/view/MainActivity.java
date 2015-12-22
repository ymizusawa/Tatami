package jp.yoshi_misa_kae.tatami_sample.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import jp.yoshi_misa_kae.tatami.annotations.view.ActivityInfo;
import jp.yoshi_misa_kae.tatami.annotations.view.Click;
import jp.yoshi_misa_kae.tatami.view.widget.TatamiRecyclerView;
import jp.yoshi_misa_kae.schedules.R;

@ActivityInfo(layoutId = R.layout.activity_main_old)
public class MainActivity extends Super1Activity implements
//        TatamiRecyclerView.TatamiRecyclerViewCreateViewListener,
        TatamiRecyclerView.TatamiRecyclerViewBindViewListener,
        TatamiRecyclerView.TatamiRecyclerViewItemClickListener {

    @Override
    public void onItemClick(int position, Object value) {
        Toast.makeText(getApplicationContext(), "position : " + position + ((Test) value).toString(), Toast.LENGTH_LONG).show();
    }


//    @TextViewInfo(id = R.id.textView, textId = R.string.app_name)
    private TextView textView;

//    @EditTextInfo(id = R.id.editText, textId = R.string.app_name)
    private EditText editText;

    private int black;
    private int test;
    private String testStr;
    private TatamiRecyclerView recyclerView;
    private ArrayList<Test> list;

    @Override
    public void callOnCreate() {
        Log.v("test", "button1 " + (button1 == null ? "is null." : "is not null."));
        Log.v("test", "button2 " + (button2 == null ? "is null." : "is not null."));
        Log.v("test", "textView " + (textView == null ? "is null." : "is not null."));
        Log.v("test", "editText " + (editText == null ? "is null." : "is not null."));
        Log.v("black", "black " + black);
        Log.v("test", "test " + test);
        Log.v("testStr", "testStr " + testStr);

        list = new ArrayList<>();
        {
            Test test = new Test();
            test.textView2 = "111";
            test.textView3 = "222";
            list.add(test);
        }
        {
            Test test = new Test();
            test.textView2 = "333";
            test.textView3 = "444";
            list.add(test);
        }
        {
            Test test = new Test();
            test.textView2 = "555";
            test.textView3 = "666";
            list.add(test);
        }

        {
            Test test = new Test();
            test.textView2 = "111";
            test.textView3 = "222";
            list.add(test);
        }
        {
            Test test = new Test();
            test.textView2 = "333";
            test.textView3 = "444";
            list.add(test);
        }
        {
            Test test = new Test();
            test.textView2 = "555";
            test.textView3 = "666";
            list.add(test);
        }

        {
            Test test = new Test();
            test.textView2 = "111";
            test.textView3 = "222";
            list.add(test);
        }
        {
            Test test = new Test();
            test.textView2 = "333";
            test.textView3 = "444";
            list.add(test);
        }
        {
            Test test = new Test();
            test.textView2 = "555";
            test.textView3 = "666";
            list.add(test);
        }

        {
            Test test = new Test();
            test.textView2 = "111";
            test.textView3 = "222";
            list.add(test);
        }
        {
            Test test = new Test();
            test.textView2 = "333";
            test.textView3 = "444";
            list.add(test);
        }
        {
            Test test = new Test();
            test.textView2 = "555";
            test.textView3 = "666";
            list.add(test);
        }

        {
            Test test = new Test();
            test.textView2 = "111";
            test.textView3 = "222";
            list.add(test);
        }
        {
            Test test = new Test();
            test.textView2 = "333";
            test.textView3 = "444";
            list.add(test);
        }
        {
            Test test = new Test();
            test.textView2 = "555";
            test.textView3 = "666";
            list.add(test);
        }

        recyclerView.setTatamiRecyclerViewListener(this);
        recyclerView.setList(list);
    }

    public static class Test {
        public String textView2;
        public String textView3;

        @Override
        public String toString() {
            return "textView2 = " + textView2 + " textView3 = " + textView3;
        }
 
    }

//    @Override
//    public TatamiRecyclerView.TatamiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View layout
//                = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.row_recycler, parent, false);
//        CustomViewHolder holder = new CustomViewHolder(layout);
//        return holder;
//    }

    @Override
    public void onBindViewHolder(TatamiRecyclerView.TatamiViewHolder viewHolder, int position, Object value) {
        CustomViewHolder vh = ((CustomViewHolder) viewHolder);
        vh.textView2.setText(((Test)value).textView2);
        vh.textView3.setText(((Test)value).textView3);
    }

    @Click(R.id.button1)
    private void onClickButton1(View view) {
        Toast.makeText(getApplicationContext(), "onClickButton1-1", Toast.LENGTH_LONG).show();
    }

    private void onClickButton1(boolean view) {
        Toast.makeText(getApplicationContext(), "onClickButton1-2", Toast.LENGTH_LONG).show();
    }

    @Click(R.id.button2)
    private void onClickButton2(View view) {
        Toast.makeText(getApplicationContext(), "onClickButton2", Toast.LENGTH_LONG).show();
    }

    private void onLongClickButton2() {
        Toast.makeText(getApplicationContext(), "onLongClickButton2", Toast.LENGTH_LONG).show();
    }

}

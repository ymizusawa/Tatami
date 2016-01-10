package jp.yoshi_misa_kae.tatami_sample.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.yoshi_misa_kae.tatami.annotations.view.ActivityInfo;
import jp.yoshi_misa_kae.tatami.annotations.view.Click;
import jp.yoshi_misa_kae.tatami.view.widget.TatamiRecyclerView;
import jp.yoshi_misa_kae.tatami_sample.R;

@ActivityInfo(layoutId = R.layout.activity_test1)
public class Test1Activity extends Super1Activity {

//    @TextViewInfo(id = R.id.textView, textId = R.string.app_name)
    private TextView textView;

//    @EditTextInfo(id = R.id.editText, textId = R.string.app_name)
    private EditText editText;

    private int black;
    private int test;
    private String testStr;
    private TatamiRecyclerView recyclerView;
    private TatamiRecyclerView recyclerView2;

    @Override
    public void callOnCreate(Bundle savedInstanceState) {
        super.callOnCreate(savedInstanceState);

        Log.v("test", "button1 " + (button1 == null ? "is null." : "is not null."));
        Log.v("test", "button2 " + (button2 == null ? "is null." : "is not null."));
        Log.v("test", "textView " + (textView == null ? "is null." : "is not null."));
        Log.v("test", "editText " + (editText == null ? "is null." : "is not null."));
        Log.v("black", "black " + black);
        Log.v("test", "test " + test);
        Log.v("testStr", "testStr " + testStr);

        List<Test> list2 = new ArrayList<>();
        {
            Test test = new Test();
            test.textView2 = "111";
            test.textView3 = "222";
            list2.add(test);
        }
        List<Test> list = new ArrayList<>();
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

        recyclerView.setTatamiRecyclerViewBindViewListener(new TatamiRecyclerView.TatamiRecyclerViewBindViewListener() {
            @Override
            public void onBindViewHolder(TatamiRecyclerView.TatamiViewHolder viewHolder, int position, Object value) {
                CustomViewHolder vh = ((CustomViewHolder) viewHolder);
                vh.textView2.setText(((Test)value).textView2);
                vh.textView3.setText(((Test)value).textView3);
            }
        });
        recyclerView.setTatamiRecyclerViewItemClickListener(new TatamiRecyclerView.TatamiRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position, Object value) {
                Toast.makeText(getApplicationContext(), "position : " + position + ((Test) value).toString(), Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setList(list2);

        recyclerView2.setTatamiRecyclerViewBindViewListener(new TatamiRecyclerView.TatamiRecyclerViewBindViewListener() {
            @Override
            public void onBindViewHolder(TatamiRecyclerView.TatamiViewHolder viewHolder, int position, Object value) {
                CustomViewHolder vh = ((CustomViewHolder) viewHolder);
                vh.textView2.setText(((Test)value).textView2);
                vh.textView3.setText(((Test)value).textView3);
            }
        });
        recyclerView2.setTatamiRecyclerViewItemClickListener(new TatamiRecyclerView.TatamiRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position, Object value) {
                Toast.makeText(getApplicationContext(), "position2 : " + position + ((Test) value).toString(), Toast.LENGTH_LONG).show();
            }
        });
        recyclerView2.setList(list);
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

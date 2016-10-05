package jp.yoshi_misa_kae.tatami.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.yoshi_misa_kae.tatami.TatamiBindViewBindView;
import jp.yoshi_misa_kae.tatami.presenters.TatamiFragmentPresenter;
import jp.yoshi_misa_kae.tatami.views.activity.TatamiActivity;

/**
 * Created by ymizusawa on 2016/08/08.
 */
public abstract class TatamiFragment extends Fragment {

    private TatamiFragmentPresenter tatamiPresenter;
    private TatamiBindViewBindView tatamiBindView;

    public abstract TatamiFragmentPresenter getTatamiPresenter();

    protected void onTatamiCreate(@Nullable Bundle savedInstanceState) {}
    protected void onTatamiCreateView(View view, LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {}
    protected void onTatamiActivityCreated(@Nullable Bundle savedInstanceState) {}
    protected void onTatamiStart() {}
    protected void onTatamiResume() {}
    protected void onTatamiPause() {}
    protected void onTatamiStop() {}
    protected void onTatamiDestroyView() {}
    protected void onTatamiDestroy() {}

    public TatamiActivity getTatamiActivity() {
        return (TatamiActivity) getActivity();
    }

    @Deprecated
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tatamiBindView = new TatamiBindViewBindView();

        tatamiPresenter = getTatamiPresenter();
        tatamiPresenter.setTatamiActivity(getTatamiActivity());
        tatamiPresenter.setTatamiFragment(this);
        tatamiPresenter.onTatamiCreate(savedInstanceState);

        onTatamiCreate(savedInstanceState);
    }

    @Deprecated
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = tatamiBindView.bindView(this, inflater, container);

        tatamiPresenter.onTatamiCreateView(inflater, container, savedInstanceState);
        onTatamiCreateView(view, inflater, container, savedInstanceState);
        return view;
    }

    @Deprecated
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tatamiPresenter.onTatamiActivityCreated(savedInstanceState);
        onTatamiActivityCreated(savedInstanceState);
    }

    @Deprecated
    @Override
    public void onStart() {
        super.onStart();

        tatamiPresenter.onTatamiStart();
        onTatamiStart();
    }

    @Deprecated
    @Override
    public void onResume() {
        super.onResume();

        tatamiPresenter.onTatamiResume();
        onTatamiResume();
    }

    @Deprecated
    @Override
    public void onPause() {
        super.onPause();

        tatamiPresenter.onTatamiPause();
        onTatamiPause();
    }

    @Deprecated
    @Override
    public void onStop() {
        super.onStop();

        tatamiPresenter.onTatamiStop();
        onTatamiStop();
    }

    @Deprecated
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        tatamiPresenter.onTatamiDestroyView();
        onTatamiDestroyView();
    }

    @Deprecated
    @Override
    public void onDestroy() {
        super.onDestroy();

        tatamiPresenter.onTatamiDestroy();
        onTatamiDestroy();

        tatamiBindView = null;
        tatamiPresenter = null;
    }

    public static TatamiFragment newInstance(Class<? extends TatamiFragment> fragment) {
        TatamiFragment returnValue = null;
        try {
            returnValue = fragment.newInstance();
            Bundle bundle = new Bundle();
            returnValue.setArguments(bundle);
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        tatamiPresenter.onActivityResult(requestCode, resultCode, data);
    }

}

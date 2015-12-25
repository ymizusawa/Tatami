package jp.yoshi_misa_kae.tatami.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.yoshi_misa_kae.tatami.presenter.TatamiFragmentPresenter;
import jp.yoshi_misa_kae.tatami.view.mvp.TatamiFragmentMvpView;

/**
 * Created by ymizusawa on 2015/08/24.
 */
public class TatamiFragment extends Fragment implements TatamiFragmentMvpView {

    private TatamiFragmentPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new TatamiFragmentPresenter();
        presenter.attachView(this);
        return presenter.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.onActivityCreated(savedInstanceState);

        callOnActivityCreated();
    }

    @Override
    public void onResume() {
        super.onResume();

        callOnResume();
    }

    @Override
    public void onStop() {
        super.onStop();

        callOnStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        callOnDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();

        callOnPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void callOnActivityCreated() {
    }

    public void callOnResume() {
    }

    public void callOnPause() {
    }

    public void callOnStop() {
    }

    public void callOnDestroyView() {
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    public void popBackStack() {
        getFragmentManager().popBackStack();
    }

    public void setFragment(Class<?> clazz, @IdRes int id, Bundle bundle, String tag, boolean isAddToBackStack) {
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            try {
                fragment = (Fragment) clazz.newInstance();
                fragment.setArguments(bundle);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (java.lang.InstantiationException e) {
                throw new RuntimeException(e);
            }

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(id, fragment, tag);
            if (isAddToBackStack)
                ft.addToBackStack(null);
            ft.commit();
        }
    }

    protected Fragment setFragment(Fragment fragment, int id, String tag, boolean isAddToBackStack) {
        Fragment f = getFragmentManager().findFragmentByTag(tag);
        if (f == null) {
            f = fragment;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(id, f, tag);
            if (isAddToBackStack)
                ft.addToBackStack(null);
            ft.commit();
        }

        return f;
    }

}

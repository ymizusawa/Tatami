/**
 * A
 */
package jp.yoshi_misa_kae.tatami.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import jp.yoshi_misa_kae.tatami.R;
import jp.yoshi_misa_kae.tatami.presenter.TatamiActivityPresenter;
import jp.yoshi_misa_kae.tatami.view.mvp.TatamiActivityMvpView;

public class TatamiActivity extends AppCompatActivity implements TatamiActivityMvpView {

    protected Toolbar toolbar;

    private TatamiActivityPresenter presenter;
    private RecyclerView.Adapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new TatamiActivityPresenter();
        presenter.attachView(this);
        presenter.onCreate(savedInstanceState);
    }

    public void createToolbar(Toolbar tb) {
        toolbar = tb;

        presenter.createToolbar(tb);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();

        presenter.detachView();
        presenter = null;
    }

    @Override
    public void startActivity(Intent intent) {
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void callOnResume() {
    }

    public void callOnPause() {
    }

    public void callOnStop() {
    }

    public void callOnCreateOptionsMenu(Menu menu) {
    }

    protected int getColorResource(@ColorRes int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    protected int getInteger(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void callOnCreate(Bundle savedInstanceState) {
    }

    @Override
    public void callOnDestroy() {
    }


    public void setFragment(Class<?> clazz, @IdRes int id, Bundle bundle, String tag, boolean isAddToBackStack) {
        try {
            Fragment fragment = (Fragment) clazz.newInstance();
            fragment.setArguments(bundle);

            setFragment(fragment, id, tag, isAddToBackStack);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void setFragment(Fragment fragment, int id, String tag, boolean isAddToBackStack) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
        if (f == null) {
            f = fragment;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(id, f);
            if (isAddToBackStack)
                ft.addToBackStack(tag);
            ft.commit();
        }
    }

    protected void setFragment(android.app.Fragment fragment, int id, String tag, boolean isAddToBackStack) {
        android.app.Fragment f = getFragmentManager().findFragmentByTag(tag);
        if (f == null) {
            f = fragment;
            android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(id, f);
            if (isAddToBackStack)
                ft.addToBackStack(tag);
            ft.commit();
        }
    }

    public void addFragment(Class<?> clazz, @IdRes int id, Bundle bundle, String tag, boolean isAddToBackStack) {
        try {
            Fragment fragment = (Fragment) clazz.newInstance();
            fragment.setArguments(bundle);

            addFragment(fragment, id, tag, isAddToBackStack);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void addFragment(Fragment fragment, int id, String tag, boolean isAddToBackStack) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
        if (f == null) {
            f = fragment;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(id, f);
            if (isAddToBackStack)
                ft.addToBackStack(tag);
            ft.commit();
        }
    }


    public void setFragmentAnimation(Class<?> clazz, @IdRes int id, Bundle bundle, String tag, boolean isAddToBackStack) {
        try {
            Fragment fragment = (Fragment) clazz.newInstance();
            fragment.setArguments(bundle);

            setFragmentAnimation(fragment, id, tag, isAddToBackStack);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void setFragmentAnimation(Fragment fragment, int id, String tag, boolean isAddToBackStack) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
        if (f == null) {
            f = fragment;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);
            ft.replace(id, f);
            if (isAddToBackStack)
                ft.addToBackStack(tag);
            ft.commit();
        }
    }

    public void addFragmentAnimation(Class<?> clazz, @IdRes int id, Bundle bundle, String tag, boolean isAddToBackStack) {
        try {
            Fragment fragment = (Fragment) clazz.newInstance();
            fragment.setArguments(bundle);

            addFragmentAnimation(fragment, id, tag, isAddToBackStack);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void addFragmentAnimation(Fragment fragment, int id, String tag, boolean isAddToBackStack) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
        if (f == null) {
            f = fragment;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit,
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit);
            ft.add(id, f);
            if (isAddToBackStack)
                ft.addToBackStack(tag);
            ft.commit();
        }
    }

}

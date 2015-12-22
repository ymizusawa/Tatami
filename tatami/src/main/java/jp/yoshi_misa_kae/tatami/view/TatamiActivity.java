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

        callOnCreate();
    }

    public void createToolbar(Toolbar tb) {
        toolbar = tb;

        presenter.createToolbar(tb);
    }

    @Override
    protected void onResume() {
        super.onResume();

        callOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        callOnPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        callOnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.detachView();
        presenter = null;

        callOnDestroy();
    }

    @Deprecated
    public void setFragment(Class<?> clazz, @IdRes int id, Bundle bundle, String tag, boolean isAddToBackStack) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            try {
                fragment = (Fragment) clazz.newInstance();
                fragment.setArguments(bundle);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(id, fragment, tag);
            if (isAddToBackStack)
                ft.addToBackStack(null);
            ft.commit();
        }
    }

    protected Fragment setFragment(Fragment fragment, int id, String tag) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
        if (f == null) {
            f = fragment;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(id, f, tag);
            ft.commit();
        }

        return f;
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

    public void callOnCreate() {
    }

    public void callOnResume() {
    }

    public void callOnPause() {
    }

    public void callOnStop() {
    }

    public void callOnDestroy() {
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

}

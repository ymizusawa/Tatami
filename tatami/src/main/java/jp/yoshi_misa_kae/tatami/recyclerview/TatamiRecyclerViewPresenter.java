package jp.yoshi_misa_kae.tatami.recyclerview;

import jp.yoshi_misa_kae.tatami.presenters.TatamiActivityPresenter;

public abstract class TatamiRecyclerViewPresenter extends TatamiActivityPresenter {

    protected TatamiRecyclerViewListener listener;

    public abstract void loadList();

    public void setListener(TatamiRecyclerViewListener listener) {
        this.listener = listener;
    }

}

package jp.yoshi_misa_kae.tatami.view.widget;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.view.inputmethod.EditorInfo;

public class TatamiSingleLineEditText extends EditText
{
    public TatamiSingleLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        setSingleLine();
        setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

}

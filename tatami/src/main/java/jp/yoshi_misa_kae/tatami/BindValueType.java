package jp.yoshi_misa_kae.tatami;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/16.
 */
public enum BindValueType {

    TYPE_ANIM("anim", 1),
    TYPE_ATTR("attr", 2),
    TYPE_BOOL("bool", 3),
    TYPE_COLOR("color", 4),
    TYPE_DIMEN("dimen", 5),
    TYPE_DRAWABLE("drawable", 6),
    TYPE_ID("id", 7),
    TYPE_INTEGER("integer", 8),
    TYPE_LAYOUT("layout", 9),
    TYPE_STRING("string", 10),
    TYPE_STYLE("style", 11),
    TYPE_STYLEABLE("styleable", 12),
    ;

    private final String type;
    private final int id;

    private BindValueType(final String type, final int id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public int getId() {
        return this.id;
    }

    public static List<BindValueType> list() {
        List<BindValueType> returnValue = new ArrayList<>();
        returnValue.add(TYPE_ANIM);
        returnValue.add(TYPE_ATTR);
        returnValue.add(TYPE_BOOL);
        returnValue.add(TYPE_COLOR);
        returnValue.add(TYPE_DIMEN);
        returnValue.add(TYPE_DRAWABLE);
        returnValue.add(TYPE_ID);
        returnValue.add(TYPE_INTEGER);
        returnValue.add(TYPE_LAYOUT);
        returnValue.add(TYPE_STRING);
        returnValue.add(TYPE_STYLE);
        returnValue.add(TYPE_STYLEABLE);

        return returnValue;
    }
}

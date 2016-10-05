package jp.yoshi_misa_kae.tatami;

/**
 * Created by ymizusawa on 2016/09/16.
 */
class AnnotationData {
    public int type;
    public String key;

    AnnotationData(int type, String key) {
        this.type = type;
        this.key = key;
    }

    AnnotationData(int type) {
        this.type = type;
    }
}

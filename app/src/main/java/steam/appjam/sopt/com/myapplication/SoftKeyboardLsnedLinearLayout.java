package steam.appjam.sopt.com.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Isoft on 2016-07-07.
 */
public class SoftKeyboardLsnedLinearLayout extends LinearLayout {
    private boolean isKeyboardShown = false;
    private List<SoftKeyboardLsner> lsners = new ArrayList<SoftKeyboardLsner>();
    private float layoutMaxH = 0f; // max measured height is considered layout normal size
    private static final float DETECT_ON_SIZE_PERCENT = 0.8f;

    public SoftKeyboardLsnedLinearLayout(Context context) {
        super(context);
    }

    public SoftKeyboardLsnedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("NewApi")
    public SoftKeyboardLsnedLinearLayout(Context context, AttributeSet attrs,
                                         int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int newH = MeasureSpec.getSize(heightMeasureSpec);
        if (newH > layoutMaxH) {
            layoutMaxH = newH;
        }
        if (layoutMaxH != 0f) {
            final float sizePercent = newH / layoutMaxH;
            if (!isKeyboardShown && sizePercent <= DETECT_ON_SIZE_PERCENT) {
                isKeyboardShown = true;
                for (final SoftKeyboardLsner lsner : lsners) {
                    lsner.onSoftKeyboardShow();
                }
            } else if (isKeyboardShown && sizePercent > DETECT_ON_SIZE_PERCENT) {
                isKeyboardShown = false;
                for (final SoftKeyboardLsner lsner : lsners) {
                    lsner.onSoftKeyboardHide();
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void addSoftKeyboardLsner(SoftKeyboardLsner lsner) {
        lsners.add(lsner);
    }

    public void removeSoftKeyboardLsner(SoftKeyboardLsner lsner) {
        lsners.remove(lsner);
    }

    // Callback
    public interface SoftKeyboardLsner {
        public void onSoftKeyboardShow();

        public void onSoftKeyboardHide();
    }
}
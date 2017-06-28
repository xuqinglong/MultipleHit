package allen.code.multiplehit.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 属性动画实现：直播打赏连击效果
 * Created by allen on 2017/6/28.<br>
 * mail：jalon@gogal.cn
 */

public class MultipleHitView extends AppCompatTextView implements View.OnClickListener {
    private Paint mPaint;
    private int number = 0;
    private RectF rectF;
    private int strokeWidth = 6;
    private static  int  sweepAngle = 0;
    private ValueAnimator animtor;
    public MultipleHitView(Context context) {
        this(context,null);
    }

    public MultipleHitView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MultipleHitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setOnClickListener(this);
    }
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rectF = new RectF(strokeWidth,strokeWidth,getMeasuredWidth()-strokeWidth,getMeasuredHeight()-strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(rectF,-90,-sweepAngle,false,mPaint);
        super.onDraw(canvas);
    }
    @Override
    public void onClick(View view) {
        if (animtor!=null){
            animtor.removeAllUpdateListeners();
        }
        sweepAngle = 360;
        number++;
        setText(number+"");
        animtor = new ValueAnimator().ofInt(sweepAngle,0);
        animtor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                sweepAngle = (int) valueAnimator.getAnimatedValue();
                if (sweepAngle ==0){
                    number = 0;
                    setText("");
                }
                invalidate();
            }
        });
        animtor.setDuration(3000);
        animtor.start();
    }
}

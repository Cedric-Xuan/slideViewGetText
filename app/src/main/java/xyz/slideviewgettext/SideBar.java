package xyz.slideviewgettext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * Created by xyz on 2017/2/5.
 */

public class SideBar extends View {
    private List<String> letterList;
    private Paint paint;
    private int textSize=35;

    public SideBar(Context context,List<String> list){
        this(context,(AttributeSet) null);
        this.letterList=list;
    }

    public  SideBar(Context context, AttributeSet attributeSet){
        this(context,attributeSet,0);
    }

    public  SideBar(Context context,AttributeSet attributeSet,int defStyle){
        super(context,attributeSet,defStyle);
        init();
    }

    private void init(){
        setBackgroundColor(0x000000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height=getHeight();//获取view的高度
        int width=getWidth();//获取view的宽度
        int singleHeight=height/letterList.size();//获取每一个字母所占的高度
        int textHight= getFontAboveBaseLineHeight(textSize);


        paint=new Paint();


        for(int i=0;i<letterList.size();i++){
            paint.setColor(0xff606060);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(textSize);

            //文字x轴坐标
            float xPos=width/2-paint.measureText(letterList.get(i))/2;

            //文字y轴坐标
//            float yPos=(singleHeight*(i)) + (2*singleHeight/3);
            float yPos=(singleHeight*i)+((singleHeight+textHight)/2);

            canvas.drawText(letterList.get(i),xPos,yPos,paint);
            paint.reset();//重置画笔
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action=event.getAction();
        final float x=event.getX();
        final float y=event.getY();

        // 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        final int index=(int)((y/getHeight())*letterList.size());

        if(x<0||x>getWidth()||y<0||y>getHeight()) {
            action = MotionEvent.ACTION_UP;
        }

            switch (action) {

                case MotionEvent.ACTION_UP:
                    setBackgroundColor(0x000000);
                    if (onTouchBarListener != null) {
                        onTouchBarListener.onTouch(null, -1, false);

                    }

                    invalidate();
                    break;


                default:
                    setBackgroundColor(0xffffffff);
                    if (onTouchBarListener != null) {
                        onTouchBarListener.onTouch(letterList.get(index), index, true);
                    }


                        invalidate();
                        break;



            }


        return true;//要实现触摸式的动态效果，必须返回true，使得事件能够被拦截


    }

    //获得字体baseLine以上的高度
    //若需要了解关于单行文本高度分析详情请看 http://blog.csdn.net/uyy203/article/details/54926753
    public int getFontAboveBaseLineHeight(float fontSize)
    {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int)(Math.ceil(- fm.ascent));
    }


    //外部设置列表数据方法，重绘view
    public void setLetterList(List<String> list){
        this.letterList=list;
        invalidate();
    }



    //外部调用接口，以便让外部获得当前触摸到的字母
    private OnTouchBarListener onTouchBarListener;
    public interface OnTouchBarListener{
        void onTouch(String letter,int pos,boolean state);
    }
    public void setOnTouchBarLisstener(OnTouchBarListener l){
        this.onTouchBarListener=l;
    }


}

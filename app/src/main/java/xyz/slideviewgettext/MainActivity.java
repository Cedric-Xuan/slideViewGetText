package xyz.slideviewgettext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    public List<String> letterList;

    public static String[] INDEX_STRING = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z",
            "#"
    };

    public static String[] Afterstring = {
            "X", "X", "X", "X", "X", "X", "X", "X", "X"
    };


    private RelativeLayout rlayout;
    private static int WC= ViewGroup.LayoutParams.WRAP_CONTENT;
    private static int MP=ViewGroup.LayoutParams.MATCH_PARENT;
    private SideBar sideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_main);

        letterList=new ArrayList<String>();
        letterList= Arrays.asList(INDEX_STRING);

        //整体布局
        rlayout=new RelativeLayout(getBaseContext());
        RelativeLayout.LayoutParams rp=new RelativeLayout.LayoutParams(MP,MP);
        setContentView(rlayout,rp);

        //屏幕中间显示的文本，会将当前触摸到的字母显示出来
        final TextView text=new TextView(getBaseContext());
        rp=new RelativeLayout.LayoutParams(WC,WC);
        rp.addRule(RelativeLayout.CENTER_IN_PARENT);
        text.setTextSize(100);
        text.setTextColor(0xff606060);
        rlayout.addView(text,rp);


        //包裹侧边导航栏view的布局
        final RelativeLayout sideLayout=new RelativeLayout(getBaseContext());
        rp=new RelativeLayout.LayoutParams(80,MP);
        rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rp.addRule(RelativeLayout.CENTER_VERTICAL);
        rlayout.addView(sideLayout,rp);


        //包裹侧边导航栏view
        sideBar=new SideBar(getBaseContext(),letterList);
        rp=new RelativeLayout.LayoutParams(WC,WC);
        sideLayout.addView(sideBar,rp);


        sideBar.setOnTouchBarLisstener(new SideBar.OnTouchBarListener() {
            @Override
            public void onTouch(String letter, int pos,boolean state) {
                if(state)
                    text.setText(letter);
                else
                    text.setText("");
            }
        });


        //改变list内容 重绘view
        Button button =new Button(getBaseContext());
        rp=new RelativeLayout.LayoutParams(WC,WC);
        button.setText("点我");
        rlayout.addView(button,rp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                letterList= Arrays.asList(Afterstring);
                sideBar.setLetterList(letterList);

            }
        });




    }
}

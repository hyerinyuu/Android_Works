package com.biz.myapp.utils;

import android.view.View;
import android.widget.TextView;

import com.biz.myapp.R;
import com.google.android.material.snackbar.Snackbar;


/*
    View Class
    안드로이드에서 눈에 보이는 모든 것(Layout, Button TextView, TextEdit)은
    모두 View 클래스를 상속 받아서 만들어진 component 들이다.
    어던 이벤트나 액션등을 지정할 때
    기존의 클래스나 인터페이스를 상속 또는 implements 해야 하는데
    그때 자기 자신의 클래스를 사용하지 않고 View Class의 요소들을 상속 받아서
    클래스를 작성한즌 것이 원칙이다.
 */
public class MyViewClass implements View.OnClickListener {

    /*
        현재 onClick method는 btn1 Button을 클릭했을 때 호출되는 method인데,
        btn1을 클릭하면 클릭된 Button의 모든 요소가
        View인 v 객체변수에 담겨 onClick() method로 전달된다.
     */
    @Override
    public void onClick(View v) {

        String msg = "반갑습니다";
        /*
            이벤트가 발생하면 onClick() method가 실행(호출)될 것이고,
            누가 호출했는가를 알고 싶을 대
            v.getId() method를 사용하면 호출한 view(comp)의 id값을 얻어 올 수 있다.
         */
        if(v.getId() == R.id.btn1){
            msg += "\n나는 btn1입니다.";
        }else if(v.getId() == R.id.txt1){

            /*
                이벤트가 발생한 view(component)로부터 어떤 값을 얻어오고자 하면
                해당 view로 형변환(type cast)를 한번 수행해서 객체를 생성하고
                만든 객체에서 각 view의 고유한 method를 호출하면 된다.
             */
            TextView t = (TextView)v;
            msg += "\n" + t.getText();
        }else if(v.getId() == R.id.txt2){
            TextView t = (TextView)v;
            msg += "\n" + t.getText();
        }
        Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show();

    }


}

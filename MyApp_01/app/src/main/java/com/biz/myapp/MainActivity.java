package com.biz.myapp;

import android.os.Bundle;

import com.biz.myapp.utils.MyViewClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

/*
    안드로이드 이벤트핸들러 2번째로 많이 사용하는 방법
        [Activity 자체를 Event Handler로 선언하기]
    View.Onclick 클래스를 implements 해주고
    onClick() method를 구현해 주는 방식

    사용할 때는 view.setOnClick(this) 키워드 방식으로 호출
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt11;
    TextView txt22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txt1 = findViewById(R.id.txt1);
        TextView txt2 = findViewById(R.id.txt2);
        Button btn1 = findViewById(R.id.btn1);

        /*txt11 = findViewById(R.id.txt1);
        txt22 = findViewById(R.id.txt2);


        txt1.setOnClickListener( this );
        txt2.setOnClickListener( this );
        btn1.setOnClickListener( this );*/

        FloatingActionButton fab = findViewById(R.id.fab);

        /*
                            [이벤트 핸들링 첫번째 방식]
            onClickListner라는 인터페이스를 사용하여 익명/무명 클래스를 생성해
            자체적으로 이벤트를 핸들링하기
         */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        자체에서 onClick() method를 구현하여 이벤트를 핸들링 하는 이유
        이벤트를 처리해야 할 view(component)들이 있는데
        대체로 이벤트 처리 코드가 유사한 경우,
        한번만 이벤트 처리 코드를 작성하고 view들이 공유해서 사용하기 위함(코드 재활용)
        익명클래스를 이용해서 하는 방법의 경우에는 이벤트마다 새로운 클래스를 생성해줘야해서
        코드가 매우 길어지고 지저분해 질 수 있다.

        v.getId() method를 사용해서 누가 호출했는가를 구분해서 코드를 진행하면 된다.
     */
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.txt2){
            txt11.setText(txt22.getText());
        } else{
            txt11.setText("오늘은 수요일 나가사끼짬뽕");
        }
    }
}

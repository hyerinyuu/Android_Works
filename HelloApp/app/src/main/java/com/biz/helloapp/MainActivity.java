package com.biz.helloapp;

import android.content.Intent;
import android.os.Bundle;

import com.biz.helloapp.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        setContentView(R.layout.my_layout);


        // layout xml에서 선언한 객체 컴포넌트를 사용할 테니 객체로 만들어 달라
        txtInput = findViewById(R.id.txtInput1);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);

        // 익명 클래스 방식으로 클릭 이벤트를 설정
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt = txtInput.getText().toString();

                // toast는 마치 토스트기에서 토스트가 다 구워져 떠오르는 것처럼 팝업이 떠오름
                // 화면의 가장 위에 떠있음(float)
                Toast.makeText(MainActivity.this, txt,
                        Toast.LENGTH_LONG).show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // snackbar은 button의 child처럼 작동함
                Snackbar.make(v, "안녕하세요", Snackbar.LENGTH_LONG).show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                    ####
                    새로운 activity를 화면에 띄울 때
                    Intent 클래스로 객체를 생성하고,
                    startActivity() method에게 객체를 주입해주면 끝
                 */

                // Intent : 우리가 만든 activity중 현재 화면에 보이는 activity
                // 첫번째 매개변수는 context,
                // 두번째 매개변수는 새로 오픈할 activity를 뜻함
                // ==> 현재 열려있는 MainActivity 화면을 밀어내고
                // 새로 생성된 새 Intent인 LoginActivity 창을 위로 올려줌
                // (뒤로가기 하면 이전 화면인 MainActivity 다시 나옴)

                // 현재화면(MainActivity.this) 에서 새로운 Activity가 보이도록 인텐트를 생성하라는 method
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);

                // 생성된 새로운 Intent가 보이도록 하라.
                startActivity(loginIntent);

            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pIntent = new Intent(Intent.ACTION_CALL_BUTTON);
                startActivity(pIntent);
            }
        });


       // Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
}

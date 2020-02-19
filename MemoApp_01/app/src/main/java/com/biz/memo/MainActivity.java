package com.biz.memo;

import android.os.Bundle;

import com.biz.memo.adapter.MemoViewAdapter;
import com.biz.memo.adapter.MemoViewModel;
import com.biz.memo.domain.MemoVO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    List<MemoVO> memoList = null;
    TextInputEditText m_input_memo = null;
    RecyclerView memo_list_view = null;
    MemoViewAdapter view_adapter = null;

    // DB연동을 위한 변수 선언
    MemoViewModel memoViewModel;

    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        memoList = new ArrayList<MemoVO>();

        Button btn_save = findViewById(R.id.memo_save);
        btn_save.setOnClickListener(this);


        m_input_memo = findViewById(R.id.m_input_text);

        memo_list_view = findViewById(R.id.memo_list_view);

        // DB 연동을 위한 준비

        // LifeCycle 2.2.0-beta01의 ViewModelProvider사용
        memoViewModel = new ViewModelProvider(this).get(MemoViewModel.class);

        /*
            DB의 데이터가 변경되어 이전에 selectAll() 가져온 리스트에 변동이 발생하면
            observ() method가 알람을 주고 onChanged 이벤트가 발생을 한다.

            onChanged() method에서 데이터를 화면에 보여주는 코드를 작성한다.
         */
        memoViewModel.selectAll().observe(this, new Observer<List<MemoVO>>() {
            @Override
            public void onChanged(List<MemoVO> memoVOS) {
                view_adapter.setMemoList(memoList);

            }
        });
        /*
        => 2.2.0에서는 위와 같은 코드가 가능하나

        2.2.2로 버전업이 되면서 아래와 같이 코드를 작성해야 오류가 나지 않음
        (this가 onClick method와 충돌해서 아래와 같은 코드로 수정함)
         */
        // memoList = memoViewModel.selectAll();
        view_adapter = new MemoViewAdapter(MainActivity.this, memoList);
        memo_list_view.setAdapter(view_adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        memo_list_view.setLayoutManager(layoutManager);

        // underline 만드는 method
        DividerItemDecoration itemDecoration = new DividerItemDecoration(memo_list_view.getContext(),
                LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.decoration_line,getApplication().getTheme()));

        memo_list_view.addItemDecoration(itemDecoration);

        FloatingActionButton fab = findViewById(R.id.fab);
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

    @Override
    public void onClick(View v) {

        String m_memo_text = m_input_memo.getText().toString();
        if(m_memo_text.isEmpty()){

            // 키보드가 올라와 있는 상황에서는 snackbar가 안보일 수도 있으니까
            // toast를 써주는게 더 나음.
            Toast.makeText(MainActivity.this, "메모를 입력하세요", Toast.LENGTH_SHORT).show();
            m_input_memo.setFocusable(true);
            return ;
        }

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat st = new SimpleDateFormat("HH:mm:ss");

        Date date = new Date(System.currentTimeMillis());

        MemoVO memoVO = MemoVO.builder()
                .m_date(sd.format(date))
                .m_time(st.format(date))
                .m_text(m_memo_text).build();

        // memoViewModel의 insert method를 호출하여 DB에 memoVO 데이터를 저장
        memoViewModel.insert(memoVO);


        // memoList.add(memoVO);

        // RecyclerView의 adapoer에게 데이터가 변경되었으니 list를 다시 그려달라고
        // 통보하는 것.(이걸 안해주면 데이터를 추가해도 list가 보이지 않음)
        // view_adapter.notifyDataSetChanged();


        m_input_memo.setText("");

    }
}

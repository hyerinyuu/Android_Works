package com.biz.memo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biz.memo.R;
import com.biz.memo.domain.MemoVO;

import java.util.List;

public class MemoViewAdapter extends RecyclerView.Adapter{

    private Context context = null;
    private List<MemoVO> memoList = null;

    // MainActivity에서 MemoViewAdapter를 만들 때 Context와 MemoList를 주입할 생성자
    public MemoViewAdapter(Context context, List<MemoVO> memoList) {
        this.context = context;
        this.memoList = memoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // context == mainActivity
        // memoItem을 view로 가져와서 쓸 준비
        View view = LayoutInflater.from(context)
                /*
                   memo_item.xml파일을 가져와서 view 객체로 생성(확장)하기
                 */
                .inflate(R.layout.memo_item, parent, false);

        MemoHolder holder = new MemoHolder(view);
        return holder;
    }

    /*
    memo_item.xml에 설정한 여러가지 view들을 사용할 수 있도록 초기화 하는 과정
     */
    class MemoHolder extends RecyclerView.ViewHolder{

        public TextView m_time;
        public TextView m_date;
        public TextView m_text;

        public MemoHolder(@NonNull View itemView) {
            super(itemView);
            m_time = itemView.findViewById(R.id.m_time);
            m_date = itemView.findViewById(R.id.m_date);
            m_text = itemView.findViewById(R.id.m_text);

        }
    }

    // memoList의 개수만큼 생성되어서 화면에 표시될 때 반복문으로 호출되는 method
    // 반복문이 호출되면서 몇번째 데이터인가를 position변수에 주입해준다.
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        /*
            [다형성]
            RecyclerView.ViewHolder를 MemoHolder로 형변환 하여
            MemoHolder에 직접 접근할 수 있도록 한다.
         */

        /*
        memoList의 각 아이템 요소를 한개씩 읽어서
        TextView의 setText() method를 이용해서 문자열을 채워 넣어준다.
         */
        MemoHolder memoHolder = (MemoHolder)holder;
        memoHolder.m_date.setText(memoList.get(position).getM_date());
        memoHolder.m_time.setText(memoList.get(position).getM_time());
        memoHolder.m_text.setText(memoList.get(position).getM_text());

    }

    @Override
    public int getItemCount() {
        // memoList가 null이 아니면 size를 return하고 0이면 리스트가 없으니 0을 return
        return memoList != null ? memoList.size() : 0;
    }

}

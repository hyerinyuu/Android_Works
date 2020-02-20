package com.biz.memo.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.biz.memo.db.MemoDataBase;
import com.biz.memo.domain.MemoVO;

import java.util.List;

/*
DB에 접근할 때 사용할 service Class
 */
public class MemoRepository {

    private MemoDao mDao;

    public MemoRepository(Application application) {

        MemoDataBase db = MemoDataBase.getInstance(application);
        mDao = db.getMemoDao();
    }

    public LiveData<List<MemoVO>> selectAll(){
      return mDao.selectAll();
    }

    // thread pool을 이용해서 thread를 실행
    // #### thread로 insert 실행
    public void insert(final MemoVO memoVO){

        // 기본 자바 코드
        /*
        MemoDataBase.dbWriterThread.execute(new Runnable() {
            @Override
            public void run() {
                mDao.save(memoVO);
            }
        });
         */
        //   [Rambda식]
        // () : 위의 run method
        MemoDataBase.dbWriterThread.execute( () -> mDao.save(memoVO) );

    }
    public void delete(MemoVO memoVO){
        MemoDataBase.dbWriterThread.execute( () -> mDao.delete(memoVO) );
    }

}

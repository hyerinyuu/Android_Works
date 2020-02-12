package com.biz.memo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.biz.memo.domain.MemoVO;
import com.biz.memo.repository.MemoDao;

@Database(entities = {MemoVO.class}, version=1)
abstract public class MemoDataBase extends RoomDatabase {

    /*
        (public abstract라고 써도 상관 없으나 구글 스타일이 그럼)
        데이터베이스 INSTANCE가 생성이 되면서
        Memodao interface를 가져다가 사용할 수 있는 class를 생성한다.
     */
    abstract public MemoDao getMemoDao();

    /*
    Database를 생성하는 클래스를 싱글톤으로 선언하기 위해서
    외부에서 접근하는 변수를 선언하는 부분
     */
    private static volatile MemoDataBase INSTANCE;
    public static MemoDataBase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (MemoDataBase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        // memoDatabase를 이용해서 memo.dbf라는 물리적 db를 만들어라
                        MemoDataBase.class,"memo.dbf").build();
            }

        }
        return INSTANCE;
    }

}

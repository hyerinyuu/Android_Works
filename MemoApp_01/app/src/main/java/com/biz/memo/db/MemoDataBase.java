package com.biz.memo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.biz.memo.domain.MemoVO;
import com.biz.memo.repository.MemoDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MemoVO.class}, version=1, exportSchema = false)
abstract public class MemoDataBase extends RoomDatabase {

    /*
        (public abstract라고 써도 상관 없으나 구글 스타일이 그럼)
        데이터베이스 INSTANCE가 생성이 되면서
        Memodao interface를 가져다가 사용할 수 있는 class를 생성한다.
     */
    abstract public MemoDao getMemoDao();

    // 고전적인 Thread 클래스를 도와서 Thread를 관리해주는 Helper Class
    // nThreads 값:3 ==> thread를 이용해서 일을 처리를 할거니까 미리 thread를 3개 준비해놔라
    // db Connection을 미리 여러개 만들어 놓고 요청이 들어오면 이용함.

    // application을 실행하면 application에 대한 정보가 메모리에 저장되는데(용량, connection등 운영체제가 관리해야할 모든 정보)
    // 이러한 메모리를 context라고 한다.==> 모든 정보를 하나의 객체로 만들어서 저장해 놓은 것.
    // thread가 여러개면 context도 여러개 생성됨.
    // main context가 작동되고 있는 상태에서 thread가 또 생성이 되면
    // context change ==> context가 많아도 운영체제입장에서 정보를 잘 교환해 일을 처리하는 것.
    // thread를 만드는 과정에서 속도가 느리면 사용자는 버벅거리는 화면을 보게 되므로
    // 미리 생성된 context를 만들어 놓고 값만 채워서 속도를 빠르게 하는 것이 ThreadPool임.

    // ##############
    // 앞으로 실행할(생성할) Thread를 위한 context정보를 담을 객체를
    // 미리 비어있는 상태로 생성을 해두고 필요할 때 공급하는 용도
    public static final ExecutorService dbWriterThread = Executors.newFixedThreadPool(3);

    /*
    Database를 생성하는 클래스를 싱글톤으로 선언하기 위해서
    외부에서 접근하는 변수를 선언하는 부분
     */
    private static volatile MemoDataBase INSTANCE;

    public static MemoDataBase getInstance(final Context context){
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

package com.biz.memo.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.biz.memo.domain.MemoVO;

import java.util.List;

@Dao
public interface MemoDao {

    /*
        LiveData
        Android의 room DB와 연동하여
        MVVM 패턴을 사용할 수 있도록 도와주는 Helper Class
        LifeCycle에 포함된 클래스로서
        DB의 내용이 변경되면 변경된 부분만 가져와서
        view에 표시할 수 있도록 알람을 내부적으로 발생시키는 클래스
     */
    // projection : db에서 select문을 사용할 때 * 을 사용하지 않고
    // 칼럼들을 모두 명시하여 나열해 주는 것.
    // ********** Fts4을 사용하려면 select문에 반드시 rowid 칼럼을 표시해주어야한다.
    @Query("SELECT rowid, * FROM tbl_memo")
    public LiveData<List<MemoVO>> selectAll();

    @Query("SELECT rowid, * FROM tbl_memo WHERE rowid = :rowid")
    public MemoVO findByRowId(int rowid);

    @Query("SELECT rowid, * FROM tbl_memo WHERE m_text LIKE :m_text")
    public LiveData<List<MemoVO>> findByText(String m_text);

    /*
        ORM구조에서는 새로운 데이터는 INSERT를 수행하고 기존 데이터는
        REPLACE를 수행하는 method를 공통으로 사용한다.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void save(MemoVO memoVO);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(MemoVO memoVO);


    @Update
    void update(MemoVO memoVO);

    /*
        표준 room @Delete method는
        VO를 매개변수로 받아서 delete를 수행
     */
    @Delete
    public void delete(MemoVO memoVO);

    @Query("DELETE FROM tbl_memo WHERE rowid = :rowid")
    public void delete(int rowid);


}

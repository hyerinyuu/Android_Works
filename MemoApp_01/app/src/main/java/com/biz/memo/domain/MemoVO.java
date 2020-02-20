package com.biz.memo.domain;

import androidx.annotation.ColorInt;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

/*
    FTS ver4
    Full Text Search 수행하는 방법에는 3과 4가 있는데
    최신 AS에서는 FTS4를 사용하도록 권장하고 있고
    매우 빠른 속도로 전체 텍스트 검색을 할 수 있다.

    FTS4는 Room 2.1.0 이상에서 제공되는 기능이다.
 */
@Fts4
@Entity(tableName = "tbl_memo")
public class MemoVO {

    // autoGenerate=true : pk로 지정된 숫자형 칼럼에 auto increment를 부여하는 속성
    /*
        PK 지정된 숫자형 칼러멩 auto increment를 부여하는 속성
        SQLite에서 FTS라는 패턴을 지원하는 DB 형식
        FTS : Full Text Search
        FTS를 사용하려면 id 칼럼이 반드시 int형이고
        db 칼럼 이름은 rowid로 설정을 해야한다.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="rowid")
    private int id;

    @ColumnInfo(name="m_date")
    private String m_date;
    @ColumnInfo(name="m_time")
    private String m_time;
    @ColumnInfo
    private String m_text;

}

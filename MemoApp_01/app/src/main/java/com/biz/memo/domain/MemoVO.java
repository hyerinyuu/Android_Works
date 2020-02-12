package com.biz.memo.domain;

import androidx.annotation.ColorInt;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
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

@Entity(tableName = "tbl_memo")
public class MemoVO {

    // autoGenerate=true : pk로 지정된 숫자형 칼럼에 auto increment를 부여하는 속성
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="rowid")
    private long id;

    @ColumnInfo(name="m_date")
    private String m_date;
    @ColumnInfo(name="m_time")
    private String m_time;
    @ColumnInfo
    private String m_text;

}

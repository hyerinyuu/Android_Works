package com.biz.naver.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverMovie {

    /*
        @Expose
        만약에 해당 칼럼에 데이터가 null이면 자동으로 안받아서
        NullPoint 오류를 최소화 시켜준다.
     */
    @SerializedName("lastBuildDate")
    @Expose
    private String lastBuildDate; // 검색 결과를 생성한 시간이다.

    @SerializedName("total")
    @Expose
    private String total;  // 검색의 시작 위치를 지정할 수 있다. 최대 1000까지 가능하다.

    @SerializedName("start")
    @Expose
    private String start;

    @SerializedName("display")
    @Expose
    private String display;  // 검색 결과 출력 건수를 지정한다. 최대 100까지 가능하다.

    // 실제로 데이터를 담을 list형 변수
    @SerializedName("items")
    List<NaverMovieItem> items = null;
}

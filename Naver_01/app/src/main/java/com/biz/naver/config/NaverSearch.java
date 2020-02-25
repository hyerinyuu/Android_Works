package com.biz.naver.config;

import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.biz.naver.adaptor.MovieAdapter;
import com.biz.naver.domain.NaverMovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/*
    AsyncTask<Integer,Integer,Void>
    네이버 API를 비동기 방식으로 호출하여 데이터를 가져오는 class
    안드로이드는 pc에 비해 성능이 떨어지기 때문에 마냥 데이터를 가져오기까지
    기다릴 수 없어서 비동기 방식을 활용함
    (비동기 : request를 보내면 ajax가 우선 ok신호를 우리에게 보내고 서버로부터 값이 날아오면
    그때 화면에 무언가 띄울 수 있도록 우리에게 신호를 다시 보내주는 방식처럼
    background에서 항상 무언가가 실행되고 있는 상태)

 */
public class NaverSearch extends AsyncTask<Integer,Integer,Void> {

    private final String naver_movie_url = "https://openapi.naver.com/v1/search/movie.json";
    private String strSearch;
    private List<NaverMovieItem> mList = null;
    private RecyclerView recyclerView;

    public NaverSearch() {

    }
    // 검색어와 RecyclerView를 전달받아서 검색을 수행하고
    // RecyclerView에 보이기
    public NaverSearch(String strSearch, RecyclerView recyclerView) {
        this.strSearch = strSearch;
        this.recyclerView = recyclerView;
    }

    /*
                    Integer... integers
                    : 매개변수의 개수가 정해지지 않은 호출방식
                    매개변수가 몇개인지 관계 없이 어떤 부분이라도 이 메서드를 호출할 수 있다.
                    doInBackground(3,4,5,6,7,8,9,0)와 같은 method는
                    == Integer{} integers = new Integer[8]와 같이 선언된 것과 같다.(자바 1.8이상)
                 */
    @Override
    protected Void doInBackground(Integer... integers) {
        this.naver_search();
        return null;
    }

    /*
        doInBackground() method가 naver_search() method를 호출하여
        백그라운드에서 실행을 하고
        실행이 완료되면 완료 event를 받을 method
        RecyclerView에 데이터를 표현
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MovieAdapter movieAdapter = new MovieAdapter(mList);
        recyclerView.setAdapter(movieAdapter);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void naver_search(){

        try {
            String apiURL = naver_movie_url;
            apiURL += "?query=" + URLEncoder.encode(strSearch, "UTF-8");
            apiURL += "&display=20"; // 한번에 20개씩 보여라
            apiURL += "&start=1";

            URL url = new URL(apiURL);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("X-Naver-Client-Id", NaverSecurity.NAVER_ID);
            httpsURLConnection.setRequestProperty("X-Naver-Client-Secret", NaverSecurity.NAVER_SEC);

            int resCode = httpsURLConnection.getResponseCode();

            BufferedReader buffer;
            // 정상적으로 응답이 오면
            if(resCode == 200){
                buffer = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            // 오류가 발생하면
            }else{
                buffer = new BufferedReader(new InputStreamReader(httpsURLConnection.getErrorStream()));
            }

            String resString = "";
            String reader;
            while(true){
                reader = buffer.readLine();
                if(reader == null) break;
                    resString += reader;
            }

            JSONObject resJson = new JSONObject(resString);
            JSONArray resItems = resJson.getJSONArray("items");

            // java 1.8에서 List의 Type이 설정되면 ArrayList<>에는 Type을 설정하지 않아도 오류가 나지 않음
            mList = new ArrayList<>();

            for(int i = 0 ; i < resItems.length(); i++ ){
                JSONObject item = resItems.getJSONObject(i);
                NaverMovieItem mVO = NaverMovieItem.builder()
                        .title(item.getString("title"))
                        .director(item.getString("director"))
                        .actor(item.getString("actor"))
                        .link(item.getString("link"))
                        .userRating(item.getString("userRating"))
                        .image(item.getString("image"))
                        .build();
                mList.add(mVO);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}

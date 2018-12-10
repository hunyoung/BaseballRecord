package com.example.hun.baseballrecord.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hun.baseballrecord.Adapter.VideoRecyclerAdapter;
import com.example.hun.baseballrecord.Model.YouTubeSearchModel;
import com.example.hun.baseballrecord.R;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;

import com.google.android.youtube.player.YouTubePlayer;

import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;


public class VideoActivity extends YouTubeBaseActivity {
    private static String TAG = "VideoActivity";

    private YouTubePlayerView youTubeView;

    private Button button;
    private EditText et;
    private RecyclerView youtubeRecyclerView;
    private List<YouTubeSearchModel> dataList = null;
    private VideoRecyclerAdapter VideoRecyclerAdapter = null;
    private String serverKey = "AIzaSyCuAjoENl1G_hs-B2EnVaBG5ZIdddiqPFM";

    private YouTubePlayer.OnInitializedListener listener;
    AsyncTask<?, ?, ?> searchTask;

    /** Global instance properties filename. */
    private static String PROPERTIES_FILENAME = "AIzaSyCuAjoENl1G_hs-B2EnVaBG5ZIdddiqPFM";

    /** Global instance of the HTTP transport. */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /** Global instance of the max number of videos we want returned (50 = upper limit per page). */
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    /** Global instance of Youtube object to make all API requests. */
    private static YouTube youtube;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);
        init();
    }

    /**
     * 레이아웃 초기화
     */
    private void init() {
        Log.d(TAG, "init()");
        button = findViewById(R.id.youtubebutton);
        youTubeView = findViewById(R.id.youtubeView);
        et =  findViewById(R.id.eturl);
        youtubeRecyclerView = findViewById(R.id.youtubeRecyclerView);
        dataList = new ArrayList<>();
        addMainMenuDummy();
        Button searchBtn = findViewById(R.id.search);

        Log.d("youtube Test",
                "사용가능여부:"+YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this)); //SUCCSESS

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTask = new searchTask().execute();
            }
        });

        listener = new YouTubePlayer.OnInitializedListener(){

            //초기화 성공시
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("U4X-fjfkBR4");//url의 맨 뒷부분 ID값만 넣으면 됨

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //첫번째 인자는 API키값 두번째는 실행할 리스너객체를 넘겨줌
                youTubeView.initialize("AIzaSyCuAjoENl1G_hs-B2EnVaBG5ZIdddiqPFM", listener);
            }
        });

    }


    private class searchTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            getUtube();

//            try {
//                Log.d(TAG,"gggggggggggggggggggggggggggg");
//
//                /*
//                 * The YouTube object is used to make all API requests. The last argument is required, but
//                 * because we don't need anything initialized when the HttpRequest is initialized, we override
//                 * the interface and provide a no-op function.
//                 */
//                youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
//                    public void initialize(HttpRequest request)  {}
//                }).setApplicationName("baseballrecord").build();
//
//                // Get query term from user.
//                String queryTerm = getInputQuery();
//
//                YouTube.Search.List search = youtube.search().list("id,snippet");
//                /*
//                 * It is important to set your developer key from the Google Developer Console for
//                 * non-authenticated requests (found under the API Access tab at this link:
//                 * code.google.com/apis/). This is good practice and increased your quota.
//                 */
////            String apiKey = properties.getProperty(serverKey);
//                String apiKey = serverKey;
//                Log.d(TAG, "Youtube search ===> " + search.toString());
//                Log.d(TAG, "Youtube search ===> " + search);
//                search.setKey(apiKey);
//                search.setQ(queryTerm);
//                /*
//                 * We are only searching for videos (not playlists or channels). If we were searching for
//                 * more, we would add them as a string like this: "video,playlist,channel".
//                 */
//                search.setType("video");
//                /*
//                 * This method reduces the info returned to only the fields we need and makes calls more
//                 * efficient.
//                 */
//                search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
//                search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
//                SearchListResponse searchResponse = search.execute();
//                Log.d(TAG, "searchResponse  ====   " + searchResponse );
//
//                List<SearchResult> searchResultList = searchResponse.getItems();
//
//                if (searchResultList != null) {
//                    prettyPrint(searchResultList.iterator(), queryTerm);
//                }
//            } catch (GoogleJsonResponseException e) {
//                System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
//                        + e.getDetails().getMessage());
//            } catch (IOException e) {
//                System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
//            } catch (Throwable t) {
//                t.printStackTrace();
//            }
//


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            setRecyclerView();
        }
    }

    private void addMainMenuDummy() {
        Log.d(TAG, "addMainMenuDummy");
//        dataList.add(new YouTubeSearchModel("videoId", "title", "url", "publishedAt"));
    }

    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        VideoRecyclerAdapter = new VideoRecyclerAdapter(getApplicationContext(), R.layout.video_item, dataList);
        youtubeRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        youtubeRecyclerView.setHasFixedSize(true);

        VideoRecyclerAdapter.OnItemClickListener mOnItemClickListener = new VideoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "position ==>  " + position);

            }
        };
        VideoRecyclerAdapter.setOnItemClickListener(mOnItemClickListener);
        youtubeRecyclerView.setAdapter(VideoRecyclerAdapter);

    }

    public void getUtube() {

        URL url = null;
        try {
            url = new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=20&q=" + et.getText().toString() + "&key=" + serverKey);
            Log.d(TAG, "video url ===> " + url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            Log.d(TAG, "resopnse code =====> " + responseCode);
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
//            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line + "\n");
            }
            br.close();
            con.disconnect();
            Log.d("Aaaaaaaaaaaaaaaa", "" + sb.toString());

            paringJsonData(sb.toString());

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void paringJsonData(String jsonString) {
        dataList.clear();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray contacts = jsonObject.getJSONArray("items");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String vodid = c.getJSONObject("id").getString("videoId");

                String title = c.getJSONObject("snippet").getString("title");
                Log.d(TAG, "title ====>   " + title);
                String changString = "";
                try {
                    changString = new String(title.getBytes("8859_1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String date = c.getJSONObject("snippet").getString("publishedAt")
                        .substring(0, 10);
                String imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails")
                        .getJSONObject("default").getString("url");

                Log.d(TAG, "imgUrl ====>   " + imgUrl);

                 dataList.add(new YouTubeSearchModel(vodid, title, imgUrl, date));

            }

        } catch (JSONException e){
            e.printStackTrace();
        }

    }




    /*
     * Prints out all SearchResults in the Iterator. Each printed line includes title, id, and
     * thumbnail.
     *
     * @param iteratorSearchResults Iterator of SearchResults to print
     *
     * @param query Search query (String)
     */
    private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {

        System.out.println("\n=============================================================");
        System.out.println(
                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
        System.out.println("=============================================================\n");

        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            // Double checks the kind is video.
            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("default");

                System.out.println(" Video Id" + rId.getVideoId());
                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
                System.out.println(" Thumbnail: " + thumbnail.getUrl());
                System.out.println("\n-------------------------------------------------------------\n");
            }
        }
    }

    /*
     * Returns a query term (String) from user via the terminal.
     */
    private String getInputQuery() {

        String inputQuery = "";

        System.out.print("Please enter a search term: ");
//        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
//        inputQuery = bReader.readLine();

        inputQuery = et.getText().toString();
        Log.d(TAG, "getInputQuery() ===>   " + inputQuery);
        if (inputQuery.length() < 1) {
            // If nothing is entered, defaults to "YouTube Developers Live."
            inputQuery = "YouTube Developers Live";
        }
        return inputQuery;
    }


}

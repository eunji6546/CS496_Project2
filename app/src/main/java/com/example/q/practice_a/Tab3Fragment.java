package com.example.q.practice_a;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Tab3Fragment extends Fragment {

    ListView postListView;
    PostViewAdapter postViewAdapter;
    JSONArray jsonArray;
    JSONObject jsonObject;

    public Tab3Fragment() {
        // Required empty public constructor
    }

    public  static Tab3Fragment newInstance(Bundle args) {
        Tab3Fragment fragment = new Tab3Fragment();
        Bundle arg = new Bundle();
        fragment.setArguments(arg);
        return fragment;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkinfo = cm.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*new Thread() {
            public void run() {
                new DownloadPostList().execute("http://143.248.47.61:8000/postlist");
            }
        }.start();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_tab3,container, false);
        postListView = (ListView)rootview.findViewById(R.id.postList);


        Button writeButton = (Button) rootview.findViewById(R.id.writebtn);
        writeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BulletinWirte.class);
                startActivityForResult(intent,1);
            }
        });
        // 서버로 부터 게시글 목록 받아오고, 리스트 뷰에 넣기
        new Thread() {
            public void run() {
                new DownloadPostList().execute("http://143.248.47.61:8000/postlist");
            }
        }.start();
        return rootview;
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class ListViewExampleClickListener implements AdapterView.OnItemClickListener{
        String  pn;
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           /*Intent intent = new Intent(getActivity(),PostDetail.class);
            OnePost mData = PostViewAdapter.mListPost.get(position);
            intent.putExtra("title",mData.mTitle);
            intent.putExtra("writer",mData.mWriter);
            intent.putExtra("contents",mData.mContents);
            intent.putExtra("password",mData.mPassword);
            intent.putExtra("keynum",mData.mKeyNum);

            startActivity(intent);*/
            Intent intent = new Intent(Tab3Fragment.this.getActivity(),PostDetail.class);
            OnePost mData = PostViewAdapter.mListPost.get(position);
            intent.putExtra("title",mData.mTitle);
            intent.putExtra("writer",mData.mWriter);
            intent.putExtra("contents",mData.mContents);
            intent.putExtra("password",mData.mPassword);
            intent.putExtra("keynum",mData.mKeyNum);

            startActivityForResult(intent,1);
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        new Thread() {
            public void run() {
                new DownloadPostList().execute("http://143.248.47.61:8000/postlist");
            }
        }.start();

    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.

    class DownloadPostList extends AsyncTask<String, Void, String> {

        JSONArray jsonArray;

        protected String doInBackground(String... url) {
            postViewAdapter = new PostViewAdapter(getActivity());
            // params comes from the execute() call: params[0] is the url.
            try {
                // 리스트 뷰에 넣기
                // [{name : a , id : b, photo: c},{},{}]

                try {
                   // jsonArray = new JSONArray(new HttpGetResponse().execute(url[0])/*downloadUrl(url[0])*/);
                    jsonArray = new JSONArray(downloadUrl(url[0]));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject one = jsonArray.getJSONObject(i);
                        String title;
                        String writer;
                        String keynum;
                        String contents;
                        String password;

                        title = one.getString("title");
                        writer = one.getString("writer");
                        keynum = one.getString("keynum");
                        contents = one.getString("contents");
                        password = one.getString("pw");

                        postViewAdapter.addItem(title, writer,keynum,contents,password);

                    }


                }catch (JSONException e){
                    e.printStackTrace();
                }

            } catch (IOException e) {
                Log.e("","Unable to retrieve web page. URL may be invalid.");
            }
            return null;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            if (jsonArray.length() != 0){
                postListView.setAdapter(postViewAdapter);
                ListViewExampleClickListener listViewExampleClickListener = new ListViewExampleClickListener();
                postListView.setOnItemClickListener(listViewExampleClickListener);
            }
        }
    }
    // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;


        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

//            // Convert the InputStream into a string
//            String contentAsString = readIt(is, len);
//           // Log.e("@@",contentAsString);
//            return contentAsString;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader( conn.getInputStream() )
            );

            JSONArray jsonResponse = null;
            //initiate strings to hold response data
            String inputLine;
            String responseData = "";
            //read the InputStream with the BufferedReader line by line and add each line to responseData
            while ( ( inputLine = in.readLine() ) != null ){
                responseData += inputLine;
            }

            return responseData;


            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

}

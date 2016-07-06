package com.example.q.practice_a;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;


public class Tab1Fragment extends Fragment {
    ListView contactListView;
    ListViewAdapter listViewAdapter;

    public Tab1Fragment() {
        // Required empty public constructor
    }

    public static Tab1Fragment newInstance(Bundle args) {
        Tab1Fragment fragment = new Tab1Fragment();
        Bundle arg = new Bundle();
        fragment.setArguments(arg);
        return fragment;
}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);

        contactListView = (ListView)rootView.findViewById(R.id.contactList);
////
//        listViewAdapter = new ListViewAdapter(getActivity());
//        contactListView.setAdapter(listViewAdapter);

        /*new Thread() {
            public void run() {
                new DownloadContactList().execute("http://143.248.47.61:8000/fbcontacts");
                new DownloadContactList().execute("http://143.248.47.56:1337/pbcontacts");
            }
//        }.start();*/

        new DownloadContactList().execute("http://143.248.47.61:8000/pbcontacts","http://143.248.47.61:8000/fbcontacts");
       // new DownloadContactList().execute("http://143.248.47.61:8000/fbcontacts");
        return rootView;
    }

     class DownloadContactList extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... url) {

            // params comes from the execute() call: params[0] is the url.
            try {
                // 리스트 뷰에 넣기
                // [{name : a , id : b, photo: c},{},{}]
                listViewAdapter = new ListViewAdapter(getActivity());
                try {
                    JSONArray jsonArray0 = new JSONArray(downloadUrl(url[0]));
                    JSONArray jsonArray1 = new JSONArray(downloadUrl(url[1]));
                    JSONArray jsonArray = new JSONArray(jsonArray0.toString().replace("]",",")+jsonArray1.toString().replace("[",""));
                    Log.e("!!",jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject one = jsonArray.getJSONObject(i);
                        String name;
                        String numberOremail;
                        String from;
                        String photo;


                        name = one.getString("name");
                        try {
                            numberOremail = one.getString("phonenumber");
                            from = "1";

                        }catch (JSONException e){
                            //페북연락처일때
                            numberOremail = "";
                            from = "0";
                        }
                        photo = one.getString("photo");
                        listViewAdapter.addItem(photo, name, numberOremail, from);
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
            contactListView.setAdapter(listViewAdapter);
            ListViewExampleClickListener listViewExampleClickListener = new ListViewExampleClickListener();
            contactListView.setOnItemClickListener(listViewExampleClickListener);

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
            is = conn.getInputStream();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader ( conn.getInputStream() )
            );

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
    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }



    public class ListViewExampleClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), ContactDetail.class);
            OneContact mData = ListViewAdapter.mListData.get(position);
            intent.putExtra("name",mData.mName);
            intent.putExtra("numberOrEmail",mData.mNumberOrEmail);
            intent.putExtra("profilePhoto", (CharSequence) mData.mPhoto);
            intent.putExtra("from",mData.mFrom);
            startActivity(intent);


        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.example.q.practice_a;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
    JSONObject object;
    JSONArray contactList;

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


        // 휴대폰 연락처 보내기
        new Thread(){
            public void run() {
                try {
                    JSONArray jarray = sendJSONinfo();
                   // new HttpConnectionThread2().doInBackground("http://143.248.47.163:3000/insert",jarray.toString());
                    new HttpConnectionThread().doInBackground("http://143.248.47.56:1337/insert/pb",jarray.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        // Inflate the layout for this fragment

        // 서버에서 받아서 리스트 뷰에 넣기

        new Thread() {
            public void run() {
                new DownloadContactList().execute("http://143.248.47.61:8000/fbcontacts");

            }
        }.start();


        return rootView;
    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.

     class DownloadContactList extends AsyncTask<String, Void, String> {



        protected String doInBackground(String... url) {

            // params comes from the execute() call: params[0] is the url.
            try {
                // 리스트 뷰에 넣기
                // [{name : a , id : b, photo: c},{},{}]
                listViewAdapter = new ListViewAdapter(getActivity());
                try {
                    Log.e("11","!!!!!11!!!!!1");
                    JSONArray jsonArray = new JSONArray(downloadUrl(url[0]));
                    Log.e("222","@@@@@@@@@@@@@");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject one = jsonArray.getJSONObject(i);
                        String name;
                        String numberOremail;
                        String from;
                        String photo; //url?


                        name = one.getString("name");
                        try {

                            numberOremail = one.getString("numberOrEmail");
                            from = "1";
                        }catch (JSONException e){
                            //페북연락처일때
                            numberOremail = "DO NOT HAVE NUMBER";
                            from = "0";
                        }

                        photo = one.getString("photo");
                        Log.e("NAME",name);

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
            Log.e("S","SDFASDFASDFASDFASDF");
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
            int response = conn.getResponseCode();
            is = conn.getInputStream();

//            // Convert the InputStream into a string
//            String contentAsString = readIt(is, len);
//           // Log.e("@@",contentAsString);
//            return contentAsString;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader ( conn.getInputStream() )
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
    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public JSONArray sendJSONinfo() throws JSONException {



        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.Data.CONTACT_ID
        };

        String[] selectionArgs = null;

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        Cursor contactCursor = getActivity().getContentResolver().query(uri,projection,null,selectionArgs, sortOrder);
        //Cursor contactCursor = managedQuery(uri,null,null,selectionArgs, sortOrder);


        object = new JSONObject();
        contactList = new JSONArray();
        listViewAdapter = new ListViewAdapter(getActivity());
        JSONObject oneContact;

        if (contactCursor.moveToFirst()) {
            do {
                try {
                    oneContact = new JSONObject();
                    oneContact.put("name", contactCursor.getString(1));
                    oneContact.put("phonenumber", contactCursor.getString(0));
                    ContentResolver cr = getActivity().getContentResolver();
                    int contactId_idx = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
                    Long contactid = contactCursor.getLong(3);

                    Uri puri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, puri);
                    if (input != null) {
                        oneContact.put("profileuri", puri);
                    } else {
                        oneContact.put("profileuri", "NONE");
                    }

                    contactList.put(oneContact);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } while (contactCursor.moveToNext());

        }
        return contactList;
     }

    public class ListViewExampleClickListener implements AdapterView.OnItemClickListener{
        String  pn;
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), ContactDetail.class);
            Log.e("**","*************");
            OneContact mData = ListViewAdapter.mListData.get(position);
            intent.putExtra("name",mData.mName);
            intent.putExtra("numberOrEmail",mData.mNumberOrEmail);
            intent.putExtra("profilePhoto", (CharSequence) mData.mPhoto); //되나?
            intent.putExtra("from",mData.mFrom);
            Log.e("**","*************");
            startActivity(intent);


        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

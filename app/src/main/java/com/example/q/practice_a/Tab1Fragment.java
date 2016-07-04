package com.example.q.practice_a;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Tab1Fragment extends Fragment {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }

    public class ListViewExampleClickListener implements AdapterView.OnItemClickListener{
        String  pn;
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Intent intent = new Intent(getActivity(), ContactDetail.class);
//            getViewByPosition(position,(ListView)view);
//            //여기에 넘버 찾아서 넘기기 모르겠다 !!
//
//            OneContact mData = ListViewAdapter.mListData.get(position);
//            intent.putExtra("phoneNumber",mData.mNumberOrEmail);
//            startActivity(intent);
//            //적당한 리스너 만들기

        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

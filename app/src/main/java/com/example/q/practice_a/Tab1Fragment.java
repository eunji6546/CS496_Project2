package com.example.q.practice_a;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;


public class Tab1Fragment extends Fragment {



    public Tab1Fragment() {
        // Required empty public constructor
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
//            Intent intent = new Intent(getActivity(), Call.class);
//            //getViewByPosition(position,(ListView)listPerson);
//            //여기에 넘버 찾아서 넘기기 모르겠다 !!
//
//            ListData mData = ListViewAdapter.mListData.get(position);
//            intent.putExtra("phoneNumber",mData.mNumber);
//            startActivity(intent);
            //적당한 리스너 만들기

        }
    }

}

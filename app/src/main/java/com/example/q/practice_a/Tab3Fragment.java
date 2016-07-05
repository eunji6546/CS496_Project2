package com.example.q.practice_a;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;

public class Tab3Fragment extends Fragment {
    View view;
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab3,container, false);
        Button writeButton = (Button) view.findViewById(R.id.writebtn);
        writeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BulletinWirte.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.example.q.practice_a;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by q on 2016-07-06.
 */
public class PostViewAdapter extends BaseAdapter {

    private Context mContext = null;
    public static ArrayList<OnePost> mListPost= null ;

    public  PostViewAdapter(Context context){
        super();
        this.mContext = context;
        this.mListPost = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return mListPost.size();
    }

    @Override
    public Object getItem(int position) {
        return mListPost.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PostHolder holder;
        if (convertView == null){

            holder = new PostHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.postlistview,null);

            holder.mTitle = (TextView) convertView.findViewById(R.id.title);
            holder.mWriter = (TextView) convertView.findViewById(R.id.writer);
            holder.mKeynum = (TextView) convertView.findViewById(R.id.keynum);

            convertView.setTag(holder);
        }else {
            holder = (PostHolder) convertView.getTag();
        }
        OnePost mData = mListPost.get(position);
        holder.mTitle.setText("Title : "+mData.mTitle);
        holder.mWriter.setText("Writer : "+mData.mWriter);
        holder.mKeynum.setText(mData.mKeyNum);
        holder.mKeynum.setVisibility(View.INVISIBLE);


        return convertView;
    }
    public void addItem(String title, String writer, String keynum,String contents, String password){
        //assume that photo is url


        OnePost addInfo;
        addInfo = new OnePost();
        addInfo.mTitle = title;
        addInfo.mWriter = writer;
        addInfo.mKeyNum = keynum;
        addInfo.mContents = contents;
        addInfo.mPassword = password;

        mListPost.add(addInfo);
    }
}

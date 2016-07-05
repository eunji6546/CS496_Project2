package com.example.q.practice_a;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;


public class ListViewAdapter extends BaseAdapter {
    private Context mContext = null;
    public static ArrayList<OneContact> mListData = new ArrayList<>();

    public ListViewAdapter(Context context){
        super();
        this.mContext = context;
    }
    @Override
    public int getCount() {return mListData.size();}

    @Override
    public Object getItem(int position) { return  mListData.get(position);}
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listviewlayout,null);

            holder.mPhoto = (ImageView) convertView.findViewById(R.id.mPhoto);
            holder.mName = (TextView) convertView.findViewById(R.id.mName);
            holder.mNumberOrEmail = (TextView) convertView.findViewById(R.id.mNumberOrEmail);
            holder.mContactType = (ImageView)convertView.findViewById(R.id.mContactType);
            convertView.setTag(holder);
            Log.e("@@@",holder.mNumberOrEmail.toString());

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        OneContact mData = mListData.get(position);

        if (mData.mPhoto!=null){
            new DownloadImageTask((ImageView) holder.mPhoto)
                    .execute(mData.mPhoto);
            //holder.mPhoto.setVisibility(View.VISIBLE);
            //holder.mPhoto.setImageDrawable(mData.mPhoto);
        }else {
            // Default photo
        }

        Log.e("))))))",mData.mFrom);
        if (mData.mFrom=="0" ){
            // from facebook
            holder.mContactType.setVisibility(View.VISIBLE);
            holder.mContactType.setImageResource(R.drawable.facebook_logo);
        }else {
            //from phonebook

            holder.mContactType.setVisibility(View.VISIBLE);
           // holder.mContactType.setImageDrawable(R.drawable.phonebook);
            holder.mContactType.setImageResource(R.drawable.phonebook);
        }

        //////////
        // 페북아이디 혹은 이메일로 바꿀 수 있도록 ///
        //////////
        holder.mName.setText(mData.mName);
        holder.mNumberOrEmail.setText(mData.mNumberOrEmail);

        return convertView;
    }

    public void addItem(String photo, String name, String number, String from ){
        //assume that photo is url

        Log.e("J","ASDFSADFSADFASD0");
        OneContact addInfo;
        addInfo = new OneContact();
        addInfo.mPhoto = photo;
        addInfo.mName = name;
        addInfo.mNumberOrEmail = number;
        addInfo.mFrom = from;

        mListData.add(addInfo);
    }

}

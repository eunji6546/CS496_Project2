package com.example.q.practice_a;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        OneContact mData = mListData.get(position);

        if (mData.mFrom=="0" ){
            // from facebook
            holder.mContactType.setVisibility(View.VISIBLE);
            holder.mContactType.setImageResource(R.drawable.facebook_logo);
            new DownloadImageTask((ImageView) holder.mPhoto).execute(mData.mPhoto);
        }else {
            //from phonebook
            holder.mContactType.setVisibility(View.VISIBLE);
            holder.mContactType.setImageResource(R.drawable.phonebook);
            InputStream input = null;

            input = ContactsContract.Contacts.openContactPhotoInputStream(mContext.getContentResolver(), Uri.parse(mData.mPhoto));
            if (input != null) {
                holder.mPhoto.setImageBitmap( BitmapFactory.decodeStream(input));
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                holder.mPhoto.setImageResource(R.drawable.blank_user_profile);
            }
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

        //Log.e("J","ASDFSADFSADFASD0");
        OneContact addInfo;
        addInfo = new OneContact();
        addInfo.mPhoto = photo;
        addInfo.mName = name;
        addInfo.mNumberOrEmail = number;
        addInfo.mFrom = from;

        mListData.add(addInfo);
    }

}

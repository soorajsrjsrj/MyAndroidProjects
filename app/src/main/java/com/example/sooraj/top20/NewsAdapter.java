package com.example.sooraj.top20;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class NewsAdapter extends BaseAdapter {
    private List<News> nw;
    private Context context;



    public NewsAdapter(Context context){

        this.nw = new ArrayList<>();
        this.context=context;

    }

    public void addFeeds(List<News> feedArrayList) {
        this.nw = feedArrayList;
    }

    public void clear() {
        nw.clear();
    }
    @Override
    public int getCount() {
        return nw.size();
    }

    @Override
    public News getItem(int position) {
        return nw.get(position);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView =convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(this.context).inflate(R.layout.news_list_item,parent,false);
        }
        News nw=getItem(position);

        TextView newtitle= (TextView) listItemView.findViewById(R.id.newstitle);
        newtitle.setText(nw.getMtitle());

        TextView newdescription =(TextView) listItemView.findViewById(R.id.newsdescription);
        newdescription.setText(nw.getMdescription());

        ImageView newurltoimage =(ImageView) listItemView.findViewById(R.id.newsurltoimage);
        Picasso.get().load(nw.getMurlToImage()).into(newurltoimage);



        return listItemView;
    }

    }

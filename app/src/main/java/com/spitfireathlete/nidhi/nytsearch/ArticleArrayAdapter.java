package com.spitfireathlete.nidhi.nytsearch;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nidhikulkarni on 2/9/16.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    private LayoutInflater inflater;

    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
        inflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }

        ImageView iv = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tv = (TextView) convertView.findViewById(R.id.tvTitle);


        tv.setText(article.getHeadline());

        iv.setImageResource(0); // clear it
        if (!TextUtils.isEmpty(article.getThumbnail())) {
            Picasso.with(getContext()).load(article.getThumbnail()).into(iv);
        }


        return convertView;
    }
}

package com.walmartlabs.classwork.fakeinstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by abalak5 on 10/11/15.
 */
public class PhotosAdaper extends ArrayAdapter<Photo> {
    public PhotosAdaper(Context context, List<Photo> objects) {
        super(context, 0, objects);
    }

    //convertView is recycling stuff that was talked about. For efficient list viewing
    public View getView(int position, View convertView, ViewGroup parent) {
        Photo photo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        // Lookup view for data population
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.getImageUrl()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(ivPhoto);
        tvCaption.setText(photo.getCaption());
        // Return the completed view to render on screen
        return convertView;
    }
}

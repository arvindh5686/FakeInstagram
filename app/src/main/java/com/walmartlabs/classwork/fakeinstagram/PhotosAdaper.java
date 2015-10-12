package com.walmartlabs.classwork.fakeinstagram;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
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
        ImageView ivProfilePic = (ImageView) convertView.findViewById(R.id.ivProfilePic);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvLastCommentUserName = (TextView) convertView.findViewById(R.id.tvCommentUserName);
        TextView tvLastComment = (TextView) convertView.findViewById(R.id.tvComment);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvProfileUserName = (TextView) convertView.findViewById(R.id.tvProfileUserName);

        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.getImageUrl()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(ivPhoto);
        tvUserName.setText(photo.getUserName());
        tvProfileUserName.setText(photo.getUserName());
        tvCaption.setText(photo.getCaption());
        ArrayList<Comment> comments = photo.getComments();

        tvLastCommentUserName.setText(comments.get(comments.size() - 1).getUserName());
        tvLastComment.setText(comments.get(comments.size() - 1).getText());

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(3)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        Picasso.with(getContext())
                .load(photo.getProfilePicUrl())
                .fit()
                .transform(transformation)
                .into(ivProfilePic);
        // Return the completed view to render on screen
        return convertView;
    }
}

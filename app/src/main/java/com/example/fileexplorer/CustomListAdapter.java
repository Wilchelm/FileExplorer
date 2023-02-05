package com.example.fileexplorer;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Product> {

    ArrayList<Product> items;
    Context context;
    int resource;

    public CustomListAdapter(Context context, int i, ArrayList<Product> items) {
        super(context, i, items);
        this.context = context;
        this.resource = i;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_list_adapter, null, true);
        }
        Product product;
        try {
            product = getItem(position);
        } catch (Exception e) {
            return convertView;
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewItem);
        TextView textView = (TextView) convertView.findViewById(R.id.txtName);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        Drawable drawable = imageView.getDrawable();
        Boolean checked = product.getChecked();

        Boolean pom = product.getDirectory();
        if (pom == true) {
            imageView.setImageResource(R.drawable.ic_baseline_folder_24);
        }
        if (pom == false) {
            Boolean thru = false;
            String someFilepath = product.getPath();
            String extension = someFilepath.substring(someFilepath.lastIndexOf("."));
            if (extension.equals(".mp3") || extension.equals(".wav") || extension.equals(".au") || extension.equals(".mid") || extension.equals(".ogg") || extension.equals(".flac")) {
                imageView.setImageResource(R.drawable.ic_baseline_audiotrack_24);
                thru=true;
            }
            if (extension.equals(".mp4") || extension.equals(".mpg") || extension.equals(".mpeg") || extension.equals(".avi")) {
                imageView.setImageResource(R.drawable.ic_baseline_ondemand_video_24);
                thru=true;
            }
            if (extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".gif") || extension.equals(".bmp") || extension.equals(".png") || extension.equals(".pcx")) {
                imageView.setImageResource(R.drawable.ic_baseline_photo_24);
                thru=true;
            }
            if (extension.equals(".zip") || extension.equals(".rar") || extension.equals(".7z") || extension.equals(".gz") || extension.equals(".tgz")) {
                imageView.setImageResource(R.drawable.ic_baseline_archive_24);
                thru=true;
            }
            //add here to have more file formats with icons recognised
            else {
                if (thru==false) {
                    imageView.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
                }
            }
        }
        textView.setText(product.getName());
        if (checked) {
            checkBox.setChecked(true);
        }
        else checkBox.setChecked(false);;
        return convertView;
    }
}


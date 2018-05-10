package com.asyf.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asyf.app.R;
import com.asyf.app.entity.Contact;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class ContactAdapter extends BaseAdapter {

    private List<Contact> contacts;
    private Context context;

    public ContactAdapter(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_contact_list, parent, false);
        ImageView img_icon = (ImageView) convertView.findViewById(R.id.imgtou);
        TextView txt_aName = (TextView) convertView.findViewById(R.id.name);
        TextView txt_aSpeak = (TextView) convertView.findViewById(R.id.says);
        img_icon.setBackgroundResource(contacts.get(position).getImgtou());
        txt_aName.setText(contacts.get(position).getName());
        txt_aSpeak.setText(contacts.get(position).getSays());
        return convertView;
    }
}

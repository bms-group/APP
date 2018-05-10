package com.asyf.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.asyf.app.R;
import com.asyf.app.activity.MessageListActivity;
import com.asyf.app.adapter.ContactAdapter;
import com.asyf.app.entity.Contact;
import com.asyf.app.entity.MyMessage;
import com.asyf.app.myView.ChatView;

import java.util.ArrayList;
import java.util.List;

import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.MessageList;
import cn.jiguang.imui.messages.MsgListAdapter;


public class PlanFragment extends Fragment {

    private ListView listView;
    private List<Contact> contacts;
    private Context mContext;
    private ContactAdapter contactAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fg_plan, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity a = getActivity();
        mContext = getContext();
        listView = a.findViewById(R.id.contact_list);
        contacts = new ArrayList<>();
        contacts.add(new Contact(R.mipmap.iv_icon_1, "小明", "在吗？"));
        contacts.add(new Contact(R.mipmap.iv_icon_1, "小明2", "在吗？"));
        contacts.add(new Contact(R.mipmap.iv_icon_1, "小明3", "在吗？"));
        contacts.add(new Contact(R.mipmap.iv_icon_1, "小明4", "在吗？"));
        contactAdapter = new ContactAdapter(contacts, mContext);
        listView.setDividerHeight(3);
        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = contacts.get(position).getName();
                Toast.makeText(mContext, "您点击了" + contacts.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MessageListActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        /*List<String> contacts = new ArrayList<>();
        contacts.add("鲁班七号");
        contacts.add("马可波罗");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_expandable_list_item_1, contacts);
        ListView listView = a.findViewById(R.id.contact);
        listView.setAdapter(arrayAdapter);*/
        /*MessageList messageList = a.findViewById(R.id.msg_list);
        MsgListAdapter.HoldersConfig holdersConfig = new MsgListAdapter.HoldersConfig();
        MsgListAdapter adapter = new MsgListAdapter<>("0", holdersConfig, null);
        MyMessage myMessage = new MyMessage("qqq", IMessage.MessageType.SEND_TEXT);
        List<MyMessage> ms = new ArrayList<>();
        ms.add(myMessage);
        adapter.addToEnd(ms);
        messageList.setAdapter(adapter);*/
        //Intent intent = new Intent(getActivity(), MessageListActivity.class);
        //startActivity(intent);
    }

}

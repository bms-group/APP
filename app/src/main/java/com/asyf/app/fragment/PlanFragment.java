package com.asyf.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asyf.app.R;
import com.asyf.app.activity.MessageListActivity;
import com.asyf.app.entity.MyMessage;
import com.asyf.app.myView.ChatView;

import java.util.ArrayList;
import java.util.List;

import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.MessageList;
import cn.jiguang.imui.messages.MsgListAdapter;


public class PlanFragment extends Fragment {


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
        Intent intent = new Intent(getActivity(), MessageListActivity.class);
        startActivity(intent);
    }

}

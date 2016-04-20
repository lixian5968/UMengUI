package com.zongbutech.umenguilxcopy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zongbutech.httplib.http.JsonUtils;
import com.zongbutech.httplib.http.OkHttpUtils;
import com.zongbutech.umenguilxcopy.Bean.HttpTopic;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lixian on 2016/4/19.
 */
public class FragmentTopic extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.homeTopicType_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshWidget;
    @Bind(R.id.homeTopicType_recycle)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    TopicAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_topic, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshWidget.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        //setHasFixedSize()方法用来使RecyclerView保持固定的大小，该信息被用于自身的优化。
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new TopicAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        onRefresh();


        return view;
    }


    @Override
    public void onRefresh() {
        String Url = "http://rtgs-api.wuweixing.com:4001/api/v1/Topics";
        OkHttpUtils.get(Url, new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                try {
                    JSONArray arr = new JSONArray(resultString);
                    List<HttpTopic> myList = new ArrayList<HttpTopic>();
                    for (int i = 0; i < arr.length(); i++) {
                        HttpTopic bean = JsonUtils.deserialize(arr.get(i).toString(), HttpTopic.class);
                        myList.add(bean);
                    }

                    mAdapter.isShowFooter(false);
                    mSwipeRefreshWidget.setRefreshing(false);
                    mAdapter.setmDate(myList);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }


}

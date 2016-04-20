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
import android.widget.TextView;

import com.zongbutech.httplib.http.JsonUtils;
import com.zongbutech.httplib.http.OkHttpUtils;
import com.zongbutech.umenguilxcopy.Bean.HttpFeed;
import com.zongbutech.umenguilxcopy.Bean.HttpUserInfo;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lixian on 2016/4/19.
 */
public class FragmentHotFeed extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.umeng_switch_button_one)
    TextView umeng_switch_button_one;
    @Bind(R.id.umeng_switch_button_two)
    TextView umeng_switch_button_two;
    @Bind(R.id.umeng_switch_button_three)
    TextView umeng_switch_button_three;
    @Bind(R.id.umeng_switch_button_four)
    TextView umeng_switch_button_four;


    @Bind(R.id.homeTopicType_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshWidget;
    @Bind(R.id.homeTopicType_recycle)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    HotFeedAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);
        umeng_switch_button_four.setSelected(true);
        umeng_switch_button_one.setOnClickListener(switchListener);
        umeng_switch_button_two.setOnClickListener(switchListener);
        umeng_switch_button_three.setOnClickListener(switchListener);
        umeng_switch_button_four.setOnClickListener(switchListener);


        mSwipeRefreshWidget.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        //setHasFixedSize()方法用来使RecyclerView保持固定的大小，该信息被用于自身的优化。
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new HotFeedAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        onRefresh();


        return view;
    }


    private View.OnClickListener switchListener = new View.OnClickListener() {
        public void onClick(View view) {
            if (view.getId() == R.id.umeng_switch_button_one) {
                umeng_switch_button_one.setSelected(true);
                umeng_switch_button_two.setSelected(false);
                umeng_switch_button_three.setSelected(false);
                umeng_switch_button_four.setSelected(false);
            } else if (view.getId() == R.id.umeng_switch_button_two) {
                umeng_switch_button_one.setSelected(false);
                umeng_switch_button_two.setSelected(true);
                umeng_switch_button_three.setSelected(false);
                umeng_switch_button_four.setSelected(false);
            } else if (view.getId() == R.id.umeng_switch_button_three) {
                umeng_switch_button_one.setSelected(false);
                umeng_switch_button_two.setSelected(false);
                umeng_switch_button_three.setSelected(true);
                umeng_switch_button_four.setSelected(false);
            } else if (view.getId() == R.id.umeng_switch_button_four) {
                umeng_switch_button_one.setSelected(false);
                umeng_switch_button_two.setSelected(false);
                umeng_switch_button_three.setSelected(false);
                umeng_switch_button_four.setSelected(true);
            }

        }
    };


    @Override
    public void onRefresh() {
        String Url = "http://rtgs-api.wuweixing.com:4001/api/v1/Feeds";
        OkHttpUtils.get(Url, new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                try {
                    JSONArray arr = new JSONArray(resultString);
                    List<HttpFeed> myList = new ArrayList<HttpFeed>();
                    for (int i = 0; i < arr.length(); i++) {
                        HttpFeed bean = JsonUtils.deserialize(arr.get(i).toString(), HttpFeed.class);
                        myList.add(bean);
                    }


                    getHttpUserInfo(myList);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    Map<String, HttpUserInfo> maps = new HashMap<String, HttpUserInfo>();
    int Number = 0;

    private void getHttpUserInfo(List<HttpFeed> myList) {

        for (int i = 0; i < myList.size(); i++) {
            getHttpUserInfoById(myList.get(i).creatorId,myList);
            Number++;
        }


    }

    private void getHttpUserInfoById(final String creatorId, final List<HttpFeed> myList) {
        String Url = "http://rtgs-api.wuweixing.com:4001/api/v1/users/UserId?access_token=Awj0Pq5k3bN6wMqQP8DVNbqUSZjo0KH3tnOjjNS6pRqwuGMqA8wraZeZHHpfII6s";
        Url = Url.replace("UserId", creatorId);
        if (maps.get(creatorId) == null) {
            OkHttpUtils.get(Url, new OkHttpUtils.ResultCallback<HttpUserInfo>() {
                @Override
                public void onSuccess(HttpUserInfo result) {
                    maps.put(creatorId, result);
                    Number--;
                    CheckNumber(Number,myList);
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        } else {
            Number--;
            CheckNumber(Number,myList);
        }


    }

    private void CheckNumber(int number, List<HttpFeed> myList) {
        if (number == 0) {
            mAdapter.isShowFooter(false);
            mSwipeRefreshWidget.setRefreshing(false);
            mAdapter.setmDate(myList,maps);
        }

    }
}

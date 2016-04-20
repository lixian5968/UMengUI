package com.zongbutech.umenguilxcopy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lixian on 2016/4/19.
 */
public class FragmentHotDemo extends Fragment {

    @Bind(R.id.umeng_switch_button_one)
    TextView umeng_switch_button_one;
    @Bind(R.id.umeng_switch_button_two)
    TextView umeng_switch_button_two;
    @Bind(R.id.umeng_switch_button_three)
    TextView umeng_switch_button_three;
    @Bind(R.id.umeng_switch_button_four)
    TextView umeng_switch_button_four;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_hot, container, false);
//        View view0 = inflater.inflate(R.layout.umeng_comm_switch_button, null);
//        View view1 = inflater.inflate(R.layout.umeng_comm_search_header_view, null);
//        view.addView(view0,0);
//        view.addView(view1,1);
        ButterKnife.bind(this, view);
        umeng_switch_button_four.setSelected(true);
        umeng_switch_button_one.setOnClickListener(switchListener);
        umeng_switch_button_two.setOnClickListener(switchListener);
        umeng_switch_button_three.setOnClickListener(switchListener);
        umeng_switch_button_four.setOnClickListener(switchListener);
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


}

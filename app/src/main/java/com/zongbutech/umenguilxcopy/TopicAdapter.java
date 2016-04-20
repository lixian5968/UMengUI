package com.zongbutech.umenguilxcopy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.zongbutech.umenguilxcopy.Bean.HttpTopic;
import com.zongbutech.umenguilxcopy.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/19
 */
public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<HttpTopic> mData;
    private boolean mShowFooter = true;
    private Context mContext;


    private OnItemClickListener mOnItemClickListener;

    public TopicAdapter(Context context) {
        this.mContext = context;
    }


    public void setmDate(List<HttpTopic> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.umeng_comm_followed_topic_lv_item, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {

            final HttpTopic mTopic = mData.get(position);
            if (mTopic == null) {
                return;
            }


            ImageLoaderUtils.display(mContext, ((ItemViewHolder) holder).umeng_comm_topic_icon, mTopic.iconUrl);
            ((ItemViewHolder) holder).umeng_comm_topic_tv.setText(mTopic.name);
            ((ItemViewHolder) holder).umeng_comm_topic_desc_tv.setText(mTopic.desc);

        }
    }

    public class ImageGridAdapter extends BaseAdapter {
        List<String> Urls;
        Context ct;

        public ImageGridAdapter(List<String> urls, Context mContext) {
            Urls = urls;
            ct = mContext;
        }

        @Override
        public int getCount() {
            return Urls.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView img = new ImageView(ct);
            ImageLoaderUtils.display(mContext, img, Urls.get(position));
            return img;
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    public HttpTopic getItem(int position) {
        if (mData == null || position >= mData.size()) {
            return null;
        } else {
            return mData.get(position);
        }

    }

    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView umeng_comm_topic_icon;
        public ToggleButton umeng_comm_topic_togglebutton;
        public TextView umeng_comm_topic_tv;
        public TextView umeng_comm_topic_desc_tv;

        public ItemViewHolder(View v) {
            super(v);
            umeng_comm_topic_icon = (ImageView) v.findViewById(R.id.umeng_comm_topic_icon);
            umeng_comm_topic_togglebutton = (ToggleButton) v.findViewById(R.id.umeng_comm_topic_togglebutton);
            umeng_comm_topic_tv = (TextView) v.findViewById(R.id.umeng_comm_topic_tv);
            umeng_comm_topic_desc_tv = (TextView) v.findViewById(R.id.umeng_comm_topic_desc_tv);
        }

        @Override
        public void onClick(View view) {


        }
    }

    private HomeTopicAdapterLoveListener listener;

    public interface HomeTopicAdapterLoveListener {
        void LoveClick(View view, int position);
    }

    public void setOnHomeTopicAdapterLoveListener(HomeTopicAdapterLoveListener listener) {
        this.listener = listener;
    }


}

package com.zongbutech.umenguilxcopy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zongbutech.umenguilxcopy.Bean.HttpFeed;
import com.zongbutech.umenguilxcopy.Bean.HttpUserInfo;
import com.zongbutech.umenguilxcopy.utils.ImageLoaderUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/19
 */
public class HotFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<HttpFeed> mData;
    private boolean mShowFooter = true;
    private Context mContext;


    private OnItemClickListener mOnItemClickListener;

    public HotFeedAdapter(Context context) {
        this.mContext = context;
    }

    Map<String, HttpUserInfo> maps;

    public void setmDate(List<HttpFeed> data, Map<String, HttpUserInfo> maps) {
        this.mData = data;
        this.notifyDataSetChanged();
        this.maps = maps;
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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.umeng_comm_feed_lv_item, parent, false);
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

            final HttpFeed mTopic = mData.get(position);
            if (mTopic == null) {
                return;
            }


            ((ItemViewHolder) holder).umeng_comm_msg_user_name.setText(maps.get(mTopic.creatorId).username);
            ImageLoaderUtils.display(mContext, ((ItemViewHolder) holder).user_portrait_img_btn, maps.get(mTopic.creatorId).iconUrl);

            ((ItemViewHolder) holder).umeng_comm_msg_text.setText(mTopic.content);
            SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
            ((ItemViewHolder) holder).umeng_comm_msg_time_tv.setText(df.format(mTopic.updatedAt));
            ((ItemViewHolder) holder).umeng_comm_like_tv.setText(mTopic.likesCount + "");
            ((ItemViewHolder) holder).umeng_comm_comment_tv.setText(mTopic.commentsCount + "");

            if (mTopic.imageUrls != null && mTopic.imageUrls.size() > 0) {
                ((ItemViewHolder) holder).forward_image_gv_layout.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).umeng_comm_msg_gridview.setAdapter(new ImageGridAdapter(mTopic.imageUrls,mContext));
            } else {
                ((ItemViewHolder) holder).forward_image_gv_layout.setVisibility(View.GONE);
            }


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
            ImageLoaderUtils.display(mContext, img,Urls.get(position));
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

    public HttpFeed getItem(int position) {
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
        public ImageView feed_type_img_btn;
        public ImageView user_portrait_img_btn;
        public RelativeLayout feed_type_icon_container;
        public ImageView umeng_comm_dialog_btn;
        public TextView umeng_comm_favorites_textview;
        public TextView umeng_comm_msg_user_name;
        public LinearLayout user_type_icon_container;
        public TextView umeng_comm_msg_text;
        public ImageView umeng_comm_msg_location;
        public TextView umeng_comm_distance;
        public TextView umeng_comm_msg_location_text;
        public RelativeLayout forward_image_gv_layout;
        public TextView umeng_comm_forard_text_tv;
        public ViewStub umeng_comm_msg_images_gv_viewstub;
        public LinearLayout umeng_comm_feed_action_layout;
        public TextView umeng_comm_msg_time_tv;
        public LinearLayout umeng_comm_forward_btn;
        public TextView umeng_comm_forward_tv;
        public LinearLayout umeng_comm_like_btn;
        public TextView umeng_comm_like_tv;
        public LinearLayout umeng_comm_comment_btn;
        public TextView umeng_comm_comment_tv;
        public GridView umeng_comm_msg_gridview;

        public ItemViewHolder(View v) {
            super(v);
            feed_type_img_btn = (ImageView) v.findViewById(R.id.feed_type_img_btn);
            user_portrait_img_btn = (ImageView) v.findViewById(R.id.user_portrait_img_btn);
            feed_type_icon_container = (RelativeLayout) v.findViewById(R.id.feed_type_icon_container);
            umeng_comm_dialog_btn = (ImageView) v.findViewById(R.id.umeng_comm_dialog_btn);
            umeng_comm_favorites_textview = (TextView) v.findViewById(R.id.umeng_comm_favorites_textview);
            umeng_comm_msg_user_name = (TextView) v.findViewById(R.id.umeng_comm_msg_user_name);
            user_type_icon_container = (LinearLayout) v.findViewById(R.id.user_type_icon_container);
            umeng_comm_msg_text = (TextView) v.findViewById(R.id.umeng_comm_msg_text);
            umeng_comm_msg_location = (ImageView) v.findViewById(R.id.umeng_comm_msg_location);
            umeng_comm_distance = (TextView) v.findViewById(R.id.umeng_comm_distance);
            umeng_comm_msg_location_text = (TextView) v.findViewById(R.id.umeng_comm_msg_location_text);
            forward_image_gv_layout = (RelativeLayout) v.findViewById(R.id.forward_image_gv_layout);
            umeng_comm_forard_text_tv = (TextView) v.findViewById(R.id.umeng_comm_forard_text_tv);
            umeng_comm_msg_images_gv_viewstub = (ViewStub) v.findViewById(R.id.umeng_comm_msg_images_gv_viewstub);
            umeng_comm_feed_action_layout = (LinearLayout) v.findViewById(R.id.umeng_comm_feed_action_layout);
            umeng_comm_msg_time_tv = (TextView) v.findViewById(R.id.umeng_comm_msg_time_tv);
            umeng_comm_forward_btn = (LinearLayout) v.findViewById(R.id.umeng_comm_forward_btn);
            umeng_comm_forward_tv = (TextView) v.findViewById(R.id.umeng_comm_forward_tv);
            umeng_comm_like_btn = (LinearLayout) v.findViewById(R.id.umeng_comm_like_btn);
            umeng_comm_like_tv = (TextView) v.findViewById(R.id.umeng_comm_like_tv);
            umeng_comm_comment_btn = (LinearLayout) v.findViewById(R.id.umeng_comm_comment_btn);
            umeng_comm_comment_tv = (TextView) v.findViewById(R.id.umeng_comm_comment_tv);
            umeng_comm_msg_gridview = (GridView) v.findViewById(R.id.umeng_comm_msg_gridview);

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

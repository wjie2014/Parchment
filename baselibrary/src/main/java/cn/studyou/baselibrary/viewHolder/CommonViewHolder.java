package cn.studyou.baselibrary.viewHolder;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * 基本功能：通用ViewHolder
 * 创建：王杰
 * 创建时间：15/3/17
 * 邮箱：w489657152@gmail.com
 */
public class CommonViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    private List<Boolean> mChecked;

    public CommonViewHolder(Context context, ViewGroup parent, int layoutId, int position) {

        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);

    }

    public static CommonViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new CommonViewHolder(context, parent, layoutId, position);
        } else {
            CommonViewHolder commonViewHolder = (CommonViewHolder) convertView.getTag();
            commonViewHolder.mPosition = position;
            return commonViewHolder;
        }
    }

    public View getmConvertView() {
        return mConvertView;
    }

    /**
     * 获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public CommonViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置ImageView的值
     *
     * @param viewId
     * @param resId
     * @return
     */
    public CommonViewHolder setImageResource(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    /**
     * 设置ImageView的值
     *
     * @param viewId
     * @param url
     * @return
     */
    public CommonViewHolder setImageURL(int viewId, String url) {
        ImageView imageView = getView(viewId);
//        ImageLoader.getInstance().displayImage(url, imageView);
        return this;
    }

    public CommonViewHolder onClickListener(int viewId, List<Boolean> checked) {
        this.mChecked = checked;
        final CheckBox checkBox = getView(viewId);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mChecked.set(mPosition, b);
            }
        });
        return this;
    }

    public CommonViewHolder setCheckBox(int viewId, boolean b) {
        final CheckBox checkBox = getView(viewId);
        checkBox.setChecked(b);
        return this;
    }

    public List<Boolean> getmChecked() {
        return mChecked;
    }
}

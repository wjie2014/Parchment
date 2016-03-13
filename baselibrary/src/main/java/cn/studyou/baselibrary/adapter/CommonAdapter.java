package cn.studyou.baselibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.studyou.baselibrary.viewHolder.CommonViewHolder;

/**
 * 基本功能：通用Adapter
 * 创建：王杰
 * 创建时间：15/3/17
 * 邮箱：w489657152@gmail.com
 */
public abstract class CommonAdapter<T> extends android.widget.BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    protected int layoutId;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.layoutId = layoutId;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder commonViewHolder = CommonViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(commonViewHolder, getItem(position));
        return commonViewHolder.getmConvertView();
    }

    public abstract void convert(CommonViewHolder holder, T t);


}

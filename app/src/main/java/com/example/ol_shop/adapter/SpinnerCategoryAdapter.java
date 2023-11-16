package com.example.ol_shop.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ol_shop.R;
import com.example.ol_shop.model.Category;

import java.util.List;

public class SpinnerCategoryAdapter extends BaseAdapter {
    List<Category> categoryList;

    public SpinnerCategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        Category objCategory = categoryList.get(i);
        return objCategory;
    }

    @Override
    public long getItemId(int i) {
        Category objCategory = categoryList.get(i);
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView;
        if(view==null){
            itemView = View.inflate(viewGroup.getContext(), R.layout.layout_spinner,null);
        }else {
            itemView = view;
        }
        final Category objCategory = categoryList.get(i);
        final int _index = i;
        TextView tv_name = itemView.findViewById(R.id.tv_spin_name);
        tv_name.setText(objCategory.getName());
        return itemView;
    }
}

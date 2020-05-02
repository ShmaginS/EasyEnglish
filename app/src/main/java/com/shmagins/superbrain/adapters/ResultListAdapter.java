package com.shmagins.superbrain.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class ResultListAdapter extends BaseExpandableListAdapter {

    private List<String> groups;
    private Map<Integer, List<String>> children;

    public ResultListAdapter(List<String> groups, Map<Integer, List<String>> children) {
        this.groups = groups;
        this.children = children;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int i) {
        List ret = children.get(i);
        return ret == null ? 0 : ret.size();
    }

    @Override
    public Object getGroup(int i) {
        return groups.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return children.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i * 10000 + i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (view == null) {
            view = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        }
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(groups.get(i));
        textView.setTypeface(null, Typeface.BOLD);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (view == null) {
            view = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        }
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(children.get(i).get(i1));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}

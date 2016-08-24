package com.team7619.keepdoing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.team7619.keepdoing.R;
import com.team7619.keepdoing.entity.Item;
import com.team7619.keepdoing.entity.SpaceBean;
import com.team7619.keepdoing.myviews.CircleImage.RoundImageView;
import com.team7619.keepdoing.myviews.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends ArrayAdapter<SpaceBean> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();

    public FoldingCellListAdapter(Context context, List<SpaceBean> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get item for selected view
        SpaceBean item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell_item, parent, false);
            // binding view parts to view holder
            viewHolder.titleIcon = (RoundImageView) cell.findViewById(R.id.title_icon);
            viewHolder.name = (TextView) cell.findViewById(R.id.name);
            viewHolder.publishTime = (TextView) cell.findViewById(R.id.publish_time);
            viewHolder.articleTitle = (TextView) cell.findViewById(R.id.article_title);
            viewHolder.articleAbout = (TextView) cell.findViewById(R.id.article_about);
            viewHolder.articleTitleCon = (TextView) cell.findViewById(R.id.article_title_con);
            viewHolder.joinNum = (TextView) cell.findViewById(R.id.join_num);
            viewHolder.articleContent = (TextView) cell.findViewById(R.id.article_content);
            viewHolder.addComment = (ImageButton) cell.findViewById(R.id.add_comment);
            viewHolder.allComment = (ImageButton) cell.findViewById(R.id.all_comment);
            viewHolder.likeIt = (ImageButton) cell.findViewById(R.id.like_it);
            viewHolder.shareIt = (ImageButton) cell.findViewById(R.id.share_it);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder
        //viewHolder.titleIcon.setImageResource();
        viewHolder.name.setText(item.getName());
        viewHolder.publishTime.setText(item.getPublishTime());
        viewHolder.articleTitle.setText(item.getArticleTitle());
        viewHolder.articleAbout.setText(item.getArticleAbout());
        viewHolder.articleTitleCon.setText(String.valueOf(item.getArticleTitleCon()));
        viewHolder.joinNum.setText(item.getJoinNum());
        viewHolder.articleContent.setText(item.getArticleContent());
        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    // View lookup cache
    private static class ViewHolder {
        RoundImageView titleIcon;
        TextView name;
        TextView publishTime;
        TextView articleTitle;
        TextView articleAbout;
        TextView articleTitleCon;
        TextView joinNum;
        TextView articleContent;
        ImageButton addComment;
        ImageButton allComment;
        ImageButton likeIt;
        ImageButton shareIt;
    }
}

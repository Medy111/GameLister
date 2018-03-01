package com.gamelist.gamelist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamelist.gamelist.R;
import com.gamelist.gamelist.models.SearchItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<SearchItem> mExampleList;




/** ONCLICKLISTENER INTERFACE for List item ( method down below )    */
    /***************************************************************/

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    /***************************************************************/


    public SearchItemAdapter(Context context, ArrayList<SearchItem> exampleList) {
        mContext = context;
        mExampleList = exampleList;

    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.search_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        SearchItem currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String creatorName = currentItem.getCreator();
        int likeCount = currentItem.getLikeCount();

        holder.mTextViewCreator.setText(creatorName);
        holder.mTextViewLikes.setText("Likes: " + likeCount);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }


    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;


        /** Setting the Onclick on the Viewholder(which is the whole list item)**/

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mTextViewLikes = itemView.findViewById(R.id.text_view_likes);

            //Inserted Onclicklistener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // checks if there is a listener  & item has valid position. Why ?
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position); // calls method from interface
                        }
                    }
                }
            });

        }

    }

    public void filterList(ArrayList<SearchItem> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}
package com.gamelist.gamelist.models;




// A List Item inside Search Fragment containing :
// IMAGE
// Creator
// LIKES

public class SearchItem {
    private String mImageUrl;
    private String mCreator;
    private int mLikes;

    public SearchItem(String imageUrl, String creator, int likes) {
        mImageUrl = imageUrl;
        mCreator = creator;
        mLikes = likes;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCreator() {
        return mCreator;
    }

    public int getLikeCount() {
        return mLikes;
    }
}
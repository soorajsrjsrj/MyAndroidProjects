package com.example.sooraj.top20;

import android.widget.ImageView;

public class News {
    private String mtitle;

    public String getMurlToImage() {
        return murlToImage;
    }

    public void setMurlToImage(String murlToImage) {
        this.murlToImage = murlToImage;
    }

    /** Location of the earthquake */
    private String mdescription;

    /** Time of the earthquake */
    private String murlToImage;

    /** Website URL of the earthquake */
    private String mUrl;


    public News(String title, String description, String urlToImage,String url) {
        mtitle = title;
        mdescription = description;
        murlToImage = urlToImage;
        mUrl=url;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMdescription() {
        return mdescription;
    }

    public void setMdescription(String mdescription) {
        this.mdescription = mdescription;
    }


    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}

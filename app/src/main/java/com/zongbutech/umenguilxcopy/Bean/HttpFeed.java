package com.zongbutech.umenguilxcopy.Bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lixian on 2016/4/12.
 */
public class HttpFeed implements Serializable {

    public int commentsCount;
    public String content;
    public Date createdAt;
    public String creatorId;
    public CreatorInfo creatorInfo;
    public String id;
    public List<String> imageUrls;
    public Date latestActiveAt;
    public int likesCount;
    public String title;
    public Date updatedAt;

    public class CreatorInfo implements Serializable {
        public String username;
    }

}

package com.zongbutech.umenguilxcopy.Bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lixian on 2016/4/12.
 */
public class HttpUserInfo implements Serializable {

    public String username;
    public String email;
    public boolean emailVerified;
    public String id;
    public List<String> friendIds;
    public int followingCount;
    public int fansCount;
    public String iconUrl;
    public int gender;
    public Date updatedAt;
}

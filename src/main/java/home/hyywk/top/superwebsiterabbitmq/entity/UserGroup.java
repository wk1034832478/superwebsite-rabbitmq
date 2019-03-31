package home.hyywk.top.superwebsiterabbitmq.entity;

import java.util.ArrayList;
import java.util.List;

public class UserGroup {
    /**
     * 群id
      */
    private int id;
    /**
     * 用户session id群
     */
    private List<String> userSessionIds = new ArrayList<>();
    /**
     * 群创建者
     */
    private int createrId;
    /**
     * 群名称
     */
    @javax.validation.constraints.Min(2)
    @javax.validation.constraints.Max(10)
    private String groupName;

    public UserGroup(int createrId,int id, String groupName, List ids) {
        this.createrId = createrId ;
        this.userSessionIds = ids ;
        this.id = id;
        this.groupName = groupName;
    }

    public UserGroup(int createrId,int id ) {
        this.createrId = createrId ;
        this.id = id;
    }

    public UserGroup() {}

    public void setCreaterId(int createrId) {
        this.createrId = createrId;
    }

    public int getCreaterId() {
        return createrId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserSessionIds(List<String> userSessionIds) {
        this.userSessionIds = userSessionIds;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getId() {
        return id;
    }

    public List<String> getUserSessionIds() {
        return userSessionIds;
    }
}

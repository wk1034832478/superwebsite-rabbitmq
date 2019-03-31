package home.hyywk.top.superwebsiterabbitmq.entity;

public interface MessageType {
    int OneToOne = 1;
    int OneToMany = 2;
    int BROADCAST = 3; // 针对某个集合进行广播，可以是几个人  一个或者几个群
    int BROADCAST_ALL = 4;

}

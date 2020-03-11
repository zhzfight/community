package life.majiang.community.enums;

import life.majiang.community.model.Notification;

public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}

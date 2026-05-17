package model;

import java.util.Date;

public class WatchHistory {
    private int historyId;
    private int userId;
    private int contentId;
    private int watchTime;
    private Date watchedAt;

    public WatchHistory(int historyId, int userId, int contentId, int watchTime, Date watchedAt) {
        this.historyId = historyId;
        this.userId = userId;
        this.contentId = contentId;
        this.watchTime = watchTime;
        this.watchedAt = watchedAt;
    }

    public int getUserId() { return userId; }
    public int getContentId() { return contentId; }
}
package model;

public class Content {
    private int contentId;
    private String title;
    private String description;
    private int releaseYear;
    private String contentType;
    private String ageRating;
    private String language;
    private int durationMinutes;

    public Content(int contentId, String title, String description, int releaseYear, String contentType, String ageRating, String language, int durationMinutes) {
        this.contentId = contentId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.contentType = contentType;
        this.ageRating = ageRating;
        this.language = language;
        this.durationMinutes = durationMinutes;
    }

    public int getContentId() { return contentId; }
    public String getTitle() { return title; }
    public int getReleaseYear() { return releaseYear; }
    public String getContentType() { return contentType; }
}
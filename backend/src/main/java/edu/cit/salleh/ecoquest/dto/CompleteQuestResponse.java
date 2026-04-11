package edu.cit.salleh.ecoquest.dto;

public class CompleteQuestResponse {
    private Long questId;
    private String questTitle;
    private Integer pointsEarned;
    private Integer totalPoints;
    private String completedAt;

    public CompleteQuestResponse(Long questId, String questTitle,
                                 Integer pointsEarned, Integer totalPoints,
                                 String completedAt) {
        this.questId = questId;
        this.questTitle = questTitle;
        this.pointsEarned = pointsEarned;
        this.totalPoints = totalPoints;
        this.completedAt = completedAt;
    }

    public Long getQuestId() { return questId; }
    public void setQuestId(Long questId) { this.questId = questId; }

    public String getQuestTitle() { return questTitle; }
    public void setQuestTitle(String questTitle) { this.questTitle = questTitle; }

    public Integer getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(Integer pointsEarned) { this.pointsEarned = pointsEarned; }

    public Integer getTotalPoints() { return totalPoints; }
    public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }

    public String getCompletedAt() { return completedAt; }
    public void setCompletedAt(String completedAt) { this.completedAt = completedAt; }
}
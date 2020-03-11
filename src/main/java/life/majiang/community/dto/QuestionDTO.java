package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private String tags;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}

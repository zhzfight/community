package life.majiang.community.model;

public class History {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORY.ID
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORY.USER_ID
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORY.QUESTION_ID
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    private Long questionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORY.TITLE
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORY.WHEN
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    private Long when;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORY.ID
     *
     * @return the value of HISTORY.ID
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORY.ID
     *
     * @param id the value for HISTORY.ID
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORY.USER_ID
     *
     * @return the value of HISTORY.USER_ID
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORY.USER_ID
     *
     * @param userId the value for HISTORY.USER_ID
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORY.QUESTION_ID
     *
     * @return the value of HISTORY.QUESTION_ID
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORY.QUESTION_ID
     *
     * @param questionId the value for HISTORY.QUESTION_ID
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORY.TITLE
     *
     * @return the value of HISTORY.TITLE
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORY.TITLE
     *
     * @param title the value for HISTORY.TITLE
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORY.WHEN
     *
     * @return the value of HISTORY.WHEN
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    public Long getWhen() {
        return when;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORY.WHEN
     *
     * @param when the value for HISTORY.WHEN
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    public void setWhen(Long when) {
        this.when = when;
    }
}
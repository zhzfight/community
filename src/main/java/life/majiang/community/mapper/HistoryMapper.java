package life.majiang.community.mapper;

import java.util.List;
import life.majiang.community.model.History;
import life.majiang.community.model.HistoryExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface HistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HISTORY
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    long countByExample(HistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HISTORY
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    int deleteByExample(HistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HISTORY
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    int insert(History record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HISTORY
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    int insertSelective(History record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HISTORY
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    List<History> selectByExampleWithRowbounds(HistoryExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HISTORY
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    List<History> selectByExample(HistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HISTORY
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    int updateByExampleSelective(@Param("record") History record, @Param("example") HistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HISTORY
     *
     * @mbg.generated Sun Mar 08 15:58:12 CST 2020
     */
    int updateByExample(@Param("record") History record, @Param("example") HistoryExample example);
}
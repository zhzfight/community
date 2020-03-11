package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.dto.QuestionQueryDTO;
import life.majiang.community.enums.SortEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import life.majiang.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(String search, Integer page, Integer size, String tag, String sort) {

        if (StringUtils.isNotBlank(search)) {
            String[] words = StringUtils.split(search, ",");
            search = Arrays.stream(words).filter(StringUtils::isNotBlank)
                    .map(word -> word.replace("+", "")
                            .replace("*", "")
                            .replace("?", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
        }

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replace("+", "")
                    .replace("*", "")
                    .replace("?", "");
            questionQueryDTO.setTag(tag);
        }

        for (SortEnum sortEnum : SortEnum.values()) {
            if (sortEnum.name().toLowerCase().equals(sort)) {
                questionQueryDTO.setSort(sort);
                if (sortEnum == SortEnum.HOT7) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
                }
                if (sortEnum == SortEnum.HOT30) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
                }
                break;
            }
        }

        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
        Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
        Integer offset = dataOffset(totalPage, page, size);


        questionQueryDTO.setSize(size);
        questionQueryDTO.setOffset(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        PaginationDTO<QuestionDTO> paginationDTO = QuestionListLoadToPaginationDTO(questions, totalPage, page);
        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {//profilecontroller里的我的提问栏目调用了这个方法
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andIdEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
        Integer offset = dataOffset(totalPage, page, size);


        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        PaginationDTO<QuestionDTO> paginationDTO = QuestionListLoadToPaginationDTO(questions, totalPage, page);

        return paginationDTO;
    }


    public QuestionDTO getById(Long id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = assambleQuestionAndUserToQusetionDTO(question);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        } else {
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());

            int updated = questionMapper.updateByExampleSelective(question, questionExample);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTags())) {
            return new ArrayList<>();
        }
        String regexpTag = regexp(queryDTO.getTags(), ",");

        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTags(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }


    private String regexp(String text, String separatorChars) {
        String[] words = StringUtils.split(text, ",");
        String regexp = Arrays.stream(words).collect(Collectors.joining("|"));
        return regexp;
    }

    private Integer dataOffset(Integer totalPage, Integer page, Integer size) {
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        return page <= 1 ? 0 : (page - 1) * size;
    }

    private PaginationDTO QuestionListLoadToPaginationDTO(List<Question> questions, Integer totalPage, Integer page) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            questionDTOList.add(assambleQuestionAndUserToQusetionDTO(question));
        }
        paginationDTO.setData(questionDTOList);
        paginationDTO.setPagination(totalPage, page);
        return paginationDTO;
    }

    private QuestionDTO assambleQuestionAndUserToQusetionDTO(Question question) {
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }
}

package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.mapper.HistoryMapper;
import life.majiang.community.model.History;
import life.majiang.community.model.HistoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    public void insert(History history) {
        HistoryExample historyExample=new HistoryExample();
        historyExample.createCriteria()
                .andUserIdEqualTo(history.getId())
                .andQuestionIdEqualTo(history.getQuestionId());
        List<History> histories = historyMapper.selectByExample(historyExample);
        if(histories.size()==0){
            historyMapper.insert(history);
        }
        else{
            historyMapper.updateByExample(history,historyExample);
        }
    }


}



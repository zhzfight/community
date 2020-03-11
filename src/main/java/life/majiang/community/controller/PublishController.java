package life.majiang.community.controller;

import life.majiang.community.cache.TagCache;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;


    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("offeredTags", TagCache.get());
        return "publish";
    }


    @PostMapping("/publish")
    public String doPusblish(@RequestParam(value = "title", required = false) String title,
                             @RequestParam(value = "description", required = false) String description,
                             @RequestParam(value = "tags", required = false) String tags,
                             @RequestParam(value = "id", required = false) Long id,
                             HttpServletRequest request,
                             Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tags", tags);
        if (title == "" || title == null) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == "" || description == null) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tags == "" || tags == null) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tags);
        if (StringUtils.isNoneBlank(invalid)) {
            model.addAttribute("error", "输入非法标签");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTags(tags);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tags", question.getTags());
        model.addAttribute("id", question.getId());
        model.addAttribute("offeredTags", TagCache.get());
        return "publish";
    }
}

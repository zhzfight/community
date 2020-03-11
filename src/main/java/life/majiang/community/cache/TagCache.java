package life.majiang.community.cache;

import life.majiang.community.dto.TagsDTO;
import org.apache.commons.lang3.StringUtils;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagsDTO> get(){
        ArrayList<TagsDTO> tagsDTOS = new ArrayList<>();
        TagsDTO program = new TagsDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c++", "c", "golang", "objective-c", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagsDTOS.add(program);

        TagsDTO framework = new TagsDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagsDTOS.add(framework);


        TagsDTO server = new TagsDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存 tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagsDTOS.add(server);

        TagsDTO db = new TagsDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql memcached", "sqlserver", "postgresql", "sqlite"));
        tagsDTOS.add(db);

        TagsDTO tool = new TagsDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom emacs", "textmate", "hg"));
        tagsDTOS.add(tool);
        return tagsDTOS;
    }
    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagsDTO> tagsDTOS = get();
        List<String> tagsList = tagsDTOS.stream().flatMap(tagsDTO -> tagsDTO.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagsList.contains(t)).collect(Collectors.joining(","));
        return invalid;


    }
}

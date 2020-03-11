package life.majiang.community.advice;


import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/*
加上ControllerAdvice注解后，可以实现全局的异常处理，ExceptionHander则是对不同的异常的自定义处理，
modelandview类似controller注解下的返回string，进行页面跳转
请求json数据的时候如果发生错误是不用跳转到error页面的，只需要返回一个封装了resultDTO的JSON数据就可以了
但是如果请求html文件时发生错误，那么就需要跳转到error页面
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {//请求json数据
            ResultDTO resultDTO;
            if (e instanceof CustomizeException) {//如果是CustomizeException的话，那么把e强转为CustomizeException
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");//写返回体
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {

            }
            return null;
        } else {//请求文本或html数据
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", "服务冒烟了，要不你稍后再试试");
            }
            return new ModelAndView("error");
        }

    }


}

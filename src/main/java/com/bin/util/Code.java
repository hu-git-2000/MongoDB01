package com.bin.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Code {

    public void getCode(HttpSession session, HttpServletResponse response) throws IOException {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 100, 1, 5);
        String code = circleCaptcha.getCode();  //拿到验证码
        session.setAttribute("code",code);
        //将验证码图片写出去
        circleCaptcha.write(response.getOutputStream());
    }

}

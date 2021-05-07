package com.team_musible.musible;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Test {
    @RequestMapping("/TEST")
    @ResponseBody
    public String index() {
        return "Spring test";
    }
}

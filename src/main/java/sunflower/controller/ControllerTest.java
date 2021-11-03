package sunflower.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RefreshScope
@RequestMapping
public class ControllerTest {

    @ResponseBody
    @PostMapping
    public String gatewayTest() {
        return "welcome to sunflower gateway service";
    }

    @GetMapping("/index")
    public String index(){
        return "_in";
    }
}

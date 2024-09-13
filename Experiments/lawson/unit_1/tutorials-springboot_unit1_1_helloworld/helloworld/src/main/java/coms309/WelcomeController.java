package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {


    //generated when your search in localhost has nothing after the numbers or
    //the optional '/'
    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    //generated when something is specified after the numbers and the '/', and will
    //display the text after the dash as a name for the greeting
    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }


}

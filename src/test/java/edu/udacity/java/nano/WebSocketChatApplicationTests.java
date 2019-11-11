package edu.udacity.java.nano;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.session.FirefoxFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class WebSocketChatApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Test
    public void contextLoads(){}

    @Test
    public void displayLoginPage() throws Exception {

        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testDisplayChatRoomPage() throws Exception {
        this.mockMvc.perform(get("/index?")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Chat Room")))
                .andExpect(view().name("chat"));
    }
}

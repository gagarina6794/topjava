package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.UserTestData.*;

class AdminAjaxControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/ajax/admin/users/";

    @Test
    void setStatus() throws Exception {
        User updated = new User(USER);
        updated.setEnabled(false);
        mockMvc.perform(post(REST_URL + USER_ID)
            .param("state", "false"))
            .andExpect(status().isOk())
            .andDo(print());

        assertMatch(userService.get(USER_ID), updated);
    }
}
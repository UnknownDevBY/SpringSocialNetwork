package com.network.test;

import com.network.controller.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@SpringBootTest
public class SmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired private AddPhotoController addPhotoController;
    @Autowired private CommentController commentController;
    @Autowired private CommunitiesController communitiesController;
    @Autowired private ConversationController conversationController;
    @Autowired private CreateCommunityController createCommunityController;
    @Autowired private DeleteController deleteController;
    @Autowired private EditController editController;
    @Autowired private FriendsController friendsController;
    @Autowired private LikesController likesController;
    @Autowired private MessageController messageController;
    @Autowired private ModifyUserController modifyUserController;
    @Autowired private NewsController newsController;
    @Autowired private PhotoController photoController;
    @Autowired private PrivacySettingsController privacySettingsController;
    @Autowired private RegistrationController registrationController;
    @Autowired private SearchController searchController;
    @Autowired private SubscribeController subscribeController;
    @Autowired private UserPageController userPageController;

    @Test
    public void contextLoads() {
        assertNotNull(addPhotoController);
        assertNotNull(commentController);
        assertNotNull(communitiesController);
        assertNotNull(conversationController);
        assertNotNull(createCommunityController);
        assertNotNull(deleteController);
        assertNotNull(editController);
        assertNotNull(friendsController);
        assertNotNull(likesController);
        assertNotNull(messageController);
        assertNotNull(modifyUserController);
        assertNotNull(newsController);
        assertNotNull(photoController);
        assertNotNull(privacySettingsController);
        assertNotNull(registrationController);
        assertNotNull(searchController);
        assertNotNull(subscribeController);
        assertNotNull(userPageController);
    }

    @Test
    public void testLinks() throws Exception {
        testLink("/", "index");
        testLink("/search", "search");
        testLink("/registration", "registration");
    }

    @Test
    @WithMockUser
    public void testMocked() throws Exception {
        testLink("/create-community", "createGroup");
    }

    @Test
    @WithUserDetails("vlade")
    public void testAuthenticated() throws Exception {
        testLink("/news", "news");
        testLink("/communities", "myGroups");
    }

    private void testLink(String url, String viewName) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));
    }
}

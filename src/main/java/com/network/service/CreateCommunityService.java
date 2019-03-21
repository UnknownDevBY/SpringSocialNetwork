package com.network.service;

import com.network.model.Community;
import com.network.model.User;

public interface CreateCommunityService {

    void createCommunity(Community community, User currentUser);
}

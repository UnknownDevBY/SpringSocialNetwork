package com.network.service.impl;

import com.network.model.Friendship;
import com.network.model.User;
import com.network.repository.FriendshipRepository;
import com.network.service.ModifyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyUserServiceImpl implements ModifyUserService {

    @Autowired private FriendshipRepository friendshipRepository;

    @Override
    public void modifyRelation(User currentUser, User pageUser) {
        Friendship relation = friendshipRepository.getByFromAndTo(currentUser, pageUser);
        if(relation == null) {
            relation = new Friendship();
            relation.setFrom(currentUser);
            relation.setTo(pageUser);
            friendshipRepository.save(relation);
        }
        else friendshipRepository.delete(relation);
    }
}

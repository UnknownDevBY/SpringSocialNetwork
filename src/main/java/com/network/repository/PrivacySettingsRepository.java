package com.network.repository;

import com.network.model.PrivacySettings;
import com.network.model.User;
import org.springframework.data.repository.CrudRepository;

public interface PrivacySettingsRepository extends CrudRepository<PrivacySettings, Integer> {

    PrivacySettings getByUser(User user);
}

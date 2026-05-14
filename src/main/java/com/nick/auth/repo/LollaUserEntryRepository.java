package com.nick.auth.repo;

import com.nick.auth.entity.LollaUserEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LollaUserEntryRepository extends JpaRepository<LollaUserEntry, Long> {
}

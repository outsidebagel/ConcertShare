package com.nick.auth.repo;

import com.nick.auth.entity.TahoeLiveUserEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TahoeLiveUserEntryRepository extends JpaRepository<TahoeLiveUserEntry, Long> {
}

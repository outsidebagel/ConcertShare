package com.nick.auth.repo;

import com.nick.auth.entity.NorthCoastUserEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NorthCoastUserEntryRepository extends JpaRepository<NorthCoastUserEntry, Long> {
}

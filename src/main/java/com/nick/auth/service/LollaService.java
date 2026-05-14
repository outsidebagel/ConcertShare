package com.nick.auth.service;

import com.nick.auth.entity.LollaUserEntry;
import com.nick.auth.entity.LollaUserEntry;
import com.nick.auth.entity.UserEntryType;
import com.nick.auth.repo.LollaUserEntryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class LollaService {
    @Value("${gallery.photo.base-path}")
    private String basePath;
    private final LollaUserEntryRepository repo;
    public LollaService(LollaUserEntryRepository repo) {
        this.repo = repo;
    }


    public List<LollaUserEntry> findAll() {
        List<LollaUserEntry> entries = repo.findAll();
        entries.sort(new Comparator<LollaUserEntry>() {
            @Override
            public int compare(LollaUserEntry a, LollaUserEntry b) {
                if (a.getUploadedAt().isBefore(b.getUploadedAt())) return 1;
                if (a.getUploadedAt().isAfter(b.getUploadedAt())) return -1;
                return 0;
            }
        });
        return entries;
    }

    public LollaUserEntry findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Transactional
    public LollaUserEntry uploadPhoto(String title, String description, MultipartFile file) throws IOException {
        LollaUserEntry entity = new LollaUserEntry();
        entity.setTitle(title);
        entity.setEntryType(UserEntryType.PHOTO);
        entity.setDescription(description);
        entity.setFileName(file.getOriginalFilename());
        entity.setUploadedAt(LocalDateTime.now());
        entity.setRelativePath("/gallery/" + file.getOriginalFilename()); //
// save metadata
        repo.save(entity);
// ensure folder exists
        Path finalDir = Paths.get(basePath, "gallery");
        Files.createDirectories(finalDir);
// save the file itself
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, finalDir.resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        return entity;
    }

    @Transactional
    public LollaUserEntry uploadVideo(String title, String description, String URL) throws IOException {
        LollaUserEntry entity = new LollaUserEntry();
        entity.setTitle(title);
        entity.setEntryType(UserEntryType.VIDEO);
        entity.setDescription(description);
        entity.setVideoURL(URL);
        entity.setUploadedAt(LocalDateTime.now());
// save metadata
        repo.save(entity);

        return entity;
    }
}

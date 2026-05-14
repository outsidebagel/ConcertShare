package com.nick.auth.service;

import com.nick.auth.entity.TahoeLiveUserEntry;
import com.nick.auth.entity.UserEntryType;
import com.nick.auth.repo.TahoeLiveUserEntryRepository;
import com.nick.auth.repo.TahoeLiveUserEntryRepository;
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
public class TahoeLiveService {
    @Value("${gallery.photo.base-path}")
    private String basePath;
    private final TahoeLiveUserEntryRepository repo;
    public TahoeLiveService(TahoeLiveUserEntryRepository repo) {
        this.repo = repo;
    }

    public List<TahoeLiveUserEntry> findAll() {
        List<TahoeLiveUserEntry> entries = repo.findAll();
        entries.sort(new Comparator<TahoeLiveUserEntry>() {
            @Override
            public int compare(TahoeLiveUserEntry a, TahoeLiveUserEntry b) {
                if (a.getUploadedAt().isBefore(b.getUploadedAt())) return 1;
                if (a.getUploadedAt().isAfter(b.getUploadedAt())) return -1;
                return 0;
            }
        });
        return entries;
    }

    public TahoeLiveUserEntry findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Transactional
    public TahoeLiveUserEntry uploadPhoto(String title, String description, MultipartFile file) throws IOException {
        TahoeLiveUserEntry entity = new TahoeLiveUserEntry();
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
    public TahoeLiveUserEntry uploadVideo(String title, String description, String URL) throws IOException {
        TahoeLiveUserEntry entity = new TahoeLiveUserEntry();
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

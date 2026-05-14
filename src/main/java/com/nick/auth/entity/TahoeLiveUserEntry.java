package com.nick.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tahoelive")
public class TahoeLiveUserEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable=false, length=200)
    private String title;
    @Column(length=255)
    private String description;
    @Column(length=255)
    private UserEntryType entryType;

    @Column(nullable=true, length=255)
    private String fileName;
    @Column(nullable=true, length=255)
    private String relativePath;

    @Column(nullable=true, length=255)
    private String videoURL;

    @Column
    private LocalDateTime uploadedAt = LocalDateTime.now();
}

package com.example.demo.application.usecase.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.util.error.IdInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserCoverPhotoUseCase {
    private final UserRepository userRepository;

    public String execute(Long userId, String originalFilename, java.io.InputStream inputStream)
            throws IOException, IdInvalidException {
        String cleanedFilename = originalFilename != null ? originalFilename.replaceAll("\\s+", "") : "unknown.jpg";
        String uniqueFileName = UUID.randomUUID().toString() + "_" + cleanedFilename;

        String rootDir = System.getProperty("user.dir");
        Path coverPhotoDir = Paths.get(rootDir, "uploads", "cover_photo");
        if (!Files.exists(coverPhotoDir)) {
            Files.createDirectories(coverPhotoDir);
        }
        Path targetPath = coverPhotoDir.resolve(uniqueFileName);
        Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);

        String coverPhotoUrl = "http://localhost:8080/uploads/cover_photo/" + uniqueFileName;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IdInvalidException("User với id = " + userId + " không tồn tại"));
        user.setCoverPhoto(coverPhotoUrl);
        userRepository.save(user);

        return coverPhotoUrl;
    }
}

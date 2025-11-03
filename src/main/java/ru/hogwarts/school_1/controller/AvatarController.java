package ru.hogwarts.school_1.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school_1.model.Avatar;
import ru.hogwarts.school_1.service.AvatarService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    private final AvatarService avatarService;
    private final String avatarsDir = "/path/to/avatarsDir";

    @GetMapping
    public ResponseEntity<List<Avatar>> getAllAvatar(@RequestParam("page") Integer pageNumber,
                                                     @RequestParam("size") Integer pageSize) {
        List<Avatar> avatars = avatarService.getAllAvatar(pageSize, pageNumber);
        return ResponseEntity.ok(avatars);
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar)
            throws IOException {

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(avatar.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, avatar.getBytes(), StandardOpenOption.CREATE_NEW);

        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{studentId}/db-avatar", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getAvatarFromDB(@PathVariable Long studentId) throws IOException {
        Optional<Avatar> avatarOptional = avatarService.getAvatarByStudentId(studentId);
        if (!avatarOptional.isPresent()) {
            throw new NoSuchElementException("No avatar found for student with ID: " + studentId);
        }
        Avatar avatar = avatarOptional.get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .body(avatar.getData());
    }

    @GetMapping(value = "/{studentId}/file-system-avatar", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getAvatarFromFS(@PathVariable Long studentId) throws MalformedURLException, FileNotFoundException {
        Resource resource = new UrlResource(Path.of(avatarsDir, studentId + ".jpg").toUri());
        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            throw new FileNotFoundException("Could not read the file");
        }
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
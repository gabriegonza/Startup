package br.com.satrtup.Startup.service.file;

import br.com.satrtup.Startup.config.FileStorageConfig;
import br.com.satrtup.Startup.domain.vo.UploadFileResponseVO;
import br.com.satrtup.Startup.exception.file.FileExceptionNotFound;
import br.com.satrtup.Startup.exception.file.FileExeption;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final Path fileStorageLocation;

    @Autowired
    public FileService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();

        this.fileStorageLocation = path;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileExeption(
                    "Could not create the directory where the uploaded files will be stored!", e);
        }
    }

    public UploadFileResponseVO uploadFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (filename.contains("..")) {
                throw new FileExeption(
                        "Sorry! Filename contains invalid path sequence " + filename);
            }
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/file/downloadFile/")
                    .path(filename)
                    .toUriString();
            return new UploadFileResponseVO(filename, fileDownloadUri, file.getContentType(), file.getSize());
        } catch (Exception e) {
            throw new FileExeption(
                    "Could not store file " + filename + ". Please try again!", e);
        }
    }

    public List<UploadFileResponseVO> uploadMultipleFiles(MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    public ResponseEntity<Resource> downloadFile(String filename, HttpServletRequest request) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new FileExceptionNotFound("File not found");
            }
        } catch (Exception e) {
            throw new FileExceptionNotFound("File not found" + filename, e);
        }
    }
}
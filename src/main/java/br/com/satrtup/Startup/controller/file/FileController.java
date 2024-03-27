package br.com.satrtup.Startup.controller.file;


import br.com.satrtup.Startup.domain.vo.UploadFileResponseVO;
import br.com.satrtup.Startup.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "File Endpoint", description = "Endpoint created for studying how to add files in a Java application.")
@RestController
@RequestMapping("/api/file")
public class FileController {
	@Autowired
	private FileService service;

	@PostMapping("/upload")
	@Operation(summary = "Adds a new File",
			description = "Adds a new File!",
			tags = {"File"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200",
							content = @Content(schema = @Schema(implementation = UploadFileResponseVO.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
		return service.uploadFile(file);
	}

	@PostMapping("/upload-multi")
	@Operation(summary = "Adds a new multiples File",
			description = "Adds a new multiples File!",
			tags = {"File"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200",
							content = @Content(schema = @Schema(implementation = UploadFileResponseVO.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return service.uploadMultipleFiles(files);
	}

	@GetMapping("/download-file/{filename:.+}")
	@Operation(summary = "Download a File",
			description = "Download a File!",
			tags = {"File"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200",
							content = @Content(schema = @Schema(implementation = UploadFileResponseVO.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) {
		return service.downloadFile(filename, request);
	}
}
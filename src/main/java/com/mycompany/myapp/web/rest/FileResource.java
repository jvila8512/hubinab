package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.File;
import com.mycompany.myapp.domain.Response;
import com.mycompany.myapp.service.FileServiceAPI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class FileResource {

    @Autowired
    private FileServiceAPI fileServiceAPI;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "file";

    // public ResponseEntity<Response> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws Exception
    @PostMapping("/file/upload")
    public ResponseEntity<Response> uploadFiles(@RequestParam("files") MultipartFile files) throws Exception {
        fileServiceAPI.save(files);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, "Foto"))
            .body(new Response("El archivo fue cargado correctamente"));
        // return ResponseEntity.ok()
        //  .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false,ENTITY_NAME, "Los archivos fueron cargados correctamente al servidor ok")
        //  ;

        //ResponseEntity.status(HttpStatus.OK).body(new Response("Los archivos fueron cargados correctamente al servidor ok"));
    }

    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<String> getFile(@PathVariable String filename) throws Exception {
        String resource = fileServiceAPI.load(filename);
        return ResponseEntity.ok().body(resource);
        // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
    }

    @GetMapping("/file/all")
    public ResponseEntity<List<File>> getAllFiles() throws Exception {
        List<File> files = fileServiceAPI
            .loadAll()
            .map(path -> {
                String filename = path.getFileName().toString();
                String url = MvcUriComponentsBuilder
                    .fromMethodName(FileResource.class, "getFile", path.getFileName().toString())
                    .build()
                    .toString();

                return new File(filename, url);
            })
            .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/file/url/{op}")
    public String getAllURL(Long op) throws Exception {
        String ruta = fileServiceAPI.getUploadsFolderPathResource(op);

        return ruta;
    }

    @GetMapping("/file/url1/")
    public String getAllURL() throws Exception {
        String ruta = fileServiceAPI.getUploadsFolderPathResource1();

        return ruta;
    }

    @DeleteMapping("/file/delete/{filename:.+}")
    public ResponseEntity<Void> deleteFile(@PathVariable String filename) throws Exception {
        fileServiceAPI.delete(filename);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, "Foto borrada"))
            .build();
    }
}

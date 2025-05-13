package com.mycompany.myapp.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceAPI {
    public void save(MultipartFile file) throws Exception;

    public String getUploadsFolderPathResource(Long op) throws IOException;

    public String getUploadsFolderPathResource1() throws IOException;

    public String load(String name) throws Exception;

    //public void save(List<MultipartFile> files) throws Exception;

    public Stream<Path> loadAll() throws Exception;

    public void delete(String name) throws Exception;
}

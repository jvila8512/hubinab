package com.mycompany.myapp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileServiceAPI {

    //private final ServletContext servletContext;
    // private final String rootFolder1;

    private Path rootFolder;
    //private  Path rootFolder=Paths.get("src/main/webapp/content/uploads") ;
    //private  Path rootFolder=Paths.get("WEB-INF/classes/static/content/uploads") ;

    //private  Path rootFolder=Paths.get("/root/tomcat/webapps/innovacrece/WEB-INF/classes/static/content/uploads/") ;

    ///root/tomcat/webapps/innovacrece/WEB-INF/classes/static/content/uploads/
    //public void save(List<MultipartFile> files) throws Exception {
    //  for (MultipartFile file : files) {
    //      this.save(file);
    //  }
    // }

    private final ResourceLoader resourceLoader;

    @Autowired
    public FileServiceImpl(ResourceLoader resourceLoader) throws IOException {
        this.resourceLoader = resourceLoader;
        Resource resource = resourceLoader.getResource("classpath:static/content/uploads/");
        // String uploadsFolderPath1 = resource.getFile().getAbsolutePath();

        // PathResource pathResource = new PathResource(resourceLoader.getResource("classpath:static/content/uploads/").getFile().toPath());

        rootFolder = resource.getFile().toPath();
    }

    @Override
    public String getUploadsFolderPathResource1() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/content/uploads/");
        String uploadsFolderPath1 = resource.getFile().getAbsolutePath();

        // PathResource pathResource = new PathResource(resourceLoader.getResource("classpath:static/content/uploads/").getFile().toPath());

        // Path rootFolder111=resource.getFile().toPath();

        return uploadsFolderPath1;
    }

    @Override
    public String getUploadsFolderPathResource(Long op) throws IOException {
        Path rootFolder1 = Paths.get("static/content/uploads/");
        //   Path rootFolder1=Paths.get("src/main/webapp/content") ;

        // src\main\webapp\content

        return rootFolder.toAbsolutePath().toString();
    }

    // @Autowired
    // public FileServiceImpl(ServletContext servletContext) {
    //  this.servletContext = servletContext;
    //this.rootFolder1 = servletContext.getRealPath("\\WEB-INF\\classes\\static\\content\\uploads");
    //  this.rootFolder1 = servletContext.getRealPath("\\uploads");

    // }

    @Override
    public void save(MultipartFile file) throws Exception {
        Files.copy(file.getInputStream(), this.rootFolder.resolve(file.getOriginalFilename()));
    }

    @Override
    public String load(String name) throws Exception {
        Path file = rootFolder.resolve(name);
        FileInputStream fin = new FileInputStream(file.toFile());
        byte imagebytearray[] = fin.readAllBytes();
        fin.read(imagebytearray);
        String imagetobase64 = Base64.getEncoder().encodeToString(imagebytearray);

        return imagetobase64;
    }

    @Override
    public Stream<Path> loadAll() throws Exception {
        return Files.walk(rootFolder, 1).filter(path -> !path.equals(rootFolder)).map(rootFolder::relativize);
    }

    @Override
    public void delete(String name) throws Exception {
        Files.delete(this.rootFolder.resolve(name));
    }
}

package com.carrentalproject.service;


import com.carrentalproject.domain.FileDB;
import com.carrentalproject.repository.FileDBRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FileDBService {

    private final FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file) throws IOException{
        String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())); //dosyadan file ismini cekmek icin
        FileDB fileDB=new FileDB(fileName,file.getContentType(), file.getBytes()); //array olarak atadik
        fileDBRepository.save(fileDB); //file i db ye kaydettik
        return fileDB;
    }

    public FileDB getFile(String id){

        return fileDBRepository.findById(id).get();//file i getircez
    }

    public Stream<FileDB> getAllFiles(){

        return fileDBRepository.findAll().stream();//tum file lari getircez
    }

}

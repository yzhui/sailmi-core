package com.sailmi.core.oss.provider.mongooss.impl;

import com.sailmi.core.oss.common.domain.model.FileObject;
import com.sailmi.core.oss.common.domain.model.FileObjectHistory;
import com.sailmi.core.oss.common.domain.request.FileObjectSaveRequest;
import com.sailmi.core.oss.common.domain.request.FileObjectSearchRequest;
import com.sailmi.core.oss.common.domain.service.FileObjectService;
import com.sailmi.core.oss.provider.mongooss.repository.FileObjectHistoryRepository;
import com.sailmi.core.oss.provider.mongooss.repository.FileObjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileObjectServiceImpl implements FileObjectService {

    @Autowired
    private FileObjectHistoryRepository fileObjectHistoryRepository;

    @Autowired
    private FileObjectRepository fileObjectRepository;

    @Override
    public Optional<com.sailmi.core.oss.provider.mongooss.model.FileObject> findById(String id) {
        return fileObjectRepository.findById(id);
    }

    @Override
    public FileObject findByStoredFilename(String storedFileName) {
        return fileObjectRepository.findByStoredFilename(storedFileName);
    }

    @Override
    public List<FileObjectHistory> findHistoryByFileObjectId(String id) {
        return fileObjectHistoryRepository.findByFileObjectId(id)
                                          .stream()
                                          .map(item -> (FileObjectHistory) item)
                                          .collect(Collectors.toList());
    }

    @Override
    public FileObject save(FileObjectSaveRequest storeFileRequest) {
        com.sailmi.core.oss.provider.mongooss.model.FileObject fileObject = new com.sailmi.core.oss.provider.mongooss.model.FileObject();
        BeanUtils.copyProperties(storeFileRequest, fileObject, "id");
        return fileObjectRepository.save(fileObject);
    }

    @Override
    public FileObjectHistory saveAsHistory(FileObject fileObject) {
        com.sailmi.core.oss.provider.mongooss.model.FileObject mongodbFileObject = (com.sailmi.core.oss.provider.mongooss.model.FileObject) fileObject;

        com.sailmi.core.oss.provider.mongooss.model.FileObjectHistory result = com.sailmi.core.oss.provider.mongooss.model.FileObjectHistory
                .from(mongodbFileObject);

        return fileObjectHistoryRepository.save(result);
    }

    @Override
    public Optional<com.sailmi.core.oss.provider.mongooss.model.FileObject> deleteById(String id) {
        Optional<com.sailmi.core.oss.provider.mongooss.model.FileObject> result = fileObjectRepository.findById(id);
        if (result == null) {
            return null;
        }

        fileObjectHistoryRepository.deleteByFileObjectId(id);
        fileObjectRepository.deleteById(id);
        return result;
    }

    @Override
    public FileObject deleteByStoredFilename(String storedFileName) {
        com.sailmi.core.oss.provider.mongooss.model.FileObject result = fileObjectRepository.findByStoredFilename(storedFileName);
        if (result == null) {
            return null;
        }

        fileObjectHistoryRepository.deleteByFileObject(result);
        fileObjectRepository.delete(result);

        return result;
    }

    @Override
    public Page<FileObject> search(FileObjectSearchRequest searchRequest) {
        return fileObjectRepository.findPage( searchRequest)
                                   .map(item -> (FileObject) item);
    }
}

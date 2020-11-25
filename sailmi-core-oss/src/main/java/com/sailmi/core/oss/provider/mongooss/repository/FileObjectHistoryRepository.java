package com.sailmi.core.oss.provider.mongooss.repository;

import com.sailmi.core.oss.provider.mongooss.model.FileObject;
import com.sailmi.core.oss.provider.mongooss.model.FileObjectHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author dz
 */
public interface FileObjectHistoryRepository extends AbstractRepository<FileObjectHistory> {

    Page<FileObjectHistory> findByFileObject(FileObject fileObject, Pageable pageable);

    List<FileObjectHistory> findByFileObject(FileObject fileObject);

    List<FileObjectHistory> findByFileObjectId(String id);

    void deleteByFileObjectId(String id);

    void deleteByFileObject(FileObject result);
}

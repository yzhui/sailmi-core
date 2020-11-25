package com.sailmi.core.oss.provider.mongooss.repository;

import com.sailmi.core.oss.provider.mongooss.model.FileObject;
import com.sailmi.core.oss.provider.mongooss.repository.custom.FileObjectRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * @author dz
 */
public interface FileObjectRepository extends AbstractRepository<FileObject>, FileObjectRepositoryCustom {

    FileObject findByStoredFilename(String finalFilename);

    Page<FileObject> findByCategory(String category, Pageable pageable);

    Page<FileObject> findByUploadedBy(String uploadedBy, Pageable pageable);

    Page<FileObject> findByUploadedAtBetween(Date start, Date end, Pageable pageable);

    Page<FileObject> findByAttachedId(String attachedId, Pageable pageable);

}

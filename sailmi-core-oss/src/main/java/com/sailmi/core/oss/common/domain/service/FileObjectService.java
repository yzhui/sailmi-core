package com.sailmi.core.oss.common.domain.service;

import com.sailmi.core.oss.common.domain.model.FileObject;
import com.sailmi.core.oss.common.domain.model.FileObjectHistory;
import com.sailmi.core.oss.common.domain.request.FileObjectSaveRequest;
import com.sailmi.core.oss.common.domain.request.FileObjectSearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * The file object and the associated attributes is stored in the file storage server.
 * To improve the file object(not physical object) access performance,
 * we need the file object service which save the file object attributes on the local database.
 * <p>
 *
 * @author dz
 */
public interface FileObjectService{

    /**
     * @param id the id of file object
     * @return FileObject
     */
    Optional<com.sailmi.core.oss.provider.mongooss.model.FileObject> findById(String id);

    /**
     * @param storedFileName the stored file name which returned by underlying store service
     * @return FileObject
     */
    FileObject findByStoredFilename(String storedFileName);

    /**
     * @param id the id of file object not the id of history id
     * @return the history of the FileObject
     */
    List<FileObjectHistory> findHistoryByFileObjectId(String id);

    /**
     * @param saveRequest the request to store file
     * @return FileObject
     */
    FileObject save(FileObjectSaveRequest saveRequest);

    /**
     * @param fileObject
     * @return
     */
    FileObjectHistory saveAsHistory(FileObject fileObject);

    /**
     * Delete the file object from database (not physically) .
     *
     * @param id the id of file object
     * @return FileObject
     */
    Optional<com.sailmi.core.oss.provider.mongooss.model.FileObject> deleteById(String id);

    /**
     * Delete the file object from database (not physically) .
     *
     * @param storedFileName full stored file name, partial is not supported
     * @return FileObject
     */
    FileObject deleteByStoredFilename(String storedFileName);

    /**
     * @param searchRequest the search request supporting different query condition
     * @return FileObject Page
     */
    Page<FileObject> search(FileObjectSearchRequest searchRequest);

}

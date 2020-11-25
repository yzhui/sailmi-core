package com.sailmi.core.oss.provider.mongooss.repository.custom;

import com.sailmi.core.oss.common.domain.request.FileObjectSearchRequest;
import com.sailmi.core.oss.provider.mongooss.model.FileObject;
import org.springframework.data.domain.Page;

/**
 * @author dz
 */
public interface FileObjectRepositoryCustom {

	Page<FileObject> findPage(FileObjectSearchRequest searchRequest);

}

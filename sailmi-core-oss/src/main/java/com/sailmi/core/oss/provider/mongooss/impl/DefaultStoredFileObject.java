package com.sailmi.core.oss.provider.mongooss.impl;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.sailmi.core.oss.common.core.StoreFileRequest;
import com.sailmi.core.oss.common.core.StoredFileObject;
import com.sailmi.core.oss.common.domain.model.FileObject;
import com.sailmi.core.oss.common.support.DefaultFileObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DefaultStoredFileObject extends DefaultFileObject implements StoredFileObject {
	@Autowired
	private GridFSBucket gridFSBucket;


    public static DefaultStoredFileObject from(StoreFileRequest request) {
        if (request == null) {
            return null;
        }
        DefaultStoredFileObject result = new DefaultStoredFileObject();
        BeanUtils.copyProperties(request, result);
        return result;
    }

    public static DefaultStoredFileObject from(FileObject fileObject) {
        if (fileObject == null) {
            return null;
        }
        DefaultStoredFileObject result = new DefaultStoredFileObject();
        BeanUtils.copyProperties(fileObject, result);
        return result;
    }

    private static final Log logger = LogFactory.getLog(DefaultStoredFileObject.class);

    private String providerName;

    private GridFSFile gridFSFile;

    @Override
    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Override
    public GridFSFile getImplementation() {
        return gridFSFile;
    }

    public void setImplementation(GridFSFile gridFSFile) {
        this.gridFSFile = gridFSFile;
    }

    @Override
    public void writeTo(OutputStream outputStream, int bufferSize) throws IOException {
        if (getImplementation() == null) {
            throw new UnsupportedOperationException("The stored file implementation is not supplied.");
        }
        logger.warn(String.format("The bufferSize is not supported in current provider, the value[%d] will be ignored.",
                                  bufferSize));
		GridFSDownloadStream in = gridFSBucket.openDownloadStream(this.getImplementation().getObjectId());
		GridFsResource resource = new GridFsResource(this.getImplementation(),in);
		byte[] readByte=new byte[bufferSize];
		InputStream resourceInputStream = resource.getInputStream();
		int readLen =resourceInputStream.read(readByte);
		while(readLen>0){
			outputStream.write(readByte,0,readLen);
			readLen =resource.getInputStream().read(readByte);
		}
		resourceInputStream.close();
		outputStream.close();
    }

    @Override
    public String toString() {
        return "DefaultStoredFileObject{" +
                "providerName='" + providerName + '\'' +
                ", gridFSFile=" + gridFSFile +
                "} " + super.toString();
    }

}

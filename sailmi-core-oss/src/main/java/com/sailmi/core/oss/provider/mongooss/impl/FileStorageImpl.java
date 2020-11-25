package com.sailmi.core.oss.provider.mongooss.impl;


import com.mongodb.client.gridfs.model.GridFSFile;
import com.sailmi.core.oss.common.core.*;
import com.sailmi.core.oss.common.support.DefaultStoreFileResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.StringUtils;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author dz
 */
public class FileStorageImpl implements FileStorage, InitializingBean {

	@Autowired
	private MongoDatabaseFactory mongoDatabaseFactory;

	@Autowired
	private MongoConverter mongoConverter;


	private GridFsTemplate gridFsTemplate;


    private static final Log logger = LogFactory.getLog(FileStorageImpl.class);

    public static final String PROVIDER_NAME = "mongooss";

    public static final String GRIDFS_COLLECTION_NAME = "FileObjects";


    private String collectionName = GRIDFS_COLLECTION_NAME;

    public String getCollectionName() {
        return this.collectionName;
    }

	public FileStorageImpl() {
		//如果gridFsTemplate未初始化，则初始化gridFsTemplate
		if(gridFsTemplate == null) {
			System.out.println("FileStorageImpl Init Grid Template Now!!!!!!!!!!!!!!!!");
			System.out.println("FileStorageImpl Mongo Database Factory Is:"+mongoDatabaseFactory);
			System.out.println("FileStorageImpl Mongo Converter is:"+mongoConverter);
			this.gridFsTemplate = new GridFsTemplate(mongoDatabaseFactory,mongoConverter);

		}

	}

	public void setCollectionName(String collectionName) {
        if (StringUtils.isEmpty(collectionName)) {
            throw new IllegalArgumentException("The collection name can't be null or empty");
        }
        this.collectionName = collectionName;
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    public boolean isMetadataSupported() {
        return true;
    }

	@Override
    public StoreFileResponse store(InputStream inputStream, StoreFileRequest request) throws StoreFileException {
        //generate the stored file name
		//对于小文件的数据后面要进行处理，只有大文件才需要GRIDFS，小文件直接存BSON
        String filenameToStore = UUID.randomUUID().toString().replace("-", "");
        System.out.println("store file,file name is:"+filenameToStore);
        String extName = com.sailmi.core.oss.common.repackage.org.apache.commons.io.FilenameUtils.getExtension(request.getOriginalFilename());
		System.out.println("store file,file ext name is:"+extName);
        if (!StringUtils.isEmpty(extName)) {
            filenameToStore += "." + extName;
        }
        System.out.println("gridFsTemplate is:"+this.gridFsTemplate);
        //存储文件
		ObjectId objectId = this.gridFsTemplate.store(inputStream,filenameToStore);
		String fileObjectId = objectId.toString();
		System.out.println("store file,file object id is:"+fileObjectId);

        logger.debug(String.format("%s is stored", filenameToStore));
		Criteria criteria = new Criteria("_id").is(fileObjectId);
		Query query = new Query(criteria);
		GridFSFile gridFSFile = this.gridFsTemplate.findOne(query);

        DefaultStoredFileObject fileObject = DefaultStoredFileObject.from(request);
        fileObject.setSize(gridFSFile.getChunkSize());
        fileObject.setUploadedAt(gridFSFile.getUploadDate());
        fileObject.setStoredFilename(filenameToStore);
        fileObject.setProviderName(PROVIDER_NAME);
        //fileObject.setImplementation(gridFSDBFile);

        return new DefaultStoreFileResponse(PROVIDER_NAME, fileObject);
    }

	@Override
	public StoreFileResponse store(File file, StoreFileRequest request) throws StoreFileException {
		return null;
	}

	@Override
    public StoreFileResponse store(byte[] bytes, StoreFileRequest request) throws StoreFileException {
        return store(new ByteArrayInputStream(bytes), request);
    }

    @Override
    public StoredFileObject findByStoredFilename(String filename) {

		Criteria criteria = new Criteria("_id").is(filename);
		Query query = new Query(criteria);
		GridFSFile gridFSFile = this.gridFsTemplate.findOne(query);

        if (gridFSFile == null) {
            return null;
        }

        DefaultStoredFileObject fileObject = new DefaultStoredFileObject();
        buildStoreFileObject(gridFSFile, fileObject);

        fileObject.setProviderName(PROVIDER_NAME);
        //fileObject.setImplementation(gridFSFile);

        return fileObject;
    }

    @Override
    public StoredFileObject findByStoredFilename(String filename, String downloadUrl) {
        logger.warn(String.format("Caution: The download url[%s] will be skipped", downloadUrl));
        return findByStoredFilename(filename);
    }

	@Override
	public InputStream getFile(String fileName){
		return null;
	}

    @Override
    public StoredFileObject delete(String filename) {
		Criteria criteria = new Criteria("_id").is(filename);
		Query query = new Query(criteria);
        GridFSFile gridFSFile = this.gridFsTemplate.findOne(query);

        if (gridFSFile == null) {
            return null;
        }

        DefaultStoredFileObject fileObject = new DefaultStoredFileObject();
        buildStoreFileObject(gridFSFile, fileObject);

        fileObject.setProviderName(PROVIDER_NAME);
        fileObject.setImplementation(null);

        try {
            this.gridFsTemplate.delete(query);
            logger.info(String.format("The gridfs file %s is deleted.", filename));
        } catch (Throwable e) {
            logger.error(String.format("Delete gridfs file %s failed.", filename), e);
        }

        return fileObject;
    }

    private void buildStoreFileObject(GridFSFile gridFSFile, DefaultStoredFileObject fileObject) {
        fileObject.setUploadedAt(gridFSFile.getUploadDate());
        fileObject.setSize(gridFSFile.getChunkSize());
        fileObject.setStoredFilename(gridFSFile.getFilename());

        Document dbObject = gridFSFile.getMetadata();

        if (dbObject == null) {
            return;
        }

        try {
            fileObject.setOriginalFilename((String) dbObject.get("oss-originalFilename"));
            fileObject.setPrettyFilename((String) dbObject.get("oss-prettyFilename"));
            fileObject.setContentType((String) dbObject.get("oss-contentType"));
            fileObject.setUploadedBy((String) dbObject.get("oss-uploadedBy"));
        } catch (Throwable e) {
            logger.error(e, e);
        }

        try {
            Map<String, String> attributes = new HashMap<>();
            dbObject.keySet().stream().filter(key -> key.startsWith("oss-attrs-")).forEach(key -> {
                String attributeName = key.substring("oss-attrs-".length());
                attributes.put(attributeName, (String) dbObject.get(key));
            });
            fileObject.setAttributes(attributes);
        } catch (Throwable e) {
            logger.error(e, e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

}

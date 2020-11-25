package com.sailmi.core.oss.provider.mongooss;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.sailmi.core.oss.common.core.FileStorage;
import com.sailmi.core.oss.common.domain.service.FileObjectService;
import com.sailmi.core.oss.props.OssProperties;
import com.sailmi.core.oss.props.OssProviderEnum;
import com.sailmi.core.oss.provider.mongooss.impl.FileObjectServiceImpl;
import com.sailmi.core.oss.provider.mongooss.impl.FileStorageImpl;
import com.sailmi.core.oss.provider.mongooss.impl.MongoCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author dz
 */
@Configuration
@EnableMongoRepositories({"com.sailmi.core.oss.provider.mongooss.repository"})
@EnableMongoAuditing
@DependsOn("ossProperties")
@Conditional(MongoCondition.class)
public class MongoAutoConfiguration {
	@Autowired
	OssProperties ossProperties;

	@Autowired
	private MongoDatabaseFactory mongoDatabaseFactory;

	@Autowired
	private MongoConverter mongoConverter;

	public static final String PROVIDER_NAME = "mongooss";

	@Bean("gridFsTemplate")
	public GridFsTemplate getGridFSTemplat() {
		System.out.println("Init Grid Template Now!!!!!!!!!!!!!!!!");
		System.out.println("Mongo Database Factory Is:"+mongoDatabaseFactory);
		System.out.println("Mongo Converter is:"+mongoConverter);
		return new GridFsTemplate(mongoDatabaseFactory,mongoConverter);
	}

	@Bean("gridFSBucket")
	public GridFSBucket getGridFSBuckets() {
		MongoDatabase db = mongoDatabaseFactory.getMongoDatabase();
		return GridFSBuckets.create(db);
	}

	@Bean(name = "fileObjectService")
    public FileObjectService fileObjectService() {

    	System.out.println("Now initial mongoossStorage!"+ossProperties.getProvider());
    	System.out.println("Enum mongo oss is:"+OssProviderEnum.mongooss);

		if(ossProperties.getProvider().equals(OssProviderEnum.mongooss)){
			System.out.println("Now Execute Initial!");
			return new FileObjectServiceImpl();
		}
		return null;
    }

	@Bean(name = "fileStorage")
	@Conditional(MongoCondition.class)
	public FileStorage fileStorage() {
		return new FileStorageImpl();
	}
}

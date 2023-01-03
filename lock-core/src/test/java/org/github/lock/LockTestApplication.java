package org.github.lock;


import org.apache.curator.test.TestingServer;
import org.github.framework.common.serializer.json.JacksonObjectSerializer;
import org.github.framework.lock.LockManager;
import org.github.framework.lock.aop.DistributionLockAspect;
import org.github.framework.lock.config.RedisLockProperties;
import org.github.framework.lock.config.ZookeeperLockProperties;
import org.github.framework.lock.exception.LockException;
import org.github.framework.lock.support.LockManagerImpl;
import org.github.framework.lock.support.redis.RedisLockProvider;
import org.github.framework.lock.support.zookeeper.ZookeeperClient;
import org.github.framework.lock.support.zookeeper.ZookeeperLockProvider;
import org.github.lock.service.BaseRLockService;
import org.github.lock.service.BizDemoService;
import org.github.lock.service.LockDemoService;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;


@SpringBootApplication(scanBasePackages = "com.github")
public class LockTestApplication {


    @Bean(destroyMethod = "stop")
    @Order(0)
    public TestingServer init() {
        try {
            TestingServer server = new TestingServer(2180);
            server.start();
            return server;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



//    @Bean
    public ZookeeperClient zookeeperClient(ZookeeperLockProperties properties)  {

        ZookeeperClient client = new ZookeeperClient();
        client.setObjectSerializer(new JacksonObjectSerializer());
        client.setProperties(properties);
        return client;
    }

    @Bean
    public LockManager lockManager() {
        LockManagerImpl lockManager =  new LockManagerImpl();
        return lockManager;
    }

//    @Bean
    public ZookeeperLockProvider zookeeperLockProvider(ZookeeperClient zookeeperClient) {
        ZookeeperLockProvider zookeeperLockProvider = new ZookeeperLockProvider();
        zookeeperLockProvider.setZookeeperClient(zookeeperClient);
        return zookeeperLockProvider;
    }

    @Bean
    public DistributionLockAspect distributionLockAspect() {
        return new DistributionLockAspect();
    }

    @Bean
    public LockDemoService lockAopService() {
        return new LockDemoService();
    }

    @Bean
    public BizDemoService bizDemoService() {
        return new BizDemoService();
    }

    @Bean
    public BaseRLockService baseRLockService() {
        return new BaseRLockService();
    }

    @Bean
//    @ConditionalOnBean(RedisLockProperties.class)
    public RedissonClient redissonClient(RedisLockProperties properties) {
        if (CollectionUtils.isEmpty(properties.getServers())) {
            throw new LockException("0001","Redis Lock Config err,Please set 'lock.redis.server=yourservers' in your spring config file!!");
        }
        Config config = new Config();
        if (properties.getServers().size() == 1) {
            config.useSingleServer().setAddress(properties.getServers().get(0));
        } else {
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            clusterServersConfig.addNodeAddress((String[])properties.getServers().toArray());
        }
        return Redisson.create(config);
    }

    @Bean
//    @ConditionalOnBean(RedisLockProperties.class)
    public RedisLockProvider redisLockProvider() {
        RedisLockProvider redisLockProvider = new RedisLockProvider();
        return redisLockProvider;
    }

    @Bean
//    @ConditionalOnProperty(prefix = "lock.redis",name = "enable",havingValue = "true")
    public RedisLockProperties redisLockProperties() {
        return new RedisLockProperties();
    }

    public static void main(String[] args) {
        SpringApplication.run(LockTestApplication.class);
    }

}

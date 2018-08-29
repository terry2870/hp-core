/**
 * 
 */
package com.hp.core.zookeeper.discovery;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.strategies.RandomStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hp.core.zookeeper.bean.RegisterInstanceDetail;

/**
 * @author ping.huang 2016年12月12日
 */
public class ServiceDiscoveryFactory implements Closeable {

	static Logger log = LoggerFactory.getLogger(ServiceDiscoveryFactory.class);

	private ServiceDiscovery<RegisterInstanceDetail> serviceDiscovery;
	
	private Map<String, ServiceProvider<RegisterInstanceDetail>> providers = Maps.newHashMap();
    private List<Closeable> closeableList = Lists.newArrayList();
    private Object lock = new Object();
	
    
    /**
	 * 注册服务
	 * @param detail
	 */
	public void registerService(RegisterInstanceDetail detail) {
		log.info("registerService start with serviceName={}. detail={}", detail.getServiceName(), detail);
		try {
			ServiceInstance<RegisterInstanceDetail> instance1 = ServiceInstance.<RegisterInstanceDetail> builder()
					.name(detail.getServiceName())
					.port(detail.getLinstenPort())
					.address(detail.getLinstenAddress())
					.payload(detail)
					.build();
			serviceDiscovery.registerService(instance1);
			log.info("registerService success with serviceName={}. detail={}", detail.getServiceName(), detail);
		} catch (Exception e) {
			log.error("registerService error. with detail={}", detail, e);
		}
	}
    
	/**
	 * 服务发现
	 * @param serviceName
	 * @return
	 * @throws Exception
	 */
	public ServiceInstance<RegisterInstanceDetail> discoveryService(String serviceName) throws Exception {
		log.debug("discoveryService start with serviceName={}", serviceName);
		ServiceProvider<RegisterInstanceDetail> provider = providers.get(serviceName);
		if (provider == null) {
			synchronized (lock) {
				provider = providers.get(serviceName);
				if (provider == null) {
					provider = serviceDiscovery.serviceProviderBuilder()
							.serviceName(serviceName)
							.providerStrategy(new RandomStrategy<RegisterInstanceDetail>())
							.build();
					provider.start();
					closeableList.add(provider);
					providers.put(serviceName, provider);
				}
			}
		}
		log.debug("discoveryService success with serviceName={}", serviceName);
		return provider.getInstance();
	}

	@Override
	public synchronized void close() {
		log.info("close ServiceProvider");
		for (Closeable closeable : closeableList) {
			CloseableUtils.closeQuietly(closeable);
		}
	}

	public ServiceDiscovery<RegisterInstanceDetail> getServiceDiscovery() {
		return serviceDiscovery;
	}

	public void setServiceDiscovery(ServiceDiscovery<RegisterInstanceDetail> serviceDiscovery) {
		this.serviceDiscovery = serviceDiscovery;
	}
}

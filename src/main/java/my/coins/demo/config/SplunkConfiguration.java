package my.coins.demo.config;

import com.splunk.HttpService;
import com.splunk.SSLSecurityProtocol;
import com.splunk.Service;
import com.splunk.ServiceArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SplunkConfiguration {


	@PostConstruct
	void init() {
		HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
	}

	@Bean
	Service splunk() {
		ServiceArgs loginArgs = new ServiceArgs();
		loginArgs.setUsername("admin");
		loginArgs.setPassword("changeme");
		loginArgs.setHost("localhost");
		loginArgs.setPort(8089);

		return Service.connect(loginArgs);
	}
}

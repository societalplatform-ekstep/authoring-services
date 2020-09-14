package com.infosys.lexauthoringservices.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.infosys.lexauthoringservices.util.DbProperties;

@Configuration
public class EsConfig {

	@Autowired
	DbProperties dbProps;

	@Bean(destroyMethod = "close")
	public RestHighLevelClient restHighLevelClient() {
		return new RestHighLevelClient(restClientBuilder());
	}

	@Bean(destroyMethod = "close")
	public RestClient restClient() {
		return restClientBuilder().build();
	}

	public RestClientBuilder restClientBuilder() {
		HttpHost[] hosts = new HttpHost[1];
		hosts[0] = new HttpHost(dbProps.getEsHost(), Integer.parseInt(dbProps.getEsPort()));
		RestClientBuilder builder = RestClient.builder(hosts);
		if (dbProps.getEsAuthEnabled()) {
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(dbProps.getEsUser(), dbProps.getEsPassword()));
			builder.setHttpClientConfigCallback(
					httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
		}
		return builder;
	}
}

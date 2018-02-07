package com.example.test;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.databene.benerator.anno.Source;
import org.databene.feed4testng.FeedTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FormTest extends FeedTest{

	@Test(dataProvider="feeder")
	@Source("./data/add.csv")
	public static void doPost(String a,String b,String expected) throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		try{
			HttpPost request = new HttpPost();
			URI uri = new URIBuilder()
					.setScheme("http")
					.setHost("192.168.160.133")
					.setPort(8080)
					.setPath("/project158/FormServlet")
					.setParameter("a", a)
					.setParameter("b",b)
					.build();
			request.setURI(uri);
			CloseableHttpResponse response = client.execute(request);
			try{
				HttpEntity entity = response.getEntity();
				String actual = EntityUtils.toString(entity);
//				System.out.println(actual);
				Assert.assertEquals(actual, expected);
			}finally{
				response.close();
			}
		}finally{
			client.close();
		}
	}
}

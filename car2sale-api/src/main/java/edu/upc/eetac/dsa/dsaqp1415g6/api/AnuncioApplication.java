package edu.upc.eetac.dsa.dsaqp1415g6.api;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.Enumeration;
import java.util.ResourceBundle;
 
public class AnuncioApplication extends ResourceConfig {
	public AnuncioApplication() {
		super();
		register(MultiPartFeature.class);
		register(DeclarativeLinkingFeature.class);
		ResourceBundle bundle = ResourceBundle.getBundle("application");

		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			property(key, bundle.getObject(key));
		}
	}
}

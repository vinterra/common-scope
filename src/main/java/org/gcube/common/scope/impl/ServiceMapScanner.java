package org.gcube.common.scope.impl;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.gcube.common.scope.api.ServiceMap;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scans the classpath for {@link ServiceMap}s.
 * 
 * @author Fabio Simeoni
 * 
 */
class ServiceMapScanner {

	private static Logger log = LoggerFactory.getLogger(ServiceMapScanner.class);

	/**
	 * The path used to find service map configuration files.
	 */
	static final String mapConfigPattern = ".*\\.servicemap";

	/**
	 * Scans the classpath for {@link ServiceMap}s.
	 */
	static Map<String, ServiceMap> maps() {

		Map<String, ServiceMap> maps = new HashMap<String, ServiceMap>();

		try {

			Set<String> resources = getMapNames();

			JAXBContext context = JAXBContext.newInstance(DefaultServiceMap.class);
			Unmarshaller um = context.createUnmarshaller();

			for (String resource : resources) {
				
				URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
				log.info("loading {} ", url);
				DefaultServiceMap map = (DefaultServiceMap) um.unmarshal(url);

				ServiceMap current = maps.get(map.scope());
				if (current != null && current.version() != null)
					if (current.version().compareToIgnoreCase(map.version()) == 1) {
						log.warn("discarding {} because older (v.{}) than one previously loaded (v.{}) for {} ",
								new Object[] { url, map.version(), current.version(), map.scope() });
						continue;
					} else
						log.info("overwriting older map (v.{}) with newer map (v.{}) for {} ",
								new Object[] { current.version(), map.version(), map.scope() });

				maps.put(map.scope(), map);
			}
		} catch (Exception e) {
			throw new RuntimeException("could not load service maps", e);
		}

		return maps;
	}

	private static Set<String> getMapNames() {

		// we include urls specified in manifest files, which is required
		// when we run tests in surefire's forked-mode
		ConfigurationBuilder builder = new ConfigurationBuilder().setUrls(
				ClasspathHelper.forManifest(jarURLs())).setScanners(new ResourcesScanner());

		Reflections reflections = new Reflections(builder);

		return reflections.getResources(Pattern.compile(mapConfigPattern));
	}
	
	private static Set<URL> jarURLs() {
		final Set<URL> result = new HashSet<URL>();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		while (classLoader != null && classLoader.getParent() != null) {
			if (classLoader instanceof URLClassLoader) {
				URL[] urls = ((URLClassLoader) classLoader).getURLs();
				if (urls != null) {
					result.addAll(new HashSet<URL>(Arrays.asList(urls)));
				}
			}
			classLoader = classLoader.getParent();
		}

		return result;
	}
}

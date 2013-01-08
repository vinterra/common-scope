package org.gcube.common.scope.impl;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Classpath discovery utils.
 * 
 * @author Fabio Simeoni
 * 
 */
public class Utils {

	// helper: we use reflections' code but exclude extension and primordial
	// classloaders
	// whose URLs we do not want to include. especially because these may have
	// non-standard URLs
	// that would need to be excluded individually from standard scanning,
	// or reflections will show (but ignore) a horrible error in the logs. and
	// we do no know how to predict what we will
	// find on any given machine
	static Set<URL> urlsToScan() {
		final Set<URL> result = Sets.newHashSet();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		while (classLoader != null && classLoader.getParent() != null) {
			if (classLoader instanceof URLClassLoader) {
				URL[] urls = ((URLClassLoader) classLoader).getURLs();
				if (urls != null) {
					result.addAll(Sets.<URL> newHashSet(urls));
				}
			}
			classLoader = classLoader.getParent();
		}

		return result;
	}
}

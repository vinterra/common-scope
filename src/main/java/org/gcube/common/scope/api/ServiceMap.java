package org.gcube.common.scope.api;

import javax.xml.bind.annotation.XmlSeeAlso;

import org.gcube.common.scope.impl.DefaultServiceMap;


/**
 * Resolves service endpoints statically configured for a given scope.
 * 
 * @author Fabio Simeoni
 *
 */
@XmlSeeAlso(DefaultServiceMap.class)
public interface ServiceMap {
	
	/**
	 * Returns the endpoint of a given service.
	 * @param service the service
	 * @return the endpoint
	 * @throws IllegalArgumentException if the service has no endpoint in the map
	 * @throws IllegalStateException if the service endpoint cannot be returned
	 */
	String endpoint(String service);
	
	/**
	 * Returns the associated scope.
	 * @return the scope
	 * @throws IllegalStateException if the scope of the map cannot be returned
	 */
	String scope();
	
	/**
	 * Returns the release version of the map.
	 * @return the version
	 * @throws IllegalStateException if the version of the map cannot be returned
	 */
	String version();
}

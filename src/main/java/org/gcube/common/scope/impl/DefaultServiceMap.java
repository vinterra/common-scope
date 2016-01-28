package org.gcube.common.scope.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.gcube.common.scope.api.ServiceMap;

/**
 * A {@link ServiceMap} with a standard XML binding.
 * 
 * @author Fabio Simeoni
 *
 */
@XmlRootElement(name="service-map")
public class DefaultServiceMap implements ServiceMap {
		
	@XmlAttribute
	private String scope;
	
	@XmlAttribute
	private String version;
	
	@XmlJavaTypeAdapter(ServiceMapAdapter.class)
	Map<String,String> services = new LinkedHashMap<String,String>();
	
		
	public DefaultServiceMap() {
		super();
	}

	public DefaultServiceMap(String scope, String version,
			Map<String, String> services) {
		super();
		this.scope = scope;
		this.version = version;
		this.services = services;
	}

	@Override
	public String scope() {
		return scope;	
	}
	
	@Override
	public String version() {
		return version;	
	}
	
	@Override
	public String endpoint(String service) {
		
		String endpoint = services.get(service);
		
		if (endpoint==null)
			throw new IllegalArgumentException("unknown service "+service);
		
		return endpoint;
	}

	
	@Override
	public String toString() {
		return "DefaultServiceMap [scope=" + scope + ", version=" + version
				+ ", services=" + services + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result
				+ ((services == null) ? 0 : services.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultServiceMap other = (DefaultServiceMap) obj;
		if (scope == null) {
			if (other.scope != null)
				return false;
		} else if (!scope.equals(other.scope))
			return false;
		if (services == null) {
			if (other.services != null)
				return false;
		} else if (!services.equals(other.services))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	
}

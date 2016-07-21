package org.gcube.common.scope;

import static org.junit.Assert.*;

import org.gcube.common.scope.impl.ContextBean;
import org.gcube.common.scope.impl.ContextBean.Type;
import org.junit.Test;

public class BeanTest {

	
	@Test
	public void beansAreParsedCorrectly() {
		
		String infra ="/infra";
		ContextBean infraBean = new ContextBean(infra);
		assertEquals("infra",infraBean.name());
		assertTrue(infraBean.is(Type.INFRASTRUCTURE));
		assertNull(infraBean.enclosingScope());
		assertEquals(infra,infraBean.toString());
		assertEquals(infraBean,new ContextBean(infra));
		
		String vo =infra+"/vo";
		ContextBean vobean = new ContextBean(vo);
		assertEquals("vo",vobean.name());
		assertTrue(vobean.is(Type.VO));
		assertEquals(infraBean,vobean.enclosingScope());
		assertEquals(vo,vobean.toString());
		
		String vre = vo+"/vre";
		ContextBean vrebean = new ContextBean(vre);
		assertEquals("vre",vrebean.name());
		assertTrue(vrebean.is(Type.VRE));
		assertEquals(vobean,vrebean.enclosingScope());
		assertEquals(vre,vrebean.toString());
		
	}
}

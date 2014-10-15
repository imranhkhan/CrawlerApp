package com.pramati.archiver;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.junit.Before;


public class MediaArchiverTest {
	
	private MediaArchiver mediaArchiver;
	
	@Before
	public void setUp(){
		mediaArchiver = createNiceMock(MediaArchiver.class);
		
	}
	
}

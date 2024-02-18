package nz.co.nileshprasad.java_spring.service;

import org.mule.runtime.api.artifact.Registry;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.core.api.construct.Flow;
import org.mule.runtime.core.api.context.notification.MuleContextNotification;
import org.mule.runtime.core.api.context.notification.MuleContextNotificationListener;
import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.api.event.EventContextFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CacheLoaderService implements MuleContextNotificationListener<MuleContextNotification> {
	
	 private final String MULE_CONTEXT_EVENT_STARTED = "mule context started";
	 
	 @Autowired
     private Registry muleRegistry;
	 
	public CacheLoaderService() {
		super();
		System.out.println("INITIALIZING CACHE");
	}
	
	
//	@PostConstruct
//	public void loadCache() {
//		System.out.println("LOADING CACHE");
//		Flow loadCache = (Flow) muleRegistry.lookupByName("loadCache").orElse(null);
//		
//		CoreEvent loadCacheEvent = createEvent("123", "Init cache on startup", loadCache);
//		
//		try {
//			loadCache.process(loadCacheEvent);
//		} catch (MuleException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	

    @Override
    public void onNotification(MuleContextNotification muleContext)  {
    	
    	  System.out.println("Mule Context :" + muleContext);
    	if (muleContext.getMuleContext().isStarted()) {
    	
	    	  System.out.println("LOADING CACHE");
	    	  
	    	  Flow loadCache = (Flow) muleRegistry.lookupByName("loadCache").orElse(null);
	    	  
	    	  System.out.println("FLOW NAME: " + loadCache.getName() + " " + loadCache.getInitialState());
	  		
	  		CoreEvent loadCacheEvent = createEvent("123", "Init cache on startup", loadCache);
	  		
	  		try {
	  			loadCache.process(loadCacheEvent);
	  		} catch (MuleException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
    	}
      
    }



	
    public CoreEvent createEvent(String correlationId, Object payload, Flow flow) {
        Message msg = Message.builder().value(payload).build();
        CoreEvent event = CoreEvent
                     .builder(EventContextFactory.create(flow,
                                 org.mule.runtime.dsl.api.component.config.DefaultComponentLocation
                                              .fromSingleComponent("add-location")))
                     .addVariable("coorelationId", correlationId).message(msg).build();
        return event;
    }

}

package net.kalaha.web.action;

import org.apache.log4j.Logger;

import net.kalaha.data.entities.User;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

public class BaseActionBean implements ActionBean {
	
    protected KalahaBeanContext context;
    
    protected final Logger log = Logger.getLogger(getClass());

    public KalahaBeanContext getContext() {
        return context;
    }
    
    public void setContext(ActionBeanContext context) {
        this.context = (KalahaBeanContext) context;
    }
    
    public User getCurrentUser() {
    	return context.getCurrentUser();
    }
}

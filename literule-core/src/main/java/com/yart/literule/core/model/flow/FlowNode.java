package com.yart.literule.core.model.flow;

import com.yart.literule.core.model.Node;
import com.yart.literule.core.model.flow.ins.FlowContext;
import com.yart.literule.core.model.flow.ins.FlowInstance;

import java.util.List;

public class FlowNode implements Node {
    protected String name;
    protected String eventBean;
    protected String x;
    protected String y;
    protected String width;
    protected String height;

    protected List<Connection> connections;
    public FlowNode() {
    }
    public FlowNode(String name) {
        this.name=name;
    }
    public final void enter(FlowContext context, FlowInstance instance){
        String msg=">>>进入决策流节点："+name;
        context.debugMsg(msg, MsgType.RuleFlow, instance.isDebug());
        ((ExecutionResponseImpl)context.getResponse()).addNodeName(name);
        KnowledgeSession session=(KnowledgeSession)context.getWorkingMemory();
        session.fireEvent(new ProcessBeforeNodeTriggeredEventImpl(this,instance,session));
        enterNode(context, instance);
        session.fireEvent(new ProcessAfterNodeTriggeredEventImpl(this,instance,session));
    }

    public abstract void enterNode(FlowContext context,FlowInstance instance);

    protected void leave(String connectionName,FlowContext context, FlowInstance instance) {
        for(Connection connection:connections){
            if(connectionName!=null){
                String cName=connection.getName();
                cName= cName==null ? cName : cName.trim();
                if(connectionName.trim().equals(cName)){
                    connection.execute(context, instance);
                    break;
                }
            }else if(connection.evaluate(context)){
                connection.execute(context, instance);
                break;
            }
        }
    }

    protected void executeNodeEvent(EventType type,FlowContext context,ProcessInstance instance){
        if(StringUtils.isEmpty(eventBean)){
            return;
        }
        ApplicationContext applicationContext=context.getApplicationContext();
        NodeEvent event=(NodeEvent)applicationContext.getBean(eventBean);
        if(type.equals(EventType.enter)){
            event.enter(this, instance, context);
        }else{
            event.leave(this, instance, context);
        }
    }

    public abstract FlowNodeType getType();

    public List<Connection> getConnections() {
        return connections;
    }
    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEventBean() {
        return eventBean;
    }
    public void setEventBean(String eventBean) {
        this.eventBean = eventBean;
    }
    public String getX() {
        return x;
    }
    public void setX(String x) {
        this.x = x;
    }
    public String getY() {
        return y;
    }
    public void setY(String y) {
        this.y = y;
    }
    public String getWidth() {
        return width;
    }
    public void setWidth(String width) {
        this.width = width;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
}

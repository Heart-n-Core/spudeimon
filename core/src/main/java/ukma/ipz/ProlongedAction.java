package ukma.ipz;

import java.util.List;

public class ProlongedAction {
    float time=0;
    float limitTime=1;
    ActionDelta action;
    List<ProlongedAction> containerLink = null;

    public ProlongedAction(float limitTime, ActionDelta action, List<ProlongedAction> containerLink) {
        this.limitTime = limitTime;
        this.action = action;
        this.containerLink = containerLink;
    }

    void executeV(float delta) {
        time+=delta;
        if(time<=limitTime){
            action.execute(delta);
        }else {
            containerLink.remove(this);
        }
    }

    public boolean execute(float delta) {
        time+=delta;
        if(time<=limitTime){
            action.execute(delta);
            return true;
        }else {
            return false;
        }
    }
}

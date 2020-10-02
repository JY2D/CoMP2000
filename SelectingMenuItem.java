import java.util.*;

public class SelectingMenuItem implements State {

    public void Action(int a, int b, Stage stage, StateOperator stateController){
        for(MenuItem mi : stage.menuOverlay){
            if (mi.contains(a,b)){
                mi.action.run();
                stage.menuOverlay = new ArrayList<MenuItem>();
            }
        }
    }
    
    public String toString() {
        return "SelectingMenuItem";
    }

}
import java.util.*;

public class SelectingTarget implements State {

    public void Action(int a, int b, Stage stage, StateOperator stateOperator){
        for(Cell cell: stage.cellOverlay){
            if (cell.contains(a, b)){
                Optional<Actor> oActor = stage.actorAt(cell);
                if (oActor.isPresent()){
                    oActor.get().makeRedder(0.1f);
                }
            }
        }
        stage.cellOverlay = new ArrayList<Cell>();
        stateOperator.setState(stateOperator.ChoosingActor);
    }
    
    public String toString() {
        return "SelectingTarget";
    }

}
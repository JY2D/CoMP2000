import java.util.*;

public class SelectingNewLocation implements State {

    public void Action(int a, int b, Stage stage, StateOperator stateOperator){
        Optional<Cell> clicked = Optional.empty();
        for (Cell cell : stage.cellOverlay) {
            if (cell.contains(a, b)) {
                clicked = Optional.of(cell);
            }
        }
        if (clicked.isPresent() && stage.actorInAction.isPresent()) {
            stage.actorInAction.get().setLocation(clicked.get());
            stage.actorInAction.get().turns--;
            stage.cellOverlay = new ArrayList<Cell>();
            stage.menuOverlay.add(new MenuItem("Fire", a, b, () -> {
                stage.cellOverlay = stage.grid.getRadius(stage.actorInAction.get().loc, stage.actorInAction.get().range, false);
                stage.cellOverlay.remove(stage.actorInAction.get().loc);
                stateOperator.setState(stateOperator.SelectingTarget);
            }));
            stateOperator.setState(stateOperator.SelectingMenuItem);
        } 
    }
    
    public String toString() {
        return "SelectingNewLocation";
    }

}
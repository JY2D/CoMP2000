import java.util.Optional;

public class ChoosingActor implements State {

    public void Action(int a, int b, Stage stage, StateOperator stateOperator){
        stage.actorInAction = Optional.empty();
        for (Actor actor : stage.actors) {
            if (actor.loc.contains(a, b) && actor.isTeamRed() && actor.turns > 0) {
                stage.actorInAction = Optional.of(actor);
                stage.cellOverlay = stage.grid.getRadius(actor.loc, actor.moves, true);
                stateOperator.setState(stateOperator.SelectingNewLocation);
            }
        }
        if(!stage.actorInAction.isPresent()){
            stateOperator.setState(stateOperator.SelectingMenuItem);
            stage.menuOverlay.add(new MenuItem("Oops", a, b, () -> stateOperator.setState(stateOperator.ChoosingActor)));
            stage.menuOverlay.add(new MenuItem("End Turn", a, b+MenuItem.height, () -> stateOperator.setState(stateOperator.CPUMoving)));
            stage.menuOverlay.add(new MenuItem("End Game", a, b+MenuItem.height*2, () -> System.exit(0)));
        }
    }
    
    public String toString() {
        return "ChoosingActor";
    }

}
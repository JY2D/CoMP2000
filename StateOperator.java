public class StateOperator {

    State ChoosingActor;
    State CPUMoving;
    State SelectingMenuItem;
    State SelectingNewLocation;
    State SelectingTarget;
    State state;

    public StateOperator() {
        ChoosingActor = new ChoosingActor();
        CPUMoving = new CPUMoving();
        SelectingMenuItem = new SelectingMenuItem();
        SelectingNewLocation = new SelectingNewLocation();
        SelectingTarget = new SelectingTarget();
    }

    public void setState(State state) {
        this.state = state;
    }
    
    public State getState() {
        return this.state;
    }

    public void action(int a, int b, Stage stage, StateOperator stateOperator){
        state.Action(a, b, stage, stateOperator);
    }
    
}
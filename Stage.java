import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.util.*;

public class Stage {
    Grid grid;
    ArrayList<Actor> actors;
    List<Cell> cellOverlay;
    List<MenuItem> menuOverlay;
    Optional<Actor> actorInAction;
    StateOperator stateOperator;
    
    private Stage(){
        grid = Grid.getInstance();
        actors = new ArrayList<Actor>();
        cellOverlay = new ArrayList<Cell>();
        menuOverlay = new ArrayList<MenuItem>();
        stateOperator = new StateOperator();
        stateOperator.setState(stateOperator.ChoosingActor);
    }

    public void paint(Graphics g, Point mouseLoc){

        // do we have AI moves to make
        if (stateOperator.getState() == stateOperator.CPUMoving){
            for(Actor a: actors){
                if (!a.isTeamRed()){
                    List<Cell> possibleLocs = getClearRadius(a.loc, a.moves, true);

                    Cell nextLoc = a.strat.chooseNextLoc(possibleLocs);

                    a.setLocation(nextLoc);
                }
            }
            stateOperator.setState(stateOperator.ChoosingActor);
            for(Actor a: actors){
                a.turns = 1;
            }
        }
        grid.paint(g,mouseLoc);
        grid.paintOverlay(g, cellOverlay, new Color(0f, 0f, 1f, 0.5f));

        for(Actor a: actors){
            a.paint(g);   
        }
        
        // state display
        g.setColor(Color.DARK_GRAY);
        g.drawString(stateOperator.getState().toString(), 720, 20);

        // display cell
        Optional<Cell> cap = grid.cellAtPoint(mouseLoc);
        if (cap.isPresent()){
            Cell capc = cap.get();
            g.setColor(Color.DARK_GRAY);
            g.drawString(String.valueOf(capc.col) + String.valueOf(capc.row), 720, 50);
            g.drawString(capc.description, 820, 50);
            g.drawString("crossing time", 720, 65);
            g.drawString(String.valueOf(capc.crossingTime()), 820, 65);
        } 

        // agent display
        int yloc = 138;
        for(int x = 0; x < actors.size(); x++){
            Actor a = actors.get(x);
            g.drawString(a.getClass().toString(), 720, yloc + 70*x);
            g.drawString("location:", 730, yloc + 13 + 70 * x);
            g.drawString(Character.toString(a.loc.col) + Integer.toString(a.loc.row), 820, yloc + 13 + 70 * x);
            g.drawString("redness:", 730, yloc + 26 + 70*x);
            g.drawString(Float.toString(a.redness), 820, yloc + 26 + 70*x);
            g.drawString("strat:", 730, yloc + 39 + 70*x);
            g.drawString(a.strat.toString(), 820, yloc + 39 + 70*x);
        }

        // menu overlay (on top of everything else)
        for(MenuItem mi: menuOverlay){
            mi.paint(g);
        }

    }

    public List<Cell> getClearRadius(Cell from, int size, boolean considerCost){
        List<Cell> init = grid.getRadius(from, size, considerCost);
        for(Actor a: actors){
            init.remove(a.loc);
        }
        return init;
    }

    public void mouseClicked(int a, int b){
    
        stateOperator.action(a, b, this, stateOperator);

    }

    public Optional<Actor> actorAt(Cell c){
        for(Actor a: actors){
            if (a.loc == c){
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }

    private static Stage stage = new Stage();

    public static Stage getInstance(){
        return stage;
    }
}
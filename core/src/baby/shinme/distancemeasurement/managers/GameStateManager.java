package baby.shinme.distancemeasurement.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

import baby.shinme.distancemeasurement.States.State;

/**
 * Created by jichoul on 2017-05-14.
 */
public class GameStateManager {

    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.pop();
    }

    public void set(State state) {
        states.pop();
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}

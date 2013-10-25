/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

/**
 *
 * @author HD
 */
public class ComponentT extends Component{
     @Override
    public void initShape() {
        shape = new int[][]{{0, 1, 0},
                            {1, 1, 1}};
    }
}

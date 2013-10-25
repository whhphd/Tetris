/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author HD
 */
public class Tetris extends JFrame implements Runnable {

    public int[][] paintMap;
    public int[][] landedMap;
    public Component currentComponent;

    Tetris() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
        } catch (Exception ex) {
        }
        this.setTitle("俄罗斯方块");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width - 450) / 2, (screenSize.height - 730) / 2, 450, 730);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                handleKeyPressed(ke);
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        paintMap = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {1, 1, 1, 1, 1, 1, 1, 1, 1,},};
        landedMap = paintMap;
        currentComponent = addComponet();
//        Graphics g = getGraphics();
//        g.setColor(Color.black);
//        g.fillRect(0, 0, 100, 60);
//        update(g);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

//           for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
        Tetris tetris = new Tetris();

//        Graphics g = tetris.getGraphics();
//        g.setColor(Color.yellow);
//        g.fillRect(10, 20, 5, 6);
//        tetris.repaint();
        Thread t = new Thread(tetris);
        t.start();
    }

    private Component addComponet() {
        double i = Math.random();
        if (i < 0.2) {
            return new ComponentT();
        }
        if (i < 0.4) {
            return new ComponentL();
        }
        if (i < 0.6) {
            return new ComponentO();
        }
        if (i < 0.8) {
            return new ComponentI();
        }

        return new ComponentZ();

    }

    //   @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        g.setColor(new Color(131, 175, 155));
//
//        for (int i = 0; i < paintMap.length; i++) {
//            for (int j = 0; j < paintMap[0].length; j++) {
//                if (paintMap[i][j] == 1) {
//                    g.fillRect(40 * j, 50 + 40 * i, 37, 37);
//                }
//            }
//        }
//    }
    @Override
    public void update(Graphics g) {
        super.update(g);
        g.setColor(new Color(131, 175, 155));

        for (int i = 0; i < paintMap.length; i++) {
            for (int j = 0; j < paintMap[0].length; j++) {
                if (paintMap[i][j] == 1) {
                    g.fillRect(40 * j, 50 + 40 * i, 37, 37);
                }
            }
        }
    }

    public boolean checkBottom(Component component) {

        for (int i = component.shape.length; i > 0; i--) {
            for (int j = 0; j < component.shape[0].length; j++) {
                if (component.shape[i - 1][j] == 1) {
                    if (checkIfCover(i + component.position[0], j + component.position[1])) {
                        return false;
                    }
                }
            }
        }

        return true;

    }

    private boolean checkIfCover(int i, int j) {
        if (getLandedMap()[i][j] == 1) {
            return true;
        }
        return false;
    }

    public void showComponent(Component component) {
        for (int i = component.shape.length; i > 0; i--) {
            for (int j = 0; j < component.shape[0].length; j++) {
                if (component.shape[i - 1][j] == 1) {
                    paintMap[i + component.position[0] - 1][j + component.position[1]] = 1;
                }
            }
        }
    }

    public int[][] getLandedMap() {
        landedMap = paintMap;
        for (int i = 0; i < currentComponent.getFrameMap().length; i++) {
            for (int j = 0; j < currentComponent.getFrameMap()[0].length; j++) {
                if (currentComponent.getFrameMap()[i][j] == 1) {
                    paintMap[i][j] = 0;
                }
            }
        }
        return landedMap;
    }

    public void ereaseLastComponentFrame(Component component) {
        for (int i = 0; i < component.lastPosition.length; i++) {
            for (int j = 0; j < component.lastPosition[i].length; j++) {
                if (component.lastPosition[i][j] == 1) {
                    paintMap[i][j] = 0;
                }
            }
        }
    }

    public void ereaseComponentFrame() {
        for (int i = 0; i < currentComponent.getMap().length; i++) {
            for (int j = 0; j < currentComponent.getMap()[i].length; j++) {
                if (currentComponent.getMap()[i][j] == 1) {
                    paintMap[i][j] = 0;
                }
            }
        }
    }

    public void handleKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                while (checkBottom(currentComponent)) {
                    currentComponent.fall();
                }
                refeshFrame();
                break;
            case KeyEvent.VK_UP:
                if (checkRotate()) {
                    currentComponent.rotate();
                    refeshFrame();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (currentComponent.checkMoveRight()) {
                    if (checkMoveRight()) {
                        currentComponent.moveRight();
                        refeshFrame();
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                if (currentComponent.checkMoveLeft()) {
                    if (checkMoveLeft()) {
                        currentComponent.moveLeft();
                        refeshFrame();
                    }
                }
                break;
        }

    }

    public void refeshFrame() {
        ereaseLastComponentFrame(currentComponent);
        showComponent(currentComponent);
        update(this.getGraphics());

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException ex) {
            }
            if (checkBottom(currentComponent)) {
                currentComponent.fall();
                refeshFrame();
            } else {
                showComponent(currentComponent);
                ereaseFullColumn();
                update(this.getGraphics());
                currentComponent = addComponet();
                refeshFrame();
            }
        }
    }

    private boolean checkRotate() {
        for (int i = 0; i < currentComponent.getRotatedMap().length; i++) {
            for (int j = 0; j < currentComponent.getRotatedMap()[0].length; j++) {
                if (currentComponent.getRotatedMap()[i][j] == 1) {
                    if (checkIfCover(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkMoveRight() {
        for (int i = 0; i < currentComponent.getMoveRightMap().length; i++) {
            for (int j = 0; j < currentComponent.getMoveRightMap()[0].length; j++) {
                if (currentComponent.getMoveRightMap()[i][j] == 1) {
                    if (checkIfCover(i, j)) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    private boolean checkMoveLeft() {
        for (int i = 0; i < currentComponent.getMoveLeftMap().length; i++) {
            for (int j = 0; j < currentComponent.getMoveLeftMap()[0].length; j++) {
                if (currentComponent.getMoveLeftMap()[i][j] == 1) {
                    if (checkIfCover(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void ereaseFullColumn() {
        List fullColumnList = getFullColumnList();

        //ereaseComponentFrame();
        for (int i = 0; i < fullColumnList.size(); i++) {
            for (int x = (int) fullColumnList.get(i) - 1; x >= 0; x--) {
                for (int y = 0; y < paintMap[0].length; y++) {
                    paintMap[x + 1][y] = paintMap[x][y];
                }
            }
            // showComponent(currentComponent);
        }
    }

    private List getFullColumnList() {
        List list = new ArrayList();
        for (int i = 0; i < paintMap.length; i++) {
            boolean flag = true;

            for (int j = 0; j < paintMap[0].length; j++) {
                if (paintMap[i][j] != 1) {
                    flag = false;
                }
            }
            if (flag) {
                if (i != (paintMap.length - 1)) {
                    list.add(i);
                }
            }
        }
        return list;
    }
}

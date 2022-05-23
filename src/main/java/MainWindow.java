import data.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import world.Camera;
import world.CameraConstants;
import world.Line;
import world.Polygon3D;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainWindow extends JFrame implements KeyListener, ActionListener {

    private static final Logger log = LoggerFactory.getLogger(MainWindow.class);
    private final Camera camera;

    private final JMenuBar menu;
    private final JMenu file;
    private final JMenu help;
    private final JMenuItem loadData;
    private final JMenuItem about;
    private final JMenuItem howToUse;


    public MainWindow(){
        super("Virtual camera");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//      == key operation support ==
        addKeyListener(this);

//      == components ==
//        menu
        menu = new JMenuBar();
        this.setJMenuBar(menu);

//      == main menu items ==
//      file
        file = new JMenu("File");
//        todo add shortcut
        menu.add(file);

//      help
        help = new JMenu("Help");
        menu.add(help);


//      == menu sub items ==
//      load data
        loadData = new JMenuItem("Load data");
        loadData.addActionListener(this);
        file.add(loadData);

//      about
        about = new JMenuItem("About");
        about.addActionListener(this);
        help.add(about);

//      howToUse
        howToUse =new JMenuItem("How to use");
        howToUse.addActionListener(this);
        help.add(howToUse);

//      == camera ==
        camera =new Camera();
//        todo delete in the end
        try {
            camera.setObjects(DataReader.load("src/main/resources/data1.json"));
        } catch (IOException e) {
           log.error(e.getMessage());
           e.printStackTrace();
        }
        this.add(camera);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == about){
            JOptionPane.showMessageDialog(this,"Virtual camera project","About",JOptionPane.INFORMATION_MESSAGE);
        }else if(e.getSource() == howToUse){
            JOptionPane.showMessageDialog(this ,"Instruction","How to use",JOptionPane.INFORMATION_MESSAGE);

        }else if(e.getSource() == loadData){
            JFileChooser fileChooser = new JFileChooser( FileSystemView.getFileSystemView().getHomeDirectory());
            int isSelected = fileChooser.showOpenDialog(this);
            if(isSelected == JFileChooser.APPROVE_OPTION){
                try{
                    File file = fileChooser.getSelectedFile();
                    List<Polygon3D> objects = DataReader.load(file.getPath());
                    camera.setObjects(objects);
                    camera.repaint();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            new JFrame(),
                            "Error reading a file. Check syntax!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
       switch(e.getKeyCode()){
//         move vertically and horizontally
//        == move up ==
           case KeyEvent.VK_UP -> camera.moveAxisY(-CameraConstants.STEP);
//         == move down ==
           case KeyEvent.VK_DOWN -> camera.moveAxisY(CameraConstants.STEP);
//         == move right ==
           case KeyEvent.VK_RIGHT -> camera.moveAxisX(CameraConstants.STEP);
//         == move left ==
           case KeyEvent.VK_LEFT -> camera.moveAxisX(-CameraConstants.STEP);
//         == move forwards ==
           case KeyEvent.VK_Q -> camera.moveAxisZ(-CameraConstants.STEP);
//         == move backwards
           case KeyEvent.VK_W -> camera.moveAxisZ(CameraConstants.STEP);
//         == zoom in ==
           case KeyEvent.VK_I -> camera.setZoom(CameraConstants.ZOOM);
//         == zoom out ==
           case KeyEvent.VK_O -> camera.setZoom(-CameraConstants.ZOOM);
//         == x rotation - forward ==
           case KeyEvent.VK_F -> camera.rotateAxisX(-CameraConstants.TURN);
//         == x rotation - backwards ==
           case KeyEvent.VK_G -> camera.rotateAxisX(CameraConstants.TURN);
//         == y rotation - right ==
           case KeyEvent.VK_H-> camera.rotateAxisY(CameraConstants.TURN);
//         == y rotation - left ==
           case KeyEvent.VK_J -> camera.rotateAxisY(-CameraConstants.TURN);
//         == z rotation- down&left ==
           case KeyEvent.VK_K -> camera.rotateAxisZ(-CameraConstants.TURN);
//         == z rotation- down&right ==
           case KeyEvent.VK_L -> camera.rotateAxisZ(CameraConstants.TURN);
    }
       camera.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

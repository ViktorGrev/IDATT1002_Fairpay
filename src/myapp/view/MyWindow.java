package myapp.view;

import myapp.repo.MyEntityRepo;
import myapp.data.MyEntity;

import java.awt.GridLayout;
import javax.swing.*;

/**
 * Main window for my application!
 *
 * @author nilstes
 */
public class MyWindow extends JFrame {

    /**
     * Constructor for window
     *
     * @param  title  Title ow the window
     * @return      the image at the specified URL
     * @see         Image
     */
    public MyWindow(String title) {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1, 1, 1));
  
		MyEntityRepo dao = new MyEntityRepo();
		MyEntity object = dao.getMyEntity("id");
  
        add(new JLabel(object.getName()));
  
        pack();
    }
}

package myapp.view;

import myapp.dao.MyEntityDao;
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
  
		MyEntityDao dao = new MyEntityDao();
		MyEntity object = dao.getMyEntity("id");
  
        add(new JLabel(object.getName()));
  
        pack();
    }
}

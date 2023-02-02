package no.ntnu.idatt1002.demo.view;

import no.ntnu.idatt1002.demo.repo.MyEntityRepo;
import no.ntnu.idatt1002.demo.data.MyEntity;

import java.awt.*;
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

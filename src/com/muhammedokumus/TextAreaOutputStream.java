package com.muhammedokumus;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.*;

/**
 * Main class for multi thread testing visualization
 */
public class TextAreaOutputStream extends OutputStream {
    private final JTextArea textTerminal;
    private final StringBuilder sb = new StringBuilder();

    public TextAreaOutputStream(final JTextArea textTerminal) {
        this.textTerminal = textTerminal;
    }

    @Override
    public void flush() {}

    @Override
    public void close() {}

    @Override
    public void write(int b) throws IOException {
        if (b == '\r') {
            return;
        }

        if (b == '\n') {
            final String text = sb.toString() + "\n";
            textTerminal.append(text);
            sb.setLength(0);
        }
        else {
            sb.append((char) b);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Setup threads*******************************
            ThreadSafeBestDSEver myCollection = new ThreadSafeBestDSEver();
            Producer p = new Producer(myCollection,5);
            Consumer c = new Consumer(myCollection);
            Thread producer  = new Thread(p, "producer");
            Thread consumer = new Thread(c, "consumer");
            // *******************************

            // Create GUI elements*******************************
            JFrame frame = new JFrame(TextAreaOutputStream.class.getSimpleName());
            JTextArea textTerminal = new JTextArea(24, 80);
            JButton buttonStart = new JButton("Start");
            JButton buttonStop = new JButton("Stop");
            JButton buttonExit = new JButton("Exit");
            buttonExit.setEnabled(false);
            JScrollPane scrollPane = new JScrollPane (textTerminal, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            // *******************************

            //Populate Frame *******************************
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(buttonStart);
            frame.add(buttonStop);
            frame.add(buttonExit);
            // *******************************

            // Setup Frame *******************************
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            System.setOut(new PrintStream(new TextAreaOutputStream(textTerminal)));
            frame.getContentPane().setLayout(new FlowLayout());
            frame.pack();
            frame.setVisible(true);
            // *******************************

            // Redirect system.out to text area
            System.setOut(new PrintStream(new TextAreaOutputStream(textTerminal)));

            // BUTTON: START
            // Start threads *******************************
            buttonStart.addActionListener(e -> {
                producer.start();
                consumer.start();
                // Setup scroll pane for auto scrolling
                Thread scroller = new Thread(() -> {
                    while(true) {
                        synchronized (myCollection) {
                            while(c.done){
                                textTerminal.setCaretPosition(textTerminal.getDocument().getLength());
                                c.done = false;
                            }
                            myCollection.notify();
                        }
                    }
                });
                scroller.start();
                buttonStart.setEnabled(false);
            });

            // BUTTON: STOP
            buttonStop.addActionListener(e -> {
                p.cancel = true;
                c.cancel = true;
                System.out.println("==================================================================");
                System.out.println("Ending main thread.");
                buttonStart.setEnabled(false);
                buttonStop.setEnabled(false);
                buttonExit.setEnabled(true);
            });

            // BUTTON: EXIT
            buttonExit.addActionListener(e -> System.exit(1));

            Thread currentThread = Thread.currentThread();
            System.out.println("Main thread: " + currentThread.getName() + "(" + currentThread.getId() + ")");
        });
    }
}
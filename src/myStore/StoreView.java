package myStore;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

/**
 * The myStore.myStore.StoreView class models a store view.
 * Each instance has a myStore.StoreManager, a unique cart ID, and
 * various Swing components to be assembled in the GUI.
 *
 * @author  Toyin Adams
 * @version 1.0
 */
public class StoreView {
    private final StoreManager storeManager;
    private final int cartID;

    // GUI layout components
    // JPanels
    private final JFrame frame;
    private final JPanel mainPanel;
    private final JPanel headerPanel;
    private final JPanel bodyPanel;
    private final JPanel footerPanel;

    // JButtons
    private final JButton add1ToCart;
    private final JButton remove1FromCart;
    private final JButton browseCart;
    private final JButton checkout;
    //private final JButton emptyCart;

    // JLabels
    private final JLabel modelDisplay;
    private final JLabel modelStock;
    private final JLabel modelName;
    private final JLabel modelID;
    private final JLabel modelPrice;
    private final JLabel status;
    private final JLabel displayID;

    // ImageIcons
    // There are 8 models, each in 6 colors
    private final ArrayList<ImageIcon> model1;  //each Arraylist contains images of the model in the 6 colors
    private final ArrayList<ImageIcon> model2;
    private final ArrayList<ImageIcon> model3;
    private final ArrayList<ImageIcon> model4;
    private final ArrayList<ImageIcon> model5;
    private final ArrayList<ImageIcon> model6;
    private final ArrayList<ImageIcon> model7;
    private final ArrayList<ImageIcon> model8;
    private final ArrayList<ArrayList<ImageIcon>> models;   // ArrayList of all model ArrayLists
    private final ArrayList<CustomColor> modelColors;            // model colors

    // indicators
    private int selectedModel;
    private int selectedColor;

    // layout colors, font
    private final Color bG1;
    private final Color bG2;
    private final Font bodyFont;

    /**
     * Constructs a myStore.StoreView with a specified myStore.StoreManager and cartID and initializes JComponents.
     *
     * @param storeManager  myStore.StoreManager, the storemanger.
     * @param cartID        int, the cartID
     */
    public StoreView(StoreManager storeManager, int cartID) {
        this.storeManager = storeManager;
        this.cartID = cartID;

        // set BG colours
        bG1 = new Color(75,115,165);
        bG2 = new Color(230,240,255);
        bodyFont = new Font("Corbel", Font.BOLD,18);

        // GUI components
        this.frame = new JFrame("SHOPTICAL.com");
        this.mainPanel = new JPanel(new BorderLayout());
        this.headerPanel = new JPanel();
        this.bodyPanel = new JPanel();

        this.modelDisplay = new JLabel();
        modelStock = new JLabel();
        modelName = new JLabel();
        modelID = new JLabel();
        modelPrice = new JLabel();
        status = new JLabel();

        model1 = new ArrayList<ImageIcon>();
        model2 = new ArrayList<ImageIcon>();
        model3 = new ArrayList<ImageIcon>();
        model4 = new ArrayList<ImageIcon>();
        model5 = new ArrayList<ImageIcon>();
        model6 = new ArrayList<ImageIcon>();
        model7 = new ArrayList<ImageIcon>();
        model8 = new ArrayList<ImageIcon>();
        modelColors = new ArrayList<CustomColor>();
        models = new ArrayList<ArrayList<ImageIcon>>(Arrays.asList(model1, model2, model3, model4, model5, model6, model7, model8));
        selectedModel = 0;  // sets default model
        selectedColor = 6;  // sets default colour

        this.footerPanel = new JPanel();
        this.browseCart = new JButton("Browse Cart");
        browseCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = "|--------------------------YOUR-CART--------------------------|\n" +
                        "|Quantity                    |myStore.Product Name               (ID)|\n";
                for (Product p : storeManager.getProductsCart(cartID)) {
                    if (storeManager.getStock(cartID, p.getId()) != 0)
                        s += String.format("|%-32d|%-20s (%d)|\n", storeManager.getStock(p.getId()), p.getName(), p.getId());
                }
                JOptionPane.showMessageDialog(frame, s, "Your Cart", JOptionPane.PLAIN_MESSAGE);
            }
        });
        this.checkout = new JButton("Checkout");
        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = "|---------------------------------RECEIPT---------------------------------|\n" +
                        "|Model             |myStore.Product ID        |Unit Price        |Quantity          |\n";
                for (Product p : storeManager.getProductsCart(cartID)) {
                    if (storeManager.getStock(cartID, p.getId()) != 0) {
                        s += String.format("|%-18s|%-18d|$%.2f %-12s|%-18d|\n", p.getName(), p.getId(), p.getPrice(), "", storeManager.getStock(cartID, p.getId()));
                    }
                }
               if (JOptionPane.showConfirmDialog(frame, s + "\nWould you like to process your transaction?", "Checkout",
                       JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
                   JOptionPane.showMessageDialog(frame,
                            String.format("Your total is: %.2f", storeManager.processTransaction(cartID)),
                            "Your transaction has been processed...",
                            JOptionPane.PLAIN_MESSAGE);
                   JOptionPane.showMessageDialog(frame,
                           "Thank you for shopping at Shoptical. Have a nice day!",
                           "Leaving Shoptical...",
                           JOptionPane.PLAIN_MESSAGE);
                   frame.setVisible(false);
                   frame.dispose();

                } else {
                   //if user enters no, nothings happens
               }
            }
        });

        this.displayID = new JLabel();

        this.add1ToCart = new JButton("+ Add 1 to cart");
        add1ToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int productID = 111100 + ((10 * selectedModel) + selectedColor);
                storeManager.addToCart(cartID,productID, 1);
                updateModelDisplay();
                enableAddRemove();
            }
        });
        this.remove1FromCart = new JButton("- Remove 1 from cart");
        remove1FromCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int productID = 111100 + ((10 * selectedModel) + selectedColor);
                storeManager.removeFromCart(cartID,productID, 1);
                updateModelDisplay();
                enableAddRemove();
            }
        });
    }

    /**
     * Checks the state of the myStore.Inventory and user myStore.ShoppingCart.
     * If the myStore.Inventory is empty it disables the add1ToCart button and if the myStore.ShoppingCart is empty it
     * disables the remove1FromCart button.
     */
    private void enableAddRemove() {
        int productID = 111100 + ((10 * selectedModel) + selectedColor);
        if (storeManager.getStock(productID) == 0) {
            add1ToCart.setEnabled(false);
            status.setText(String.format("There are no more Model %ds available in stock.", selectedModel + 1));
        } else {
            add1ToCart.setEnabled(true);
            status.setText("");
        }
        if (storeManager.getStock(cartID, productID) == 0) {
            remove1FromCart.setEnabled(false);
            status.setText(String.format("There are no Model %ds in your cart.", selectedModel + 1));
        } else {
            remove1FromCart.setEnabled(true);
            status.setText("");
        }
    }

    /**
     * Adds the specified CustomColors to the colors field of the myStore.StoreView
     * The CustomColors correspond to the available model colors.
     */
    private void buildColors() {
        String[] sColors = {"red", "orange", "yellow", "green", "blue", "purple", "black"};
        Color[] oColors = {new Color(200, 0,0), new Color(225, 90,0),
                new Color(240, 200,0), new Color(0, 160,70),
                new Color(0, 120,160), new Color(100, 0,160), new Color(0, 0,0)
        };
        for (int i = 0; i < 7; i++) {
            CustomColor color = new CustomColor(sColors[i], oColors[i]);
            this.modelColors.add(color);
        }
    }

    /**
     * Builds the ArrayList of ArrayLists that contains all the frame models and their color variations.
     */
    private void buildModels() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0 ; j < 7; j++) {
                models.get(i).add(new ImageIcon(String.format("graphics/Frame%d_%s.png", i + 1,
                        modelColors.get(j).getAsString())));
            }
        }
    }

    /**
     * Creates a myStore.Product corresponding to the specified model and color index.
     * Model key: [0]-Model1, [1]-Model2, [2]-Model3, [3]-Model4, [4]-Model5, ...
     * Color key: [0]-red, [1]-orange, [2]-yellow, [3]-green, [4]-blue, [5]-purple, [6]-black
     *
     * @param model model index, denotes model
     * @param color color index, denotes color
     * @return
     */
    private Product createModel(int model, int color) {
        String c = modelColors.get(color).getAsString();
        Product frame = new Product(String.format("Model %d - %s", model + 1, c), 111100 + ((10 * model) + color),
                60.00 - (2.00 * model) + (0.50 * color));
        return frame;
    }

    /**
     * Adds 20 of each Products corresponding the different frame models to the myStore.StoreManager myStore.Inventory.
     * The user's myStore.ShoppingCart is also initialized with Products mapped to 0 quantities.
     */
    private void setUpInventory() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                Product p = createModel(i, j);
                storeManager.initInventory(p, 20);
                storeManager.initCart(cartID);
            }
        }
    }

    /**
     * Creates and returns a JPanel containing JButtons corresponding to the color variations
     * of the frame models. Each JButton sets the selected color variation to be displayed.
     *
     * @return JPanel consisting of JButtons corresponding to each frame model colour variation
     */
    private JPanel colourPanel() {
        JPanel colourPanel = new JPanel();
        colourPanel.setBackground(bG2);
        colourPanel.setPreferredSize(new Dimension(500, 50));
        for (CustomColor c : modelColors) {
            JButton colorButton = new JButton();
            colorButton.setPreferredSize(new Dimension(65, 20));
            colorButton.setBackground(c.getAsColor());
            colorButton.setForeground(c.getAsColor());
            colorButton.setText(String.valueOf(modelColors.indexOf(c))); // hidden text that contains index correpsonding to color
            colorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedColor = Integer.valueOf(colorButton.getText());
                    updateModelDisplay();
                    status.setText("");
                }
            });
            colourPanel.add(colorButton);
        }
        return colourPanel;
    }

    /**
     * Updates the model display and model info JLabels based on selected model and selected colour indicators
     */
    private void updateModelDisplay() {
        modelDisplay.setIcon(models.get(selectedModel).get(selectedColor));
        int productID = 111100 + ((10 * selectedModel) + selectedColor);
        Product p = storeManager.getProduct(productID);
        modelStock.setText(String.format("Stock: %d", storeManager.getStock(p)));
        modelName.setText(p.getName());
        modelID.setText(String.format("ID: %d", productID));
        modelPrice.setText(String.format("Price: %.2f", p.getPrice()));
    }

    /**
     * Creates subsidiary components and assembles the layout and theme of the header.
     */
    private void assembleHeader() {
        headerPanel.setBackground(bG1);
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.PAGE_AXIS));
        JLabel welcome = new JLabel("Welcome to SHOPTICAL.com");
        JLabel intro = new JLabel("Browse through our frame models and colours below!");
        welcome.setForeground(bG2);
        welcome.setFont(new Font("Corbel", Font.BOLD,36));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        intro.setForeground(bG2);
        intro.setFont(bodyFont);
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(Box.createRigidArea(new Dimension(0,8)));
        headerPanel.add(welcome);
        headerPanel.add(intro);
        headerPanel.add(Box.createRigidArea(new Dimension(0,8)));
        mainPanel.add(headerPanel, BorderLayout.PAGE_START);
    }

    /**
     * Creates subsidiary components and assembles the layout and theme of the scroll pane.
     * The Scroll pane enables the user to browse through and select a frame model.
     */
    private void assembleBodyScroll() {
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.PAGE_AXIS));
        JLabel scrollTitle = new JLabel("Select a frame model");
        // formatting
        scrollTitle.setForeground(bG1);
        scrollTitle.setFont(bodyFont);
        scrollTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPanel.setBackground(bG2);
        scrollPanel.add(Box.createRigidArea(new Dimension(0,8)));
        scrollPanel.add(scrollTitle);
        scrollPanel.add(Box.createRigidArea(new Dimension(0,8)));
        // radio buttons
        ButtonGroup frameModels = new ButtonGroup();
        for (int i = 0; i < 8; i++) {
            ImageIcon model = new ImageIcon(String.format("graphics/f%d.png", i + 1));
            JToggleButton select = new JToggleButton(String.valueOf(i + 1), model);
            select.setFont(bodyFont);
            select.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedModel = Integer.parseInt(select.getText()) - 1;
                    updateModelDisplay();
                    status.setText("");
                }
            });
            select.setBackground(bG2);
            select.setAlignmentX(Component.CENTER_ALIGNMENT);
            scrollPanel.add(select);
            scrollPanel.add(Box.createRigidArea(new Dimension(0,8)));
            frameModels.add(select);
        }
        JScrollPane scrollPane = new JScrollPane(scrollPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(310, 515));
        // add to body panel
        bodyPanel.add(scrollPane);
    }

    /**
     * Creates subsidiary components and assembles the layout and theme of display section
     * to the right of the scroll pane.
     * The selected model in the selected color variation will be displayed along with
     * corresponding myStore.Product info.
     */
    private void assembleBodyRight() {
        //set colours
        modelStock.setBackground(bG2);
        modelStock.setForeground(bG1);
        modelName.setBackground(bG2);
        modelName.setForeground(bG1);
        modelID.setBackground(bG2);
        modelID.setForeground(bG1);
        modelPrice.setBackground(bG2);
        modelPrice.setForeground(bG1);
        status.setBackground(bG2);
        status.setForeground(bG1);
        add1ToCart.setBackground(bG1);
        add1ToCart.setForeground(bG2);
        remove1FromCart.setBackground(bG1);
        remove1FromCart.setForeground(bG2);

        // set fonts
        modelStock.setFont(bodyFont);
        modelName.setFont(bodyFont);
        modelID.setFont(bodyFont);
        modelPrice.setFont(bodyFont);
        status.setFont(bodyFont);
        add1ToCart.setFont(bodyFont);
        remove1FromCart.setFont(bodyFont);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(500, 510));
        rightPanel.setBackground(bG2);
        rightPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        updateModelDisplay();
        rightPanel.add(colourPanel(), BorderLayout.PAGE_START);

        JPanel rightCenter = new JPanel();
        rightCenter.setLayout(new BorderLayout());
        rightCenter.setBackground(bG2);
        rightCenter.add(modelDisplay, BorderLayout.PAGE_START);
        JPanel addRemove = new JPanel();
        addRemove.setLayout(new BoxLayout(addRemove, BoxLayout.PAGE_AXIS));
        addRemove.setBackground(bG2);
        //add1ToCart.setPreferredSize(new Dimension(70, 70));
        //remove1FromCart.setPreferredSize(new Dimension(70, 70));
        addRemove.add(Box.createRigidArea(new Dimension(0,50)));
        addRemove.add(add1ToCart);
        addRemove.add(Box.createRigidArea(new Dimension(0,10)));
        addRemove.add(remove1FromCart);
        JPanel modelInfo = new JPanel();
        modelInfo.setLayout(new BoxLayout(modelInfo, BoxLayout.PAGE_AXIS));
        modelInfo.setBackground(bG2);
        modelInfo.setPreferredSize(new Dimension(300, 500));
        modelInfo.add(Box.createRigidArea(new Dimension(0,50)));
        modelInfo.add(modelStock);
        modelInfo.add(modelName);
        modelInfo.add(modelID);
        modelInfo.add(modelPrice);

        JPanel rightCenterB = new JPanel();
        rightCenterB.setLayout(new BorderLayout());
        rightCenterB.setBackground(bG2);
        //rightCenterB.setPreferredSize(new Dimension(500, 200));
        rightCenterB.add(modelInfo, BorderLayout.LINE_START);
        rightCenterB.add(addRemove, BorderLayout.LINE_END);
        rightCenter.add(rightCenterB, BorderLayout.CENTER);
        rightPanel.add(rightCenter, BorderLayout.CENTER);

        JPanel rightFooter = new JPanel();
        rightFooter.setBackground(bG2);
        rightFooter.setPreferredSize(new Dimension(500, 50));
        rightFooter.add(status);
        //rightFooter.add(add1ToCart);
        //rightFooter.add(Box.createRigidArea(new Dimension(0,20)));
        //rightFooter.add(remove1FromCart);
        rightPanel.add(rightFooter, BorderLayout.PAGE_END);
        bodyPanel.add(Box.createRigidArea(new Dimension(100,0)));
        bodyPanel.add(rightPanel);
    }

    /**
     * Sets the layout of remaining components and assembles all sections into the overall myStore.StoreView GUI.
     */
    private void assemble() {
        displayID.setText(String.format("User Cart ID : %d", cartID));
        buildColors();
        buildModels();
        setUpInventory();
        assembleHeader();

        // set colours
        bodyPanel.setBackground(bG2);
        footerPanel.setBackground(bG1);

        assembleBodyScroll();
        assembleBodyRight();

        // footer
        footerPanel.setLayout(new BorderLayout());
        JPanel footerLeft = new JPanel();
        footerLeft.setBackground(bG1);
        JPanel footerRight = new JPanel();
        footerRight.setBackground(bG1);
        displayID.setForeground(bG2);
        displayID.setBackground(bG1);
        displayID.setFont(bodyFont);
        footerRight.add(displayID);

        browseCart.setBackground(bG2);
        browseCart.setForeground(bG1);
        checkout.setBackground(bG2);
        checkout.setForeground(bG1);
        //emptyCart.setBackground(bG2);
        //emptyCart.setForeground(bG1);
        browseCart.setFont(bodyFont);
        checkout.setFont(bodyFont);
        //emptyCart.setFont(bodyFont);

        footerLeft.add(browseCart);
        footerLeft.add(checkout);
        //footerLeft.add(emptyCart);

        footerPanel.add(footerLeft, BorderLayout.LINE_START);
        footerPanel.add(footerRight, BorderLayout.LINE_END);

        // set the preferred sizes
        mainPanel.setPreferredSize(new Dimension(1000, 650));
        bodyPanel.setPreferredSize(new Dimension(1000, 520));
        modelDisplay.setPreferredSize(new Dimension(500, 180));
        footerPanel.setPreferredSize(new Dimension(1000, 50));


        // add header, body, and footer panels to main panel
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.PAGE_END);

        // pack
        frame.add(mainPanel);
        frame.pack();
    }


    /**
     * Assembles and displays the frame graphical-user interface of the myStore.StoreView and implements a window prompt
     * ensuring the user wants to exit when the closing button is pressed.
     */
    public void displayGUI() {
        assemble();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want exit Shoptical.com?")
                        == JOptionPane.OK_OPTION) {
                    // close it down!
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
    }

    /**
     * Main method.
     *
     * @param args String[], system arguments
     */
    public static void main(String[] args) {
        StoreManager sm = new StoreManager();
        StoreView sv = new StoreView(sm, sm.newCartId());
        sv.displayGUI();
    }
}


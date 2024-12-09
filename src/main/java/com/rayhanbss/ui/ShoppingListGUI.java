package com.rayhanbss.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListGUI implements ActionListener {
    private JFrame frame;
    private final DefaultListModel<String> itemNameModel;
    private final DefaultListModel<Integer> quantityModel;
    private JTextField itemNameField;
    private JTextField quantityField;
    private JButton addButton;
    private JButton exportButton;
    private JPanel removePanel;
    private final List<JButton> removeButtons;

    public ShoppingListGUI() {
        removeButtons = new ArrayList<>();
        itemNameModel = new DefaultListModel<>();
        quantityModel = new DefaultListModel<>();
    }

    public void start() {
        frame = new JFrame("Shopping List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image icon = new ImageIcon("src/main/java/com/rayhanbss/resource/icon.png").getImage();
        frame.setIconImage(icon);
        frame.setSize(360, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(0, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel();
        headerPanel.setBackground(Color.WHITE);

        JPanel listPanel = createListPanel();
        listPanel.setBackground(Color.WHITE);

        JPanel inputPanel = createInputPanel();
        inputPanel.setBackground(Color.WHITE);

        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBackground(Color.WHITE);

        mainPanel.add(headerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(listPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(buttonPanel);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        ImageIcon icon = new ImageIcon("src/main/java/com/rayhanbss/resource/icon.png");
        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));

        JPanel titlePanel = getTitlePanel();

        headerPanel.add(iconLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        headerPanel.add(titlePanel);
        headerPanel.add(Box.createHorizontalGlue());

        return headerPanel;
    }

    private static JPanel getTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Shopping List");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Create your own shopping list");
        subtitleLabel.setFont(new Font("Roboto", Font.PLAIN, 12));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);
        return titlePanel;
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel headerPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(new JLabel(" Item Name", SwingConstants.LEFT));
        headerPanel.add(new JLabel("Quantity",SwingConstants.CENTER));
        headerPanel.add(new JLabel("Remove", SwingConstants.CENTER));

        JPanel listsPanel = new JPanel(new BorderLayout(10, 0));
        listsPanel.setBackground(Color.WHITE);

        JList<String> itemNameList = new JList<>(itemNameModel);
        JList<Integer> quantityList = new JList<>(quantityModel);

        itemNameList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                super.clearSelection();
            }
        });
        quantityList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                super.clearSelection();
            }
        });

        int rowHeight = 25;
        itemNameList.setFixedCellHeight(rowHeight);
        quantityList.setFixedCellHeight(rowHeight);

        DefaultListCellRenderer itemRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (c instanceof JLabel label) {
                    label.setBorder(new EmptyBorder(0, 5, 0, 5));
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                }
                return c;
            }
        };

        DefaultListCellRenderer quantityRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (c instanceof JLabel label) {
                    label.setBorder(new EmptyBorder(0, 5, 0, 5));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                }
                return c;
            }
        };

        itemNameList.setCellRenderer(itemRenderer);
        quantityList.setCellRenderer(quantityRenderer);

        JPanel itemNamePanel = new JPanel(new BorderLayout());
        JPanel quantityPanel = new JPanel(new BorderLayout());
        itemNamePanel.add(itemNameList);
        quantityPanel.add(quantityList);

        removePanel = new JPanel();
        removePanel.setBackground(Color.WHITE);
        removePanel.setLayout(new BoxLayout(removePanel, BoxLayout.Y_AXIS));

        JPanel combinedListsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        combinedListsPanel.setBackground(Color.WHITE);
        combinedListsPanel.add(itemNamePanel);
        combinedListsPanel.add(quantityPanel);
        combinedListsPanel.add(removePanel);

        listsPanel.add(combinedListsPanel, BorderLayout.CENTER);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(listsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 5));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(new JLabel("Item Name"));
        panel.add(new JLabel("Quantity"));

        itemNameField = new JTextField();
        quantityField = new JTextField();

        panel.add(itemNameField);
        panel.add(quantityField);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        addButton = new JButton("Add");
        addButton.setBackground(new Color(52, 168, 83));
        addButton.setForeground(Color.WHITE);
        addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        addButton.addActionListener(this);

        exportButton = new JButton("Export");
        exportButton.setBackground(new Color(52, 168, 83));
        exportButton.setForeground(Color.WHITE);
        exportButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        exportButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        exportButton.addActionListener(this);

        panel.add(addButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(exportButton);

        return panel;
    }

    private void rebuildRemovePanel() {
        removePanel.removeAll();

        for (int i = 0; i < itemNameModel.getSize(); i++) {
            JButton removeBtn = removeButtons.get(i);
            removeBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            removeBtn.setPreferredSize(new Dimension(removeBtn.getPreferredSize().width, 25));
            removePanel.add(removeBtn);
            removePanel.add(Box.createVerticalStrut(0));
        }

        removePanel.revalidate();
        removePanel.repaint();
    }

    private JButton createRemoveButton() {
        JButton button = new JButton();
        button.setForeground(new Color(233, 67, 53));
        button.setBackground(Color.WHITE);
        button.setText("×");
        button.setFont(new Font("Roboto", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        return button;
    }

    private void exportToPNG() {
        if (itemNameModel.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Shopping list is empty! Add some items before exporting.",
                    "Export Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            JPanel exportPanel = new JPanel();
            exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));
            exportPanel.setBackground(Color.WHITE);
            exportPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

            JPanel headerPanel = createHeaderPanel();
            headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            headerPanel.setBackground(Color.WHITE);
            exportPanel.add(headerPanel);
            exportPanel.add(Box.createRigidArea(new Dimension(0, 20)));

            JPanel tableHeader = getTableHeader();
            exportPanel.add(tableHeader);

            for (int i = 0; i < itemNameModel.getSize(); i++) {
                JPanel itemRow = new JPanel(new GridLayout(1, 2, 10, 0));
                itemRow.setBackground(Color.WHITE);
                itemRow.setAlignmentX(Component.LEFT_ALIGNMENT);
                itemRow.setMaximumSize(new Dimension(320, 25));

                JLabel itemName = new JLabel(itemNameModel.getElementAt(i));
                JLabel quantity = new JLabel(quantityModel.getElementAt(i).toString());

                itemRow.add(itemName);
                itemRow.add(quantity);
                exportPanel.add(itemRow);

                if (i < itemNameModel.getSize() - 1) {
                    exportPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                }
            }

            exportPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            JLabel timestamp = new JLabel("Generated on: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            timestamp.setFont(new Font("Roboto", Font.ITALIC, 10));
            timestamp.setAlignmentX(Component.LEFT_ALIGNMENT);
            exportPanel.add(timestamp);

            JFrame tempFrame = new JFrame();
            tempFrame.add(exportPanel);
            tempFrame.pack();

            Dimension size = exportPanel.getPreferredSize();

            BufferedImage image = new BufferedImage(
                    size.width,
                    size.height,
                    BufferedImage.TYPE_INT_RGB
            );

            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, size.width, size.height);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            exportPanel.print(g2d);
            g2d.dispose();

            tempFrame.dispose();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Shopping List");

            String defaultFileName = "shopping-list-" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) +
                    ".png";
            fileChooser.setSelectedFile(new File(defaultFileName));

            int userSelection = fileChooser.showSaveDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                if (!fileToSave.getName().toLowerCase().endsWith(".png")) {
                    fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
                }

                ImageIO.write(image, "PNG", fileToSave);

                JOptionPane.showMessageDialog(frame,
                        "Shopping list exported successfully to:\n" + fileToSave.getAbsolutePath(),
                        "Export Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Error exporting shopping list: " + ex.getMessage(),
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private static JPanel getTableHeader() {
        JPanel tableHeader = new JPanel(new GridLayout(1, 2, 10, 0));
        tableHeader.setBackground(Color.WHITE);
        tableHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableHeader.setMaximumSize(new Dimension(320, 30));

        JLabel itemHeaderLabel = new JLabel("Item Name");
        itemHeaderLabel.setFont(new Font("Roboto", Font.BOLD, 12));
        JLabel quantityHeaderLabel = new JLabel("Quantity");
        quantityHeaderLabel.setFont(new Font("Roboto", Font.BOLD, 12));

        tableHeader.add(itemHeaderLabel);
        tableHeader.add(quantityHeaderLabel);
        return tableHeader;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String inputName = itemNameField.getText();
            String quantity = quantityField.getText();

            if(inputName.isEmpty() || quantity.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Item Name and Quantity cannot be empty!");
            } else {
                try {
                    int quantityInt = Integer.parseInt(quantity);
                    if (quantityInt <= 0) {
                        JOptionPane.showMessageDialog(frame, "Quantity must be greater than 0!");
                        return;
                    }

                    itemNameModel.addElement(inputName);
                    quantityModel.addElement(quantityInt);

                    JButton removeBtn = createRemoveButton();
                    removeButtons.add(removeBtn);

                    rebuildRemovePanel();

                    itemNameField.setText("");
                    quantityField.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Quantity must be an integer number!");
                }
            }
        }

        if (e.getSource() instanceof JButton clickedButton) {
            if (removeButtons.contains(clickedButton)) {
                int index = removeButtons.indexOf(clickedButton);

                itemNameModel.remove(index);
                quantityModel.remove(index);

                removeButtons.remove(index);

                rebuildRemovePanel();
            }
        }

        if (e.getSource() == exportButton ) {
            exportToPNG();
        }
    }

}
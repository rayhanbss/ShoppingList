package com.rayhanbss;
import com.rayhanbss.ui.ShoppingListGUI;

/*
Collection yang digunakan:
1. DefaultListModel untuk JList di swing ui
2. List ArrayList untuk menyimpan Remove Button
*/

public class Main {
    public static void main(String[] args) {
        ShoppingListGUI shoppingListGUI = new ShoppingListGUI();
        shoppingListGUI.start();
    }
}
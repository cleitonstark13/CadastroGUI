package main;

/* Autor
 * 
 * Cleiton Alves e Silva Júnior
 * 
 */

import javax.swing.SwingUtilities;

import GUI.CadastroGUI;

public class Main {
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { 
        	new CadastroGUI();
        });
    }
}

package GUI;

/* Autor
 * 
 * Cleiton Alves e Silva Júnior
 * 
 */
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditarPessoaDialog extends JDialog implements ActionListener {
	private JTextField cpfField;
	private CadastroGUI parent;

	public EditarPessoaDialog(CadastroGUI parent) {
		super(parent, "Editar Pessoa", true);
		this.parent = parent;
		ImageIcon btnEditar = new ImageIcon(getClass().getResource("/IMG/editar.png"));
		ImageIcon btnCancelar = new ImageIcon(getClass().getResource("/IMG/cancelar.png"));

		JPanel panel = new JPanel(new FlowLayout()); // Usando FlowLayout para alinhar à esquerda
		JLabel cpfLabel = new JLabel("Digite o CPF:");
		cpfField = new JTextField(10); // Defina o tamanho desejado

		JButton editarButton = new JButton("Editar", btnEditar);
		editarButton.addActionListener(this);
		JButton cancelarButton = new JButton("Cancelar", btnCancelar);
		cancelarButton.addActionListener(this);

		panel.add(cpfLabel);
		panel.add(cpfField);
		panel.add(editarButton);
		panel.add(cancelarButton);

		getContentPane().add(panel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack(); // Redimensiona automaticamente com base no conteúdo
		setLocationRelativeTo(parent);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Editar")) {
			String cpfParaEditar = cpfField.getText();
			parent.editarPessoaPorCPF(cpfParaEditar);
			dispose(); // Fecha a janela de edição
		}
		if (e.getActionCommand().equals("Cancelar")) {
			dispose();
		}
	}
}

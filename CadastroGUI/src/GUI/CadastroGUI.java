package GUI;

/* Autor
 * 
 * Cleiton Alves e Silva Júnior
 * 
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CadastroGUI extends JFrame implements ActionListener, KeyListener {
	private JTextField nomeField, cpfField, dataNascimentoField, telefoneField, emailField;
	private JButton salvarButton, consultarButton, removerButton, editarButton, salvarEdicaoButton,
			cancelarEdicaoButton, excluirArquivoButton;
	private JTable tabelaResultados;
	private DefaultTableModel tableModel;
	private boolean emModoEdicao = false;
	private List<Pessoa> pessoas = new ArrayList<>();
	private int pessoaEmEdicaoIndex = -1;
	private KeyEvent e;

	public CadastroGUI() {
		super();
		verificarArquivoDeDados();
		setTitle("Cadastro de Dados");
		setSize(600, 400);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		ImageIcon imagemTituloJanela = new ImageIcon(getClass().getResource("/IMG/icon.png"));
		setIconImage(imagemTituloJanela.getImage());

		inicializarGUI();
	}

	private void inicializarGUI() {

		/*
		 * Imagens para os botões
		 */
		ImageIcon btnSalvar = new ImageIcon(getClass().getResource("/IMG/salvar.png"));
		ImageIcon btnConsultar = new ImageIcon(getClass().getResource("/IMG/consultar.png"));
		ImageIcon btnRemover = new ImageIcon(getClass().getResource("/IMG/remover.png"));
		ImageIcon btnEditar = new ImageIcon(getClass().getResource("/IMG/editar.png"));
		ImageIcon btnExcluirArq = new ImageIcon(getClass().getResource("/IMG/excluir.png"));
		ImageIcon btnCancelar = new ImageIcon(getClass().getResource("/IMG/cancelar.png"));

		JLabel nomeLabel = new JLabel("Nome:");
		JLabel cpfLabel = new JLabel("CPF:");
		JLabel dataNascimentoLabel = new JLabel("Data de Nascimento:");
		JLabel telefoneLabel = new JLabel("Telefone:");
		JLabel emailLabel = new JLabel("e-mail");

		nomeField = new JTextField();
		cpfField = new JTextField();
		dataNascimentoField = new JTextField();
		telefoneField = new JTextField();
		emailField = new JTextField();

		salvarButton = new JButton("Salvar", btnSalvar);
		salvarButton.addActionListener(this);

		removerButton = new JButton("Remover", btnRemover);
		removerButton.addActionListener(this);

		consultarButton = new JButton("Consultar", btnConsultar);
		consultarButton.addActionListener(this);

		editarButton = new JButton("Editar", btnEditar);
		editarButton.addActionListener(this);

		salvarEdicaoButton = new JButton("Salvar Edição", btnSalvar);
		salvarEdicaoButton.addActionListener(this);

		cancelarEdicaoButton = new JButton("Cancelar Edição", btnCancelar);
		cancelarEdicaoButton.addActionListener(this);

		excluirArquivoButton = new JButton("Excluir Arquivo", btnExcluirArq);
		excluirArquivoButton.addActionListener(this);

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(5, 2));

		inputPanel.add(cpfLabel);
		inputPanel.add(cpfField);
		inputPanel.add(nomeLabel);
		inputPanel.add(nomeField);
		inputPanel.add(dataNascimentoLabel);
		inputPanel.add(dataNascimentoField);
		inputPanel.add(telefoneLabel);
		inputPanel.add(telefoneField);
		inputPanel.add(emailLabel);
		inputPanel.add(emailField);

		tableModel = new DefaultTableModel();
		tabelaResultados = new JTable(tableModel);
		tableModel.addColumn("CPF");
		tableModel.addColumn("Nome");
		tableModel.addColumn("Data de Nascimento");
		tableModel.addColumn("Telefone");
		tableModel.addColumn("e-mail");

		JScrollPane scrollPane = new JScrollPane(tabelaResultados);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(salvarButton);
		buttonPanel.add(consultarButton);
		buttonPanel.add(removerButton);
		buttonPanel.add(editarButton);
		buttonPanel.add(salvarEdicaoButton);
		buttonPanel.add(cancelarEdicaoButton);
		buttonPanel.add(excluirArquivoButton);

		salvarEdicaoButton.setVisible(false);
		cancelarEdicaoButton.setVisible(false);

		// Crie uma ação nas caixas de texto de Nome, CPF, Data de Nascimento e Telefone

		cpfField.addKeyListener(this);
		nomeField.addKeyListener(this);
		dataNascimentoField.addKeyListener(this);
		telefoneField.addKeyListener(this);
		emailField.addKeyListener(this);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(inputPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(mainPanel);
	}

	/*
	 * Atribuição dos comandos das funções
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == salvarButton) {
			salvarDados();
		} else if (e.getSource() == consultarButton) {
			consultarPessoa();
		} else if (e.getSource() == removerButton) {
			removerPessoa();
		} else if (e.getSource() == editarButton) {
			abrirEditarPessoaDialog();
		} else if (e.getSource() == salvarEdicaoButton) {
			salvarEdicao();
		} else if (e.getSource() == cancelarEdicaoButton) {
			cancelarEdicao();
		} else if (e.getSource() == excluirArquivoButton) {
			excluirArquivoDados();
		}
	}

	/*
	 * Método que exclui os arquivo de dados
	 */
	private void excluirArquivoDados() {
		File arquivoDeDados = new File("C:\\Users\\cjunior\\eclipse-workspace\\CadastroGUI\\src\\BD\\dados.txt");
		ImageIcon iconAtencao = new ImageIcon(getClass().getResource("/IMG/atencao.png"));

		if (arquivoDeDados.exists()) {
			int resposta = JOptionPane.showConfirmDialog(this, "Tem certeza de que deseja excluir o arquivo dados.txt?",
					"Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, iconAtencao);

			if (resposta == JOptionPane.YES_OPTION) {
				if (arquivoDeDados.delete()) {
					JOptionPane.showMessageDialog(this, "Arquivo dados.txt excluído com sucesso.");
				} else {
					JOptionPane.showMessageDialog(this, "Erro ao excluir o arquivo dados.txt.");
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "O arquivo dados.txt não existe.");
		}
	}

	/*
	 * Método que verifica se o arquivo dados.txt existe
	 */

	private void verificarArquivoDeDados() {
		File arquivoDeDados = new File("C:\\Users\\cjunior\\eclipse-workspace\\CadastroGUI\\src\\BD\\dados.txt");

		if (!arquivoDeDados.exists()) {
			try {
				arquivoDeDados.createNewFile();
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Erro ao criar o arquivo de dados: " + ex.getMessage());
			}
		}
	}

	/*
	 * Método que formata o CPF para incluíndo a máscara e verificando a quantidade
	 * de dígidos
	 */

	private String formatarCPF(String cpf) {
		// Remove qualquer caractere que não seja dígito
		cpf = cpf.replaceAll("[^\\d]", "");

		// Verifica se o CPF possui 11 dígitos
		if (cpf.length() != 11) {
			JOptionPane.showMessageDialog(this, "CPF inválido. Deve conter 11 dígitos.");
			return "";
		}

		// Formata o CPF com máscara
		return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
	}

	/*
	 * Método que formata a data de nascimento
	 */

	private String formatarDataNascimento(String dataNascimento) {
		// Remove qualquer caractere que não seja dígito
		dataNascimento = dataNascimento.replaceAll("[^\\d]", "");

		// Verifica se a data de nascimento possui 8 dígitos
		if (dataNascimento.length() != 8) {
			JOptionPane.showMessageDialog(this, "Data de Nascimento inválida. Deve conter 8 dígitos.");
			return "";
		}

		// Formata a data de nascimento com máscara
		return dataNascimento.substring(0, 2) + "/" + dataNascimento.substring(2, 4) + "/"
				+ dataNascimento.substring(4);
	}

	/*
	 * Método que formata o número de telefone
	 */

	private String formatarTelefone(String telefone) {
		// Remove qualquer caractere que não seja dígito
		telefone = telefone.replaceAll("[^\\d]", "");

		// Verifica se o telefone possui pelo menos 10 dígitos (incluindo o DDD)
		if (telefone.length() < 10) {
			JOptionPane.showMessageDialog(this, "Telefone inválido. Deve conter pelo menos 10 dígitos.");
			return "";
		}

		// Formata o telefone com máscara
		return "(" + telefone.substring(0, 2) + ") " + telefone.charAt(2) + " " + telefone.substring(3, 7) + "-"
				+ telefone.substring(7);
	}

	/*
	 * Método que verifica se o cpf que está sendo cadastrado já existe na base de
	 * dados
	 */

	private boolean cpfJaCadastrado(String cpf) {
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader("C:\\Users\\cjunior\\eclipse-workspace\\CadastroGUI\\src\\BD\\dados.txt"));
			String linha;

			while ((linha = reader.readLine()) != null) {
				if (linha.startsWith("CPF: " + cpf)) {
					reader.close();
					return true; // CPF já cadastrado
				}
			}

			reader.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Erro ao verificar CPF: " + ex.getMessage());
		}

		return false; // CPF não cadastrado
	}

	/*
	 * Método que verifica se o cpf cadastrado é válido de acordo com as regras de
	 * criação de um cpf
	 */

	private boolean isCPFValido(String cpf) {
		// Remove todos os caracteres que não são dígitos
		cpf = cpf.replaceAll("[^\\d]", "");

		// Verifica se o CPF possui 11 dígitos
		if (cpf.length() != 11) {
			return false;
		}

		// Calcula o primeiro dígito verificador
		int soma = 0;
		for (int i = 10; i >= 2; i--) {
			soma += (cpf.charAt(10 - i) - '0') * i;
		}
		int primeiroDigito = 11 - (soma % 11);
		if (primeiroDigito >= 10) {
			primeiroDigito = 0;
		}

		// Verifica se o primeiro dígito verificador é igual ao dígito no CPF
		if (primeiroDigito != (cpf.charAt(9) - '0')) {
			return false;
		}

		// Calcula o segundo dígito verificador
		soma = 0;
		for (int i = 11; i >= 2; i--) {
			soma += (cpf.charAt(11 - i) - '0') * i;
		}
		int segundoDigito = 11 - (soma % 11);
		if (segundoDigito >= 10) {
			segundoDigito = 0;
		}

		// Verifica se o segundo dígito verificador é igual ao dígito no CPF
		return segundoDigito == (cpf.charAt(10) - '0');
	}

	/*
	 * Método que verifica se o e-mail cadastrado é válido contendo o "@"
	 */

	private boolean isEmailValido(String email) {
		// Verifica se o email contém o caractere "@"
		if (email.contains("@")) {
			return true;
		} else {
			JOptionPane.showMessageDialog(this, "Email inválido. Deve conter o caractere '@'.");
			return false;
		}
	}

	/*
	 * Salva os dados cadastrados
	 */

	private void salvarDados() {
		String cpf = cpfField.getText();
		String nome = nomeField.getText();
		String dataNascimento = dataNascimentoField.getText();
		String telefone = telefoneField.getText();
		String email = emailField.getText();

		// Formata o CPF com máscara
		cpf = formatarCPF(cpf);

		// Formata a data de nascimento com máscara
		dataNascimento = formatarDataNascimento(dataNascimento);

		// Formata o telefone com máscara
		telefone = formatarTelefone(telefone);

		// Verifica se o CPF está vazio
		if (cpf.isEmpty()) {
			JOptionPane.showMessageDialog(this, "CPF não pode estar vazio.");
			return;
		}

		// Verifica se o CPF já está cadastrado
		if (cpfJaCadastrado(cpf)) {
			JOptionPane.showMessageDialog(this, "CPF já cadastrado.");
			return;
		}

		// Verifica se o CPF é válido
		if (!isCPFValido(cpf)) {
			JOptionPane.showMessageDialog(this, "CPF inválido. Digite um CPF válido.");
			return;
		}

		// Verifica se o email é válido
		if (!isEmailValido(email)) {
			return;
		}

		try {
			BufferedWriter writer = new BufferedWriter(
					new FileWriter("C:\\Users\\cjunior\\eclipse-workspace\\CadastroGUI\\src\\BD\\dados.txt", true));
			writer.write("CPF: " + cpf + "\n");
			writer.write("Nome: " + nome + "\n");
			writer.write("Data de Nascimento: " + dataNascimento + "\n");
			writer.write("Telefone: " + telefone + "\n");
			writer.write("e-mail: " + email + "\n");
			writer.write("\n");
			writer.close();
			UIManager.put("OptionPane.okButtonText", "Sair");
			JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");
			cpfField.setText("");
			nomeField.setText("");
			dataNascimentoField.setText("");
			telefoneField.setText("");
			emailField.setText("");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Erro ao salvar os dados: " + ex.getMessage());
		}

	}

	/*
	 * Lista todas as pessoas cadastradas
	 */

	private void listarTodasPessoas() {
		DefaultTableModel model = (DefaultTableModel) tabelaResultados.getModel();
		model.setRowCount(0); // Limpar a tabela

		try {
			BufferedReader reader = new BufferedReader(
					new FileReader("C:\\Users\\cjunior\\eclipse-workspace\\CadastroGUI\\src\\BD\\dados.txt"));
			String linha;
			Object[] rowData = new Object[5];

			while ((linha = reader.readLine()) != null) {
				if (linha.startsWith("CPF: ")) {
					rowData[0] = linha.substring(5); // CPF
				} else if (linha.startsWith("Nome: ")) {
					rowData[1] = linha.substring(6); // Nome
				} else if (linha.startsWith("Data de Nascimento: ")) {
					rowData[2] = linha.substring(20); // Data de Nascimento
				} else if (linha.startsWith("Telefone: ")) {
					rowData[3] = linha.substring(10); // Telefone
				} else if (linha.startsWith("e-mail: ")) {
					rowData[4] = linha.substring(8); // e-mail
				} else if (linha.isEmpty()) {
					model.addRow(rowData);
				}
			}
			reader.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Erro ao listar os dados: " + ex.getMessage());
		}
	}

	/*
	 * Janela de consulta CPF
	 */

	private String abrirJanelaConsultaCPF() {

		ImageIcon iconUser = new ImageIcon(getClass().getResource("/IMG/user.png"));

		JTextField cpfInput = new JTextField();
		Object[] message = { "Digite o CPF:", cpfInput };
		UIManager.put("OptionPane.okButtonText", "Consultar");
		UIManager.put("OptionPane.cancelButtonText", "Sair");
		int option = JOptionPane.showConfirmDialog(this, message, "Consultar por CPF", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE, iconUser);

		if (option == JOptionPane.OK_OPTION) {
			return cpfInput.getText();
		} else {
			return null; // Retorna null se o usuário cancelar a operação
		}
	}

	/*
	 * Método de consulta de pessoa pelo cpf
	 */

	private void consultarPessoa() {

		DefaultTableModel model = (DefaultTableModel) tabelaResultados.getModel();
		model.setRowCount(0); // Limpar a tabela

		// Verifique se o CPF digitado não está vazio
		String cpfParaConsultar = abrirJanelaConsultaCPF();

		if (cpfParaConsultar == null) {
			// Se o CPF estiver vazio ou o usuário cancelou, liste todas as pessoas
			// cadastradas
			return;
		}

		if (cpfParaConsultar.trim().isEmpty()) {
			listarTodasPessoas();
			return;
		}

		// Remova a máscara do CPF digitado
		String cpfConsulta = cpfParaConsultar.replaceAll("[^\\d]", "");

		try {
			BufferedReader reader = new BufferedReader(
					new FileReader("C:\\Users\\cjunior\\eclipse-workspace\\CadastroGUI\\src\\BD\\dados.txt"));
			String linha;
			boolean encontrou = false;
			Object[] rowData = new Object[5];

			while ((linha = reader.readLine()) != null) {
				if (linha.startsWith("CPF: ")) {
					// Remove a máscara do CPF armazenado no arquivo
					String cpfArmazenado = linha.substring(5).replaceAll("[^\\d]", "");

					// Compara o CPF digitado (sem máscara) com o CPF armazenado (sem máscara)
					if (cpfConsulta.equals(cpfArmazenado)) {
						encontrou = true;
					}
				}
				if (encontrou) {
					if (linha.startsWith("CPF: ")) {
						rowData[0] = linha.substring(5); // CPF
					} else if (linha.startsWith("Nome: ")) {
						rowData[1] = linha.substring(6); // Nome
					} else if (linha.startsWith("Data de Nascimento: ")) {
						rowData[2] = linha.substring(20); // Data de Nascimento
					} else if (linha.startsWith("Telefone: ")) {
						rowData[3] = linha.substring(10); // Telefone
					} else if (linha.startsWith("e-mail: ")) {
						rowData[4] = linha.substring(8); // e-mail
					} else if (linha.isEmpty()) {
						model.addRow(rowData);
						encontrou = false;
					}
				}
			}
			reader.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Erro ao consultar os dados: " + ex.getMessage());
		}
	}

	/*
	 * Controle de exibição dos botões
	 */

	private void exibirBotoesEdicao() {
		salvarButton.setVisible(false);
		consultarButton.setVisible(false);
		removerButton.setVisible(false);
		excluirArquivoButton.setVisible(false);
		salvarEdicaoButton.setVisible(true);
		cancelarEdicaoButton.setVisible(true);
	}

	/*
	 * Salva os dados editados
	 */

	private void salvarEdicao() {
		// Coletar os dados editados
		String cpf = cpfField.getText();
		String nome = nomeField.getText();
		String dataNascimento = dataNascimentoField.getText();
		String telefone = telefoneField.getText();
		String email = emailField.getText();

		// Formata o CPF com máscara
		cpf = formatarCPF(cpf);

		// Formata a data de nascimento com máscara
		dataNascimento = formatarDataNascimento(dataNascimento);

		// Formata o telefone com máscara
		telefone = formatarTelefone(telefone);

		try {
			File inputFile = new File("C:\\Users\\cjunior\\eclipse-workspace\\CadastroGUI\\src\\BD\\dados.txt");
			File tempFile = new File("C:\\Users\\cjunior\\eclipse-workspace\\CadastroGUI\\src\\BD\\temp.txt");

			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String linha;
			boolean encontrou = false;

			while ((linha = reader.readLine()) != null) {
				if (linha.startsWith("CPF: " + cpfField.getText())) {
					encontrou = true;
					// Substitui as linhas correspondentes aos dados da pessoa
					writer.write("CPF: " + cpf + "\n");
					writer.write("Nome: " + nome + "\n");
					writer.write("Data de Nascimento: " + dataNascimento + "\n");
					writer.write("Telefone: " + telefone + "\n");
					writer.write("e-mail: " + email + "\n");
					writer.write("\n");
					for (int i = 0; i < 5; i++) {
						reader.readLine();
					}
					continue;
				}
				writer.write(linha + "\n");
			}

			reader.close();
			writer.close();

			if (encontrou) {
				inputFile.delete();
				tempFile.renameTo(inputFile);
				JOptionPane.showMessageDialog(this, "Edição concluída com sucesso!");
				cpfField.setText("");
				nomeField.setText("");
				dataNascimentoField.setText("");
				telefoneField.setText("");
				emailField.setText("");
				emModoEdicao = false;
				editarButton.setEnabled(true);

				salvarButton.setVisible(true);
				consultarButton.setVisible(true);
				removerButton.setVisible(true);
				excluirArquivoButton.setVisible(true);
				salvarEdicaoButton.setVisible(false);
				cancelarEdicaoButton.setVisible(false);

				// Limpar a tabela de resultados
				DefaultTableModel model = (DefaultTableModel) tabelaResultados.getModel();
				model.setRowCount(0);

				// Recarregar os dados na tabela
				listarTodasPessoas();
			} else {
				JOptionPane.showMessageDialog(this, "Erro ao editar. Pessoa não encontrada.");
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Erro ao editar: " + ex.getMessage());
		}
	}

	/*
	 * Cancela a edição de dados
	 */

	private void cancelarEdicao() {
		JOptionPane.showMessageDialog(this, "Edição cancelada.");
		cpfField.setText("");
		nomeField.setText("");
		dataNascimentoField.setText("");
		telefoneField.setText("");
		emailField.setText("");
		emModoEdicao = false;

		editarButton.setEnabled(true);
		salvarButton.setVisible(true);
		consultarButton.setVisible(true);
		removerButton.setVisible(true);
		excluirArquivoButton.setVisible(true);
		salvarEdicaoButton.setVisible(false);
		cancelarEdicaoButton.setVisible(false);
	}

	/*
	 * Criação da janela de Edição de pessoa
	 */
	private void abrirEditarPessoaDialog() {
		EditarPessoaDialog dialog = new EditarPessoaDialog(this);
		dialog.setVisible(true);
	}

	/*
	 * Método de edição de pessoa
	 */

	public void editarPessoaPorCPF(String cpfParaEditar) {
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader("C:\\Users\\cjunior\\eclipse-workspace\\CadastroGUI\\src\\BD\\dados.txt"));
			String linha;
			boolean encontrou = false;
			StringBuilder dadosPessoa = new StringBuilder();

			while ((linha = reader.readLine()) != null) {
				if (linha.startsWith("CPF: ")) {
					String cpfArmazenado = linha.substring(5).replaceAll("[^\\d]", ""); // Remover máscara do CPF
																						// armazenado
					if (cpfArmazenado.equals(cpfParaEditar)) {
						encontrou = true;
						// Lê as linhas correspondentes aos dados da pessoa
						dadosPessoa.append(linha).append("\n");
						dadosPessoa.append(reader.readLine()).append("\n");
						dadosPessoa.append(reader.readLine()).append("\n");
						dadosPessoa.append(reader.readLine()).append("\n");
						dadosPessoa.append(reader.readLine()).append("\n");
					}
				}
			}
			reader.close();

			if (encontrou) {
				JOptionPane.showMessageDialog(this, "Dados encontrados para edição.");
				emModoEdicao = true;
				String[] dados = dadosPessoa.toString().split("\n");

				cpfField.setText(dados[0].substring(5)); // CPF (com máscara)
				nomeField.setText(dados[1].substring(6)); // Nome
				dataNascimentoField.setText(dados[2].substring(20)); // Data de Nascimento
				telefoneField.setText(dados[3].substring(10)); // Telefone
				emailField.setText(dados[4].substring(8)); // e-mail

				exibirBotoesEdicao(); // Exibe os botões de edição

				// Desativa o botão "Editar" quando estiver em modo de edição
				editarButton.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(this, "CPF não encontrado.");
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Erro ao consultar os dados: " + ex.getMessage());
		}
	}

	/*
	 * Janela de remoção por cpf
	 */

	private String abrirJanelaRemoverCPF() {
		ImageIcon iconUser = new ImageIcon(getClass().getResource("/IMG/user.png"));

		JTextField cpfInput = new JTextField();
		Object[] message = { "Digite o CPF:", cpfInput };
		UIManager.put("OptionPane.okButtonText", "Remover");
		UIManager.put("OptionPane.cancelButtonText", "Sair");
		int option = JOptionPane.showConfirmDialog(this, message, "Remover por CPF", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE, iconUser);

		if (option == JOptionPane.OK_OPTION) {
			String cpfDigitado = cpfInput.getText();
			// Remove qualquer caractere que não seja dígito para obter o CPF sem máscara
			return cpfDigitado.replaceAll("[^\\d]", "");
		} else {
			return null; // Retorna null se o usuário cancelar a operação
		}
	}

	/*
	 * Método remover pessoa
	 */

	private void removerPessoa() {
		String cpfParaRemover = abrirJanelaRemoverCPF();
		DefaultTableModel model = (DefaultTableModel) tabelaResultados.getModel();

		if (cpfParaRemover == null) {
			// O usuário cancelou a operação, não faça nada
			return;
		}

		try {
			File inputFile = new File("C:\\Users\\cjunior\\eclipse-workspace\\CadastroGUI\\src\\BD\\dados.txt");
			File tempFile = new File("C:\\Users\\cjunior\\eclipse-workspace\\CadastroGUI\\src\\BD\\temp.txt");

			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String linha;
			boolean encontrou = false;

			while ((linha = reader.readLine()) != null) {
				if (linha.startsWith("CPF: ")) {
					// Remove a máscara do CPF armazenado para comparar
					String cpfArmazenado = linha.substring(5).replaceAll("[^\\d]", "");
					if (cpfParaRemover.equals(cpfArmazenado)) {
						encontrou = true;
						// Remove as linhas correspondentes ao nome a ser removido
						for (int i = 0; i < 4; i++) {
							reader.readLine();
						}
						continue;
					}
				}
				writer.write(linha + "\n");
			}

			reader.close();
			writer.close();

			if (encontrou) {
				inputFile.delete();
				tempFile.renameTo(inputFile);
				JOptionPane.showMessageDialog(this, "Pessoa removida com sucesso!");
				cpfField.setText("");
				model.setRowCount(0); // Limpar a tabela
			} else {
				JOptionPane.showMessageDialog(this, "Pessoa não encontrada.");
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Erro ao remover a pessoa: " + ex.getMessage());
		}
	}

	/*
	 * Regras do KeyListener
	 */

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.e = e;
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			salvarDados();
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cpfField.setText("");
			nomeField.setText("");
			dataNascimentoField.setText("");
			telefoneField.setText("");
			emailField.setText("");
		}
		if (e.getKeyCode() == KeyEvent.VK_F3) {
			consultarPessoa();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}

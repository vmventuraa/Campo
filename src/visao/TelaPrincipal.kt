package visao

import modelo.Tabuleiro
import modelo.TabuleiroEvento
import java.awt.BorderLayout
import java.awt.Image
import java.net.URL
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.swing.*

fun main(args: Array<String>) {
    TelaPrincipal()
}

class TelaPrincipal : JFrame() {
    private lateinit var audio : URL
    private lateinit var ais : AudioInputStream
    private lateinit var clip : Clip


    // alterado linha 1 e coluna 2 do tabuleiro e quantidade de minas 3
    private val tabuleiro = Tabuleiro(qtdeLinhas = 10, qtdeColunas = 10, qtdeMinas = 0)
    private val painelTabuleiro = PainelTabuleiro(tabuleiro)

    //alterado ícone do app 4
    val imgURL = javaClass.getResource("/images/bomb1.png")
    val img = ImageIcon(imgURL)

    //alterado mensagem de vitória para ícone 5
    val vitoriaUrl = javaClass.getResource("/images/vitoria.png")
    val vitoriaIcon = ImageIcon(vitoriaUrl)

    //alterado mensagem de derrota para ícone 6
    val derrotaUrl = javaClass.getResource("/images/game-over.png")
    val derrotaIcon = ImageIcon(derrotaUrl).apply {
        // Definindo o novo tamanho do ícone
        val newWidth = 200 // Largura desejada
        val newHeight = 200 // Altura desejada
        image = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH)
    }

    init {

        this.iconImage = img.image
        tabuleiro.onEvento(this::mostrarResultado)
        add(painelTabuleiro)

        setSize(690, 438)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE

        //alterado título do app 7
        title = "Campo Minado FIAP"
        isVisible = true
    }

    private fun mostrarResultado(evento: TabuleiroEvento) {
        SwingUtilities.invokeLater {
            val msg: String
            val icon: Icon?

            // Define a mensagem e o ícone com base no evento
            when(evento) {
                TabuleiroEvento.VITORIA -> {

                    icon = vitoriaIcon
                    audio = URL("https://www.wavsource.com/snds_2020-10-01_3728627494378403/sfx/cheering.wav")
                    ais = AudioSystem.getAudioInputStream(audio)
                    clip = AudioSystem.getClip()
                    clip.open(ais)
                    clip.start()

                }
                TabuleiroEvento.DERROTA -> {

                    icon = derrotaIcon
                    audio = URL("https://www.wavsource.com/snds_2020-10-01_3728627494378403/sfx/bomb_x.wav")
                    ais = AudioSystem.getAudioInputStream(audio)
                    clip = AudioSystem.getClip()
                    clip.open(ais)
                    clip.start()

                }
            }

            // Cria um JLabel personalizado para exibir o ícone de derrota
            val label = JLabel(icon)
            label.horizontalAlignment = SwingConstants.CENTER // Centraliza o ícone horizontalmente

            // Cria um painel personalizado com layout centralizado e adiciona o JLabel
            val panel = JPanel(BorderLayout())
            panel.add(label, BorderLayout.CENTER) // Adiciona o JLabel centralizado

            // Exibe a mensagem com o ícone de derrota centralizado
            //alterada mensagem para "resultado" 8
            JOptionPane.showMessageDialog(this, panel, "Resultado", JOptionPane.CLOSED_OPTION)
            tabuleiro.reiniciar()

            painelTabuleiro.repaint()
            painelTabuleiro.validate()
        }
    }
}